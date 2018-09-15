package com.joham.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author joham
 */
public class ExcelImport {

    public static void main(String[] args) {
        System.out.println(importXLS());
    }

    private static List<Area> importXLS() {

        ArrayList<Area> list = new ArrayList<>();
        try {
            //1、获取文件输入流
            InputStream inputStream = new FileInputStream("/Users/joham/my/demo/demo-site/src/main/java/com/joham/excel/1.xls");
            //2、获取Excel工作簿对象
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            //3、得到Excel工作表对象
            HSSFSheet sheetAt = workbook.getSheetAt(0);
            //4、循环读取表格数据
            for (int rowNum = 1; rowNum <= sheetAt.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = sheetAt.getRow(rowNum);
                if (hssfRow == null) {
                    continue;
                }
                //读取当前行中单元格数据，索引从0开始
                String province = getCellStringValue(hssfRow.getCell(0));
                String city = getCellStringValue(hssfRow.getCell(1));
                String district = getCellStringValue(hssfRow.getCell(2));
                String postcode = getCellStringValue(hssfRow.getCell(3));
                String price = getCellStringValue(hssfRow.getCell(4));
                Area area = new Area();
                area.setCity(city);
                area.setDistrict(district);
                area.setProvince(province);
                area.setPostCode(postcode);
                area.setPrice(new BigDecimal(price));
                list.add(area);
            }
            //5、关闭流
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static String getCellStringValue(HSSFCell cell) {
        if(cell == null) {
            return "";
        }
        String cellValue = "";
        switch (cell.getCellType()) {
            //字符串类型
            case HSSFCell.CELL_TYPE_STRING:
                cellValue = cell.getStringCellValue();
                if (cellValue.trim().equals("") || cellValue.trim().length() <= 0) {
                    cellValue = " ";
                }
                break;
            //数值类型
            case HSSFCell.CELL_TYPE_NUMERIC:
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            //公式
            case HSSFCell.CELL_TYPE_FORMULA:
                cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                cellValue = " ";
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                break;
            case HSSFCell.CELL_TYPE_ERROR:
                break;
            default:
                break;
        }
        return cellValue;
    }
}
