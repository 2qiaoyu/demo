package com.joham.excel;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author joham
 */
@Controller
public class ExcelExportController {

    @RequestMapping(value = "exportExcel")
    public static void exportXLS(HttpServletResponse response) throws IOException {

        List<Area> list = new ArrayList<>();
        Area area1 = new Area();
        area1.setProvince("北京");
        area1.setCity("北京");
        area1.setDistrict("西城区");
        area1.setPostCode("12312312");
        area1.setPrice(new BigDecimal("3.90"));
        list.add(area1);

        //1.在内存中创建一个excel文件
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        //2.创建工作簿
        HSSFSheet sheet = hssfWorkbook.createSheet();
        //设置第三列宽度
        sheet.setColumnWidth(3, 256 * 30);
        //3.创建标题行
        HSSFRow hssfRow = sheet.createRow(0);
        hssfRow.createCell(0).setCellValue("省");
        hssfRow.createCell(1).setCellValue("市");
        hssfRow.createCell(2).setCellValue("区");
        hssfRow.createCell(3).setCellValue("邮编");
        hssfRow.createCell(4).setCellValue("价格");

        //4.遍历数据,创建数据行
        for (Area area : list) {
            //获取最后一行的行号
            int lastRowNum = sheet.getLastRowNum();
            HSSFRow dataRow = sheet.createRow(lastRowNum + 1);
            dataRow.createCell(0).setCellValue(area.getProvince());
            dataRow.createCell(1).setCellValue(area.getCity());
            dataRow.createCell(2).setCellValue(area.getDistrict());
            dataRow.createCell(3).setCellValue(area.getPostCode());
            dataRow.createCell(4).setCellValue(area.getPrice().toString());
        }
        //5.创建文件名
        String fileName = "test.xls";
        // 设置下载时客户端Excel的名称
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        OutputStream outputStream;
        outputStream = response.getOutputStream();
        hssfWorkbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    @RequestMapping(value = "exportExcel1")
    public static void exportXLS1(HttpServletResponse response) throws Exception {
        List<Area> list = new ArrayList<>();
        Area area1 = new Area();
        area1.setProvince("北京");
        area1.setCity("北京");
        area1.setDistrict("西城区");
        area1.setPostCode("12312312");
        area1.setPrice(new BigDecimal("3.90"));
        list.add(area1);
        ExcelHelper.parseObject2Excel(list, response);
    }
}
