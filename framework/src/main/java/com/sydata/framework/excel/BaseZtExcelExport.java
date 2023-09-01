package com.sydata.framework.excel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.OutputStream;

/**
 * <p>
 * 中台基础导出类
 * </p>
 *
 * @author pangbohuan
 * @date 2021/8/11 17:33
 */
@Data
public abstract class BaseZtExcelExport {

    public static final String EXCEL_SUFFIX_XLS = ".xls";
    public static final String EXCEL_SUFFIX_XLSX = ".xlsx";

    @ApiModelProperty(value = "关闭流标识（默认自动关闭）")
    private boolean close = true;

    @ApiModelProperty(value = "excel格式")
    private String excelFormat = EXCEL_SUFFIX_XLS;

    /**
     * 执行导出
     *
     * @param outputStream 输出流
     **/
    public void doExport(OutputStream outputStream) throws IOException {
        ZtExportUtil.doExport(this.workbook(), outputStream, close);
    }

    /**
     * 执行WEB导出
     */
    public void doWebExport() {
        ZtExportUtil.doWebExport(this.workbook(), this.filename() + EXCEL_SUFFIX_XLS, close);
    }


    /**
     * 导出的文件名
     *
     * @return 文件名
     */
    protected abstract String filename();

    /**
     * 生成workbook
     *
     * @return workbook
     */
    protected abstract Workbook workbook();
}
