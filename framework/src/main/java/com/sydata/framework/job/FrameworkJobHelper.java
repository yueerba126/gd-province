package com.sydata.framework.job;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.PageUtil;
import com.sydata.framework.job.dto.LimitDto;
import com.xxl.job.core.context.XxlJobHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author lzq
 * @description 框架XxlJobHelper
 * @date 2022/10/27 16:44
 */
@Component
@Slf4j
public class FrameworkJobHelper extends XxlJobHelper {

    /**
     * 数组分片处理
     *
     * @param ts 需要分片的集合
     * @return 分片后的集合
     */
    public static <T> List<T> shardList(List<T> ts) {
        int shardTotal = XxlJobHelper.getShardTotal();
        int shardIndex = XxlJobHelper.getShardIndex();
        int nextIndex = shardIndex + 1;

        // 假设是最后一个实例则将剩余处理数都给他
        int averageCount = ts.size() / shardTotal;
        int fromIndex = shardIndex * averageCount;
        int toIndex = nextIndex == shardTotal ? ts.size() : nextIndex * averageCount;

        String log = "数组分片：分片实例编号{}, 分片实例总数{}, 数组长度{}, 处理下标{}/{}";
        XxlJobHelper.log(log, shardIndex, shardTotal, ts.size(), fromIndex, toIndex);

        List<T> sub = ListUtil.sub(ts, fromIndex, toIndex);
        ts.clear();
        return sub;
    }

    /**
     * 总数分片分页处理
     *
     * @param totalCount 总行数
     * @param pageSize   每页行数
     * @return limitDto列表
     */
    public static List<LimitDto> shardLimitTotalCount(Long totalCount, int pageSize) {
        if (totalCount == null || totalCount == 0) {
            return Collections.emptyList();
        }

        int shardTotal = XxlJobHelper.getShardTotal();
        int shardIndex = XxlJobHelper.getShardIndex();
        int nextIndex = shardIndex + 1;

        // 假设是最后一个实例则将剩余处理数都给他
        int averageCount = (int) (totalCount / shardTotal);
        int shardCount = nextIndex == shardTotal ? totalCount.intValue() - shardIndex * averageCount : averageCount;
        int shardStart = shardIndex * averageCount;
        int shardEnd = shardStart + shardCount;

        String logs = "处理数分页：分片实例编号{}, 分片实例总数{}, 处理总数{}, 分配数量{}, limit {},{}";
        XxlJobHelper.log(logs, shardIndex, shardTotal, totalCount, shardCount, shardStart, shardEnd);

        int partitions = PageUtil.totalPage(shardCount, pageSize);
        List<LimitDto> list = new ArrayList<>();
        for (int i = 0; i < partitions; i++) {
            int offset = i * pageSize;
            int size = Math.min(shardCount - offset, pageSize);
            list.add(new LimitDto(offset + shardStart, size));
        }
        return list;
    }

    /**
     * 总数分片分页处理
     *
     * @param totalCountSupplier 获取总数方法
     * @return limitDto列表
     */
    public static List<LimitDto> shardLimitTotalCount(Supplier<Long> totalCountSupplier) {
        int pageSize = Integer.parseInt(FrameworkJobHelper.getJobParam());
        return shardLimitTotalCount(totalCountSupplier.get(), pageSize);
    }
}
