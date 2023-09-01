package com.sydata.framework.excel;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.sydata.framework.cache.util.ClassFieldMapUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import one.util.streamex.StreamEx;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;

/**
 * <p>
 * 基础组多个Sheet Excel导出工具器
 * </p>
 *
 * @author pangbohuan
 * @date 2021/8/11 17:25
 */
@ApiModel(description = "基础组多个Sheet Excel导出工具器")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ZtSheetExcelExport extends BaseZtExcelExport {

    @ApiModelProperty(value = "sheet列表")
    private String filename;

    @ApiModelProperty(value = "sheet列表")
    private List<SheetParam> sheetParams = new ArrayList<>();

    public ZtSheetExcelExport(String filename) {
        this.filename = filename;
    }

    /**
     * 追加自定义Sheet导出参数
     *
     * @param sheetName sheetName
     * @param entity    数据模型
     * @param data      数据
     * @return
     */
    public <T> ZtSheetExcelExport add(String sheetName, Class<T> entity, List<T> data) {
        ExportParams title = new ExportParams();
        title.setSheetName(sheetName);
        this.add(title, entity, data);
        return this;
    }

    /**
     * 追加自定义Sheet导出参数
     *
     * @param title  sheetExcel导出参数
     * @param entity 数据模型
     * @param data   数据
     */
    public <T> ZtSheetExcelExport add(ExportParams title, Class<T> entity, List<T> data) {
        sheetParams.add(new SheetParam(title, entity, data));
        return this;
    }

    @Override
    protected String filename() {
        return filename;
    }

    @Override
    protected Workbook workbook() {
        Assert.notEmpty(sheetParams, "需要导出的sheet不能为空");
        List<Map<String, Object>> sheetsList = StreamEx.of(sheetParams)
                .peek(SheetParam::check)
                .map(sheetParam -> (Map<String, Object>) sheetParam.convertReportWorkExportMap())
                .toList();
        return ExcelExportUtil.exportExcel(sheetsList, ExcelType.HSSF);
    }


    /**
     * <p>
     * 自定义Sheet导出参数
     * </p>
     *
     * @author pangbohuan
     * @date 2021/8/11 17:14
     */
    @ApiModel(description = "自定义Sheet导出参数")
    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @NoArgsConstructor
    @AllArgsConstructor
    private class SheetParam<T> implements Serializable {

        @ApiModelProperty(value = "sheetExcel导出参数")
        private ExportParams title;

        @ApiModelProperty(value = "导出的数据模型")
        private Class<T> entity;

        @ApiModelProperty(value = "导出的数据")
        private List<T> data;


        private Map<String, Object> convertReportWorkExportMap() {
            Map<String, Field> fieldMap = ClassFieldMapUtil.mapByClass(SheetParam.class);
            Map<String, Object> reportWorkExportMap = new HashMap<>(fieldMap.size());
            fieldMap.forEach((fieldName, field) -> {
                try {
                    reportWorkExportMap.put(fieldName, field.get(this));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
            return reportWorkExportMap;
        }

        private void check() {
            Assert.notNull(title, "sheetExcel导出参数不能为空");
            Assert.notNull(entity, "导出的数据模型class不能为空");
            if (data == null) {
                data = Collections.emptyList();
            }
        }
    }
}
