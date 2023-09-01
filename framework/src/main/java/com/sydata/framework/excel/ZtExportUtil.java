package com.sydata.framework.excel;

import cn.hutool.core.io.IoUtil;
import com.sydata.framework.util.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author lzq
 * @description 导出工具类
 * @date 2023/5/17 22:31
 */
public final class ZtExportUtil {

    /**
     * 执行导出
     *
     * @param workbook     workbook
     * @param outputStream 输出流
     * @param close        关闭流标识
     **/
    public static void doExport(Workbook workbook, OutputStream outputStream, boolean close) throws IOException {
        Assert.notNull(workbook, "workbook不能为空");
        Assert.notNull(outputStream, "导出流不能为空");

        // 写入导出流
        try {
            workbook.write(outputStream);
            outputStream.flush();
        } finally {
            IoUtil.close(workbook);
            // 关闭流标识决定需不需要关闭流
            if (close) {
                IoUtil.close(outputStream);
            }
        }
    }

    /**
     * 执行WEB导出
     *
     * @param workbook workbook
     * @param filename 文件名
     * @param close    关闭流标识
     */
    public static void doWebExport(Workbook workbook, String filename, boolean close) {
        Assert.state(StringUtils.isNotEmpty(filename), "export文件名不能为空");
        String exportFilename = filename;

        // 设置HttpServletResponse输出参数
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        String fileName = new String(exportFilename.getBytes(), StandardCharsets.ISO_8859_1);
        HttpServletResponse response = attributes.getResponse();
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // 执行导出
        try {
            doExport(workbook, response.getOutputStream(), close);
        } catch (IOException e) {
            throw new IllegalArgumentException("导出失败!", e);
        }
    }
}
