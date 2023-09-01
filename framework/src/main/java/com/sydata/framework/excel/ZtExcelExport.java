package com.sydata.framework.excel;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.Assert;

import java.util.List;

/**
 * <p>
 * 基础组单个Sheet Excel导出工具器
 * </p>
 *
 * @author pangbohuan
 * @date 2021/6/29 16:37
 */
@Data
@Accessors(chain = true)
public class ZtExcelExport<T> extends BaseZtExcelExport {

    @ApiModelProperty(value = "Excel 导出参数")
    private ExportParams params;

    @ApiModelProperty(value = "导出对象Class")
    private Class<T> pojoClass;

    @ApiModelProperty(value = "导出数据")
    private List<? extends T> data;

    public ZtExcelExport(ExportParams params, Class<T> pojoClass) {
        this.params = params;
        this.pojoClass = pojoClass;
    }


    @Override
    protected String filename() {
        return this.params.getTitle();
    }

    @Override
    protected Workbook workbook() {
        Assert.notNull(params, "导出参数不能为空");
        Assert.notNull(pojoClass, "导出对象Class不能为空");
        return ExcelExportUtil.exportExcel(params, pojoClass, data);
    }
}
