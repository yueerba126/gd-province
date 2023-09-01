package com.sydata.framework.excel;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 基础组Excel 构建器
 * </p>
 *
 * @author pangbohuan
 * @date 2021/6/29 16:40
 */
@Slf4j
public final class ZtExcelBuildUtil {

    /**
     * 创建基础组Excel导出工具器
     *
     * @param pojoClass 导出模型class
     * @param title     表格名称
     * @return 基础组Excel导出工具器
     */
    public static <T> ZtExcelExport<T> buildExcelExport(Class<T> pojoClass, String title) {
        ExportParams params = new ExportParams();
        params.setTitle(title);
        params.setSheetName(title);
        params.setStyle(ExcelExportStylerZtImpl.class);
        return new ZtExcelExport(params, pojoClass);
    }


    /**
     * 创建基础组Excel导出工具器
     *
     * @param pojoClass 导出模型class
     * @param params    Excel 导出参数
     * @return 基础组Excel导出工具器
     */
    public static <T> ZtExcelExport<T> buildExcelExport(Class<T> pojoClass, ExportParams params) {
        return new ZtExcelExport(params, pojoClass);
    }


    /**
     * 创建基础组Excel多Sheet导出工具器
     *
     * @param filename Excel文件名
     * @return 基础组多个Sheet Excel导出工具器
     */
    public static ZtSheetExcelExport buildSheetExcelExport(String filename) {
        return new ZtSheetExcelExport(filename);
    }
}
