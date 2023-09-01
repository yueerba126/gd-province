package com.sydata.common.file.service.impl;

import cn.dev33.satoken.spring.SpringMVCUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.common.file.annotation.DataBindFileStorage;
import com.sydata.common.file.config.FileStorageConfig;
import com.sydata.common.file.config.MinioConfig;
import com.sydata.common.file.domain.FileStorage;
import com.sydata.common.file.dto.FileStateChangeDto;
import com.sydata.common.file.enums.FileStateEnum;
import com.sydata.common.file.mapper.FileStorageMapper;
import com.sydata.common.file.param.FileStoragePageParam;
import com.sydata.common.file.service.IFileStorageService;
import com.sydata.common.file.vo.FileStorageVo;
import com.sydata.framework.cache.MultiCacheBatchHelp;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.job.FrameworkJobHelper;
import com.sydata.framework.job.dto.LimitDto;
import com.sydata.framework.queue.RedisQueueProduce;
import com.sydata.framework.queue.annotation.RedisQueueListener;
import com.sydata.framework.util.BeanUtils;
import com.sydata.framework.util.StringUtils;
import com.xxl.job.core.handler.annotation.XxlJob;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectsArgs;
import io.minio.messages.DeleteObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaTypeFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.COLON;
import static com.baomidou.mybatisplus.core.toolkit.StringPool.SLASH;
import static com.sydata.common.file.enums.FileStateEnum.*;
import static com.sydata.framework.util.StringUtils.DOT;
import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.util.Collections.emptyList;
import static java.util.Objects.nonNull;
import static java.util.function.Function.identity;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM;

/**
 * 文件存储Service业务层处理
 *
 * @author lzq
 * @date 2022-06-23
 */
@Slf4j
@Service("fileStorageService")
@CacheConfig(cacheNames = FileStorageServiceImpl.CACHE_NAME)
public class FileStorageServiceImpl extends ServiceImpl<FileStorageMapper, FileStorage> implements IFileStorageService {

    static final String CACHE_NAME = "common:fileStorage";

    private static final String DATE_FORMAT = "yyyy/MM/dd";

    private static final String OVERTIME_AUTO_DISCARD_QUEUE = CACHE_NAME + ":overtimeAutoDiscardQueue";
    private static final String NOT_USE_KEY = CACHE_NAME + ":notUse:";
    private static final String STATE_CHANGE_BUFFER = CACHE_NAME + ":stateChangeBuffer";

    @Resource
    private FileStorageMapper fileStorageMapper;

    @Resource
    private FileStorageConfig fileStorageConfig;

    @Resource
    private MinioClient minioClient;

    @Resource
    private MinioConfig minioConfig;

    @Resource
    private RedisQueueProduce redisQueueProduce;

    @Resource
    private RedisTemplate redisTemplate;


    @Cacheable(key = "'id='+#id")
    @Override
    public FileStorage getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public List<FileStorage> listByIds(Collection<? extends Serializable> idList) {
        return MultiCacheBatchHelp.apply(CACHE_NAME)
                .<String, FileStorage, FileStorage>composeExecute()
                .fields(FileStorage::getId)
                .params((Collection<String>) idList)
                .targets(super::listByIds)
                .group(targets -> StreamEx.of(targets).toMap(FileStorage::getId, identity()))
                .get()
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public boolean updateBatchById(Collection<FileStorage> entityList) {
        MultiCacheBatchHelp.apply(CACHE_NAME).<FileStorage, FileStorage, FileStorage>composeExecute()
                .fields(FileStorage::getId)
                .inline()
                .params(entityList)
                .remove();
        return super.updateBatchById(entityList);
    }

    @Override
    public boolean removeByIds(Collection<?> list) {
        MultiCacheBatchHelp.apply(CACHE_NAME).<String, FileStorage, FileStorage>composeExecute()
                .fields(FileStorage::getId)
                .params((Collection<String>) list)
                .remove();
        return super.removeByIds(list);
    }

    @DataBindFieldConvert
    @Override
    public Page<FileStorageVo> pages(FileStoragePageParam pageParam) {
        Page<FileStorage> page = super.lambdaQuery()
                .eq(isNotEmpty(pageParam.getEnterpriseId()), FileStorage::getEnterpriseId, pageParam.getEnterpriseId())
                .eq(isNotEmpty(pageParam.getState()), FileStorage::getState, pageParam.getState())
                .ge(nonNull(pageParam.getBeginCreateTime()), FileStorage::getCreateTime, pageParam.getBeginCreateTime())
                .le(nonNull(pageParam.getEndCreateTime()), FileStorage::getCreateTime, pageParam.getBeginCreateTime())
                .orderByDesc(FileStorage::getId)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, FileStorageVo.class);
    }

