package com.sydata.framework.excel;

import cn.afterturn.easypoi.excel.export.styler.ExcelExportStylerDefaultImpl;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * <p>
 * 基础组导出样式实现类
 * </p>
 *
 * @author pangbohuan
 * @date 2021/6/30 9:40
 */
public class ExcelExportStylerZtImpl extends ExcelExportStylerDefaultImpl {

    public ExcelExportStylerZtImpl(Workbook workbook) {
        super(workbook);
    }

    @Override
    public CellStyle getHeaderStyle(short color) {
        CellStyle headerStyle = super.getHeaderStyle(color);
        Font font = workbook.createFont();
        //设置粗体
        font.setBold(true);
        headerStyle.setFont(font);
        this.setBorder(headerStyle);
        return headerStyle;
    }

    @Override
    public CellStyle getTitleStyle(short color) {
        CellStyle titleStyle = super.getTitleStyle(color);
        Font font = workbook.createFont();
        //设置粗体
        font.setBold(true);
        titleStyle.setFont(font);
        this.setBorder(titleStyle);
        return titleStyle;
    }

    @Override
    public CellStyle stringSeptailStyle(Workbook workbook, boolean isWarp) {
        CellStyle cellStyle = super.stringSeptailStyle(workbook, isWarp);
        this.setBorder(cellStyle);
        return cellStyle;
    }

    @Override
    public CellStyle stringNoneStyle(Workbook workbook, boolean isWarp) {
        CellStyle cellStyle = super.stringNoneStyle(workbook, isWarp);
        this.setBorder(cellStyle);
        return cellStyle;
    }

    /**
     * 设置边框
     *
     * @param cellStyle 单元格样式
     */
    private void setBorder(CellStyle cellStyle) {
        //下边框
        cellStyle.setBorderBottom(BorderStyle.THIN);
        //左边框
        cellStyle.setBorderLeft(BorderStyle.THIN);
        //上边框
        cellStyle.setBorderTop(BorderStyle.THIN);
        //右边框
        cellStyle.setBorderRight(BorderStyle.THIN);
    }
}
