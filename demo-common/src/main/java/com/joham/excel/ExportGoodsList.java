package com.joham.excel;

import com.joham.goods.bean.Goods;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * 导出商品列表数据
 *
 * @author joham
 */
public final class ExportGoodsList {
    /**
     * 导出商品列表
     *
     * @param goodsList 待导出的商品列表
     */
    public static void exportGoodsList(List<Object> goodsList,
                                       HttpServletResponse response) {
        // 创建文档
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet1 = wb.createSheet("商品列表");
        sheet1.setColumnWidth(0, 6000);
        sheet1.setColumnWidth(1, 15000);
        sheet1.setColumnWidth(2, 6000);
        sheet1.setColumnWidth(3, 6000);
        sheet1.setColumnWidth(4, 6000);
        sheet1.createFreezePane(5, 2);
        HSSFRow row1 = sheet1.createRow(0);
        sheet1.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
        HSSFCell cell1 = row1.createCell(0);
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cell1.setCellStyle(cellStyle);
        cell1.setCellValue("商品列表");
        HSSFRow row2 = sheet1.createRow(1);
        row2.createCell(0).setCellValue("商品编号");
        row2.createCell(1).setCellValue("商品名称");
        row2.createCell(2).setCellValue("销售价格");
        row2.createCell(3).setCellValue("商品状态");
        row2.createCell(4).setCellValue("商品品牌");

        // 循环Bean并添加值到文件中
        HSSFRow tempRow;
        if (null != goodsList && goodsList.size() > 0) {
            for (int i = 0; i < goodsList.size(); i++) {
                Goods listVo = (Goods) goodsList.get(i);
                tempRow = sheet1.createRow(2 + i);
                tempRow.createCell(0).setCellValue(listVo.getGoodsNo());
                tempRow.createCell(1).setCellValue(listVo.getGoodsName());
                tempRow.createCell(2).setCellValue(
                        String.valueOf(listVo.getGoodsPrice()));
                tempRow.createCell(3).setCellValue(
                        "1".equals(listVo.getGoodsAdded()) ? "上架销售中" : "下架");
                tempRow.createCell(4).setCellValue(
                        listVo.getGoodsBrand().getBrandName());
            }
        }
        String filename = String.valueOf(System.currentTimeMillis()).concat(
                ".xls");
        // 设置下载时客户端Excel的名称
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename="
                + filename);
        OutputStream ouputStream;
        try {
            ouputStream = response.getOutputStream();
            wb.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
        } catch (IOException e) {
        }

    }

    private ExportGoodsList() {
        super();
    }

}