    @SneakyThrows(Throwable.class)
    @Override
    public String upload(MultipartFile file, String organizeId, String stockHouseId,
                         FileStateEnum fileStateEnum, String fileType) {
        // 生成文件存储实体并插入数据库
        LocalDateTime now = LocalDateTime.now();
        String id = IdWorker.getIdStr();
        String storageName = storageName(organizeId, now, id, fileType);
        long fileSize = file.getSize();
        FileStorage fileStorage = new FileStorage().setId(id)
                .setBucket(minioConfig.getBucketName())
                .setStorageName(storageName)
                .setFileType(fileType)
                .setFileSize(fileSize)
                .setState(fileStateEnum.getState())
                .setCreateTime(now)
                .setEnterpriseId(organizeId)
                .setStockHouseId(stockHouseId);
        super.save(fileStorage);

        // 上传文件
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .object(storageName)
                    .contentType(MediaTypeFactory.getMediaType(storageName).orElse(APPLICATION_OCTET_STREAM).toString())
                    .stream(inputStream, fileSize, -1).build();
            minioClient.putObject(args);
        } finally {
            IoUtil.close(inputStream);
        }
        return id;
    }

    @Override
    public String uploadUse(MultipartFile file, String organizeId, String stockHouseId) {
        String fileType = FileUtil.extName(file.getOriginalFilename());
        Assert.hasLength(fileType, "获取不到文件类型,导致文件无法上传.请检查文件是否有扩展名");
        return this.upload(file, organizeId, stockHouseId, USE, fileType);
    }

    @SneakyThrows(Throwable.class)
    @Override
    public String uploadNotUse(MultipartFile file, String organizeId, String stockHouseId) {
        // 文件大小校验
        DataSize maxFileSize = fileStorageConfig.getMaxFileSize();
        boolean isExceedMaxFileSize = maxFileSize.compareTo(DataSize.ofBytes(file.getSize())) >= 0;
        Assert.state(isExceedMaxFileSize, "超出文件限制大小,文件最大限制为" + maxFileSize.toMegabytes() + "MB");

        // 获取真实的文件类型进行校验
        String fileType;
        try {
            fileType = FileTypeUtil.getType(file.getInputStream());
            boolean state = fileStorageConfig.getFileTypes().contains(fileType);
            Assert.state(state, "不支持该文件类型,系统检测该文件实际类型是" + fileType);
        } finally {
            IoUtil.close(file.getInputStream());
        }

        // 上传文件
        String id = this.upload(file, organizeId, stockHouseId, NOT_USE, fileType);

        // 设置争抢资源,并加入延时队列,指定时间内未使用将自动弃用
        int overtimeAutoDiscardTime = fileStorageConfig.getOvertimeAutoDiscardTime();
        String competeKey = SpringUtil.getApplicationName() + COLON + NOT_USE_KEY + id;
        redisTemplate.opsForValue().set(competeKey, null, overtimeAutoDiscardTime * 2, TimeUnit.MINUTES);
        redisQueueProduce.delayedOffer(OVERTIME_AUTO_DISCARD_QUEUE, id, overtimeAutoDiscardTime, TimeUnit.MINUTES);

        return id;
    }

    @SneakyThrows(Throwable.class)
    @Override
    public void download(String fileStorageId) {
        FileStorage fileStorage = SpringUtil.getBean(this.getClass()).getById(fileStorageId);
        Assert.notNull(fileStorage, "文件不存在");

        // 设置响应contentType
        HttpServletResponse response = SpringMVCUtil.getResponse();
        String contentType = MediaTypeFactory.getMediaType(fileStorage.getStorageName())
                .orElse(APPLICATION_OCTET_STREAM).toString();
        response.setContentType(contentType);

        // 写出文件流
        ServletOutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            inputStream = fileStream(fileStorage.getStorageName());
            outputStream = response.getOutputStream();
            IoUtil.copy(inputStream, outputStream);
        } catch (Exception e) {
            throw new IllegalArgumentException("文件服务器未找到该文件：" + fileStorageId, e);
        } finally {
            IoUtil.close(outputStream);
            IoUtil.close(inputStream);
        }
    }

    @SneakyThrows(Throwable.class)
    @Override
    public void downloadWithFileName(String fileStorageId, String fileName) {
        // 设置响应附件名称
        String attachmentFileName = new String((fileName).getBytes(), ISO_8859_1);
        HttpServletResponse response = SpringMVCUtil.getResponse();
        response.setHeader("Content-Disposition", "attachment;filename=" + attachmentFileName);

        // 文件下载
        download(fileStorageId);
    }

    @SneakyThrows(Throwable.class)
    @Override
    public void downloadToZip(List<String> fileStorageIds, List<String> fileNames, String zipName) {
        Assert.state(fileStorageIds.size() == fileNames.size(), "文件存储ID集合与文件名称集合长度不一致");
        List<FileStorage> fileStorages = SpringUtil.getBean(this.getClass()).listByIds(fileStorageIds);
        Assert.state(fileStorages.size() == fileStorageIds.size(), "文件存储ID集合中存在非法文件");

        // 设置响应信息
        HttpServletResponse response = SpringMVCUtil.getResponse();
        String fileName = new String((zipName + ".zip").getBytes(), ISO_8859_1);
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setContentType("application/octet-stream; charset=UTF-8");

        // 打包文件导出
        ServletOutputStream servletOutputStream = null;
        ZipOutputStream zip = null;
        try {
            servletOutputStream = response.getOutputStream();
            zip = new ZipOutputStream(servletOutputStream);
            for (int i = 0; i < fileStorages.size(); i++) {
                InputStream inputStream = null;
                try {
                    inputStream = fileStream(fileStorages.get(i).getStorageName());
                    zip.putNextEntry(new ZipEntry(fileNames.get(i)));
                    IoUtil.copy(inputStream, zip);
                } finally {
                    zip.closeEntry();
                    IoUtil.close(inputStream);
                }
            }
            zip.finish();
        } finally {
            IoUtil.close(zip);
            IoUtil.close(servletOutputStream);
        }
    }

    @Override
    public void useFile(String fileStorageId) {
        if (StringUtils.isEmpty(fileStorageId)) {
            return;
        }

        String competeKey = SpringUtil.getApplicationName() + COLON + NOT_USE_KEY + fileStorageId;
        Boolean state = redisTemplate.delete(competeKey);
        Assert.state(state, "文件已使用或已弃用");

        // 加入状态变更缓冲区-使用
        pushStateChangeBuffer(fileStorageId, USE);
    }

    @Override
    public Boolean discardFile(String fileStorageId) {
        if (StringUtils.isEmpty(fileStorageId)) {
            return Boolean.FALSE;
        }

        // 加入状态变更缓冲区-弃用
        pushStateChangeBuffer(fileStorageId, DISCARD);
        return Boolean.TRUE;
    }

    @DataBindService(strategy = DataBindFileStorage.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, fileStorageMapper);
    }

    @SneakyThrows(Throwable.class)
    @Override
    public InputStream fileStreamById(String id) {
        FileStorage fileStorage = SpringUtil.getBean(this.getClass()).getById(id);
        Assert.notNull(fileStorage, "文件存储ID不存在");
        return fileStream(fileStorage.getStorageName());
    }

    @Override
    public byte[] fileBytes(String id) {
        InputStream inputStream = null;
        try {
            inputStream = fileStreamById(id);
            return IoUtil.readBytes(inputStream);
        } finally {
            IoUtil.close(inputStream);
        }
    }

    @Override
    public String toHexString(String id) {
        byte[] bytes = fileBytes(id);
        final StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            // 0~F前面补零
            if ((bytes[i] & 0xff) < 0x10) {
                hexString.append("0");
            }
            hexString.append(Integer.toHexString(0xFF & bytes[i]));
        }
        return hexString.toString().toLowerCase();
    }


    /**
     * 过期自动弃用文件
     *
     * @param fileStorageId 文件存储ID
     */
    @RedisQueueListener(queueName = OVERTIME_AUTO_DISCARD_QUEUE)
    private void overtimeAutoDiscard(String fileStorageId) {
        String competeKey = SpringUtil.getApplicationName() + COLON + NOT_USE_KEY + fileStorageId;
        Boolean state = redisTemplate.delete(competeKey);
        if (!state) {
            return;
        }
        discardFile(fileStorageId);
    }

    /**
     * 加入状态变更缓冲区
     *
     * @param fileStorageId 文件存储ID
     * @param stateEnum     文件状态枚举
     */
    private void pushStateChangeBuffer(String fileStorageId, FileStateEnum stateEnum) {
        FileStateChangeDto buffer = new FileStateChangeDto(fileStorageId, stateEnum.getState(), LocalDateTime.now());
        String stateChangeBuffer = SpringUtil.getApplicationName() + COLON + STATE_CHANGE_BUFFER;
        redisTemplate.opsForList().leftPush(stateChangeBuffer, buffer);
    }


    /**
     * 处理文件状态变更缓冲区
     */
    @XxlJob("handleFileStorageStateChangeBuffer")
    public void handleFileStorageStateChangeBuffer() {
        // 总数分片分页处理
        String stateChangeBuffer = SpringUtil.getApplicationName() + COLON + STATE_CHANGE_BUFFER;
        ListOperations listOperations = redisTemplate.opsForList();
        List<LimitDto> limits = FrameworkJobHelper.shardLimitTotalCount(() -> listOperations.size(stateChangeBuffer));
        for (LimitDto limit : limits) {
            // 获取不到分区数据退出本次处理工作
            List<FileStateChangeDto> list = listOperations.rightPop(stateChangeBuffer, limit.getSize());
            if (CollectionUtils.isEmpty(list)) {
                break;
            }

            Map<String, FileStorage> fileStorageMap = StreamEx.of(list)
                    .map(FileStateChangeDto::getFileStorageId)
                    .distinct()
                    .toMap(identity(), fileStorageId -> new FileStorage().setId(fileStorageId));

            Map<String, List<FileStateChangeDto>> stateMap = StreamEx.of(list).groupingBy(FileStateChangeDto::getState);

            // 设置使用时间
            List<FileStateChangeDto> useList = stateMap.getOrDefault(USE.getState(), emptyList());
            useList.forEach(t -> {
                FileStorage fileStorage = fileStorageMap.get(t.getFileStorageId());
                fileStorage.setState(USE.getState()).setUseTime(t.getOperationTime());
            });

            // 设置弃用时间
            List<FileStateChangeDto> discardList = stateMap.getOrDefault(DISCARD.getState(), emptyList());
            discardList.forEach(t -> {
                FileStorage fileStorage = fileStorageMap.get(t.getFileStorageId());
                fileStorage.setState(DISCARD.getState()).setDiscardTime(t.getOperationTime());
            });

            // 批量修改
            this.updateBatchById(fileStorageMap.values());
        }
    }

    /**
     * 清理弃用文件
     *
     * @XxlJob("clearDiscardFile")
     */
    public void clearDiscardFile() {
        // 查询已弃用的文列表
        List<String> ids = super.lambdaQuery()
                .select(FileStorage::getId)
                .eq(FileStorage::getState, DISCARD.getState())
                .list()
                .stream()
                .map(FileStorage::getId)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }

        // 计算总分区
        List<String> shardIds = FrameworkJobHelper.shardList(ids);
        if (CollectionUtils.isEmpty(shardIds)) {
            return;
        }

        int partitionSize = Integer.parseInt(FrameworkJobHelper.getJobParam());
        List<List<String>> partitions = ListUtil.partition(shardIds, partitionSize);

        // 根据分区大小循环删除数据
        partitions.forEach(partition -> {
            // 批量删除文件
            List<DeleteObject> deleteObjects = super.lambdaQuery()
                    .select(FileStorage::getStorageName)
                    .in(FileStorage::getId, partition)
                    .list()
                    .stream()
                    .map(FileStorage::getStorageName)
                    .map(DeleteObject::new)
                    .collect(Collectors.toList());
            RemoveObjectsArgs args = RemoveObjectsArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .objects(deleteObjects)
                    .build();
            minioClient.removeObjects(args);

            // 批量删除文件存储数据
            this.removeByIds(partition);
        });
    }


    /**
     * 文件存储名称
     *
     * @param enterpriseId 组织ID
     * @param createTime   创建时间
     * @param id           文件存储ID
     * @param fileType     文件类型
     * @return 文件存储名
     */
    private String storageName(String enterpriseId, LocalDateTime createTime, String id, String fileType) {
        return new StringJoiner(SLASH)
                .add(enterpriseId)
                .add(DateTimeFormatter.ofPattern(DATE_FORMAT).format(createTime))
                .add(id + DOT + fileType)
                .toString();
    }

    /**
     * 获取文件流
     *
     * @param storageName 文件存储名称
     * @return 文件流
     */
    @SneakyThrows(Throwable.class)
    private InputStream fileStream(String storageName) {
        // 读取文件流
        GetObjectArgs args = GetObjectArgs.builder()
                .bucket(minioConfig.getBucketName())
                .object(storageName)
                .build();
        return minioClient.getObject(args);
    }
}