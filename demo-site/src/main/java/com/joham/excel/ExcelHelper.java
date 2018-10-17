package com.joham.excel;

import com.alibaba.fastjson.JSONArray;
import com.joham.excel.annotaion.ChildExcel;
import com.joham.excel.annotaion.Excel;
import com.joham.excel.utils.ExcelDateUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * 通用导入导出
 */
public class ExcelHelper {

    private static Logger LOGGER = LoggerFactory.getLogger(ExcelHelper.class);

    public final static String EXCEL_SUFFIX_XLS = "xls";

    public final static String EXCEL_SUFFIX_XLSX = "xlsx";

    /**
     * 类属性辅助Map,避免多次反射取属性
     */
    public static Map<Class, LinkedList> classAuxiliaryMap = new HashMap<>();


    /**
     * 将数据写入指定的输出流中
     *
     * @param list
     * @param outputStream 输出流
     * @throws Exception
     */
    public static <T> void parseObject2Excel(List<T> list, OutputStream outputStream, String suffix) throws Exception {
        if (CollectionUtils.isEmpty(list)) {
            throw new IllegalArgumentException("数据不能为空");
        }
        if (Objects.isNull(outputStream)) {
            throw new IllegalArgumentException("OutputStream 不能为空");
        }
        Workbook wb = createWorkBook(suffix);
        Sheet sheet = wb.createSheet("Sheet1");
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //写入列头和校验信息
        writeTitleAndCheck(wb, sheet, null, list.get(0).getClass());
        //写入数据
        writeExcel(0, sheet, list, false, null, cellStyle);

        wb.write(outputStream);
        outputStream.close();
    }


    /**
     * 将excel 数据 写入指定的文件中
     *
     * @param list
     * @param file
     * @throws Exception
     */
    public static <T> void parseObject2Excel(List<T> list, File file) throws Exception {

        //获取文件后缀
        String suffix = getFileSuffix(file);

        if (EXCEL_SUFFIX_XLS.equals(suffix) || EXCEL_SUFFIX_XLS.equals(suffix)) {
            parseObject2Excel(list, new FileOutputStream(file), suffix);
        } else {
            throw new Exception("文件格式错误");
        }
    }


    /**
     * 将数据转换成Excel导出
     *
     * @param list
     * @param response
     * @throws Exception
     */
    public static <T> void parseObject2Excel(List<T> list, HttpServletResponse response, String suffix) throws Exception {
        if (CollectionUtils.isNotEmpty(list)) {
            Workbook wb = createWorkBook(suffix);
            Sheet sheet = wb.createSheet("Sheet1");

            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            //写入列头和校验信息
            writeTitleAndCheck(wb, sheet, null, list.get(0).getClass());
            //写入数据
            writeExcel(0, sheet, list, false, null, cellStyle);

            //生成文件名
            String filename = String.valueOf(System.currentTimeMillis()).concat("." + suffix);
            //写入到response
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + filename);
            OutputStream outputStream = null;
            try {
                outputStream = response.getOutputStream();
                wb.write(outputStream);
            } catch (IOException e) {
                LOGGER.error("导出Excel错误:" + e);
            } finally {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            }
        } else {
            throw new Exception("导出的数据为空");
        }
    }


    /**
     * 将数据转换成Excel2003导出
     *
     * @param list
     * @param response
     * @throws Exception
     */
    public static <T> void parseObject2Excel(List<T> list, HttpServletResponse response) throws Exception {
        parseObject2Excel(list, response, EXCEL_SUFFIX_XLS);
    }


    /**
     * 将导入的Excel表格转成需要的对象列表
     */
    public static <T> List<T> parseExcel2Object(MultipartFile file, Class<T> clazz) throws Exception {
        Workbook workbook = createWorkBook(getFileSuffix(file), file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        return getExcelData(sheet, clazz);
    }

    /**
     * 解析Excel文件
     */
    public static <T> List<T> parseExcel2Object(File file, Class<T> clazz) throws Exception {
        Workbook workbook;
        try {
            workbook = createWorkBook(getFileSuffix(file), new FileInputStream(file));
        } catch (Exception e) {
            throw new Exception("Excel解析失败,请检查文件是否正确");
        }
        return getExcelData(workbook.getSheetAt(0), clazz);
    }


    /**
     * 解析Excel文件流
     */
    public static <T> List<T> parseExcel2Object(InputStream inputStream, Class<T> clazz, String suffix) throws Exception {
        Workbook workbook;
        try {
            workbook = createWorkBook(suffix, inputStream);
        } catch (Exception e) {
            throw new Exception("Excel解析失败,请检查文件是否正确");
        }
        return getExcelData(workbook.getSheetAt(0), clazz);
    }

    /**
     * 解析Excel文件流
     */
    public static <T> List<T> parseExcel2Object(InputStream inputStream, Class<T> clazz) throws Exception {
        return parseExcel2Object(inputStream, clazz, EXCEL_SUFFIX_XLS);
    }


    /**
     * 获取导出模版
     *
     * @param response
     * @param clazz
     * @throws Exception
     */
    public static void getExcelTemplate(HttpServletResponse response, Class clazz, String suffix) throws Exception {
        Workbook wb = createWorkBook(suffix);
        Sheet sheet = wb.createSheet("sheet1");
        writeTitleAndCheck(wb, sheet, null, clazz);
        String filename = String.valueOf(System.currentTimeMillis()).concat("." + suffix);
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + filename);
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            wb.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        }
    }

    public static void getExcelTemplate(HttpServletResponse response, Class clazz) throws Exception {
        getExcelTemplate(response, clazz, EXCEL_SUFFIX_XLS);
    }


    /**
     * 写入数据到Excel中,
     * （处理思想:如果开始行号为0,则将主列包含的列头信息写入到第一行，如果是子列，也将子列的包含的头信息写到第一行，
     * 处理子列时,应将主列包含的列号传入,方便写入完子列后把主列信息按行合并）
     *
     * @param startRow  开始行
     * @param sheet     sheet页
     * @param list      要写入的数据
     * @param isChild   是否是子数据
     * @param parentSet 主列的列号信息
     * @return
     * @throws Exception
     */
    private static <T> int writeExcel(int startRow, Sheet sheet, List<T> list, boolean isChild, Set<Integer> parentSet, CellStyle cellStyle) throws Exception {

        LinkedList classLinkedList = getClassAuxiliary(list.get(0).getClass());
        Map<Integer, Method> getKeyMap = (Map<Integer, Method>) classLinkedList.get(0);
        Map<Integer, String> formatMap = (Map<Integer, String>) classLinkedList.get(5);
        Set<Integer> columnSet = (Set<Integer>) classLinkedList.get(9);
        if (startRow == 0) {
            startRow++;
        }
        int tempRowNumber = startRow;
        //处理数据写入表格
        for (int i = 0; i < list.size(); i++) {
            Row row;
            if (isChild) {
                row = sheet.getRow(startRow);
                if (row == null) {
                    row = sheet.createRow(startRow);
                }
            } else {
                row = sheet.createRow(startRow);
            }

            //解决多个子对象存在的情况，所有的子对象的开始行都是父行的位置
            int maxChildRow = startRow;

            //单独父行保存下来,给多个子对象用
            int parentRow = startRow;

            //解析每条数据到Excel中
            for (Integer key : getKeyMap.keySet()) {
                Object returnValue = getKeyMap.get(key).invoke(list.get(i));
                Cell cell = null;
                if (returnValue != null) {
                    if (returnValue instanceof Date) {
                        cell = row.createCell(key - 1);
                        cell.setCellValue(ExcelDateUtil.date2String((Date) returnValue, formatMap.get(key)));
                    } else if (returnValue instanceof List) {
                        List childList = (List) returnValue;
                        if (CollectionUtils.isNotEmpty(childList)) {
                            int childRow;
                            childRow = writeExcel(parentRow, sheet, childList, true, columnSet, cellStyle);
                            //计算出所有子中最大的
                            if (childRow > maxChildRow) {
                                maxChildRow = childRow;
                            }
                        }

                    } else {
                        cell = row.createCell(key - 1);
                        cell.setCellValue(returnValue == null ? "" : returnValue.toString());
                    }
                } else {
                    cell = row.createCell(key - 1);
                    cell.setCellValue("");
                }
                if (cell != null) {
                    cell.setCellStyle(cellStyle);
                }
            }
            //所有的子对象遍历完后,就可以得出当前父对象结束的行数
            startRow = maxChildRow;

            //如果是最后一条数据,行号就不需要再自增
            if (i < list.size() - 1) {
                startRow++;
            }
        }
        if (parentSet != null) {
            for (Integer j : parentSet) {
                sheet.addMergedRegion(new CellRangeAddress(tempRowNumber, startRow, j - 1, j - 1));
            }
        }
        return startRow;
    }

    /**
     * 获取文件的后缀
     *
     * @param file
     * @return
     * @throws Exception
     */

    private static String getFileSuffix(File file) throws Exception {
        if (file.exists()) {
            throw new Exception("文件不存在");
        }
        return file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length());
    }

    /**
     * 获取上传文件的后缀
     *
     * @param file
     * @return
     * @throws Exception
     */
    private static String getFileSuffix(MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new Exception("文件不存在");
        }
        String fileName = file.getOriginalFilename();
        return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
    }


    /**
     * 根据后缀创建excel
     *
     * @param suffix
     * @return
     * @throws Exception
     */
    private static Workbook createWorkBook(String suffix, InputStream inputStream) throws Exception {

        if (EXCEL_SUFFIX_XLS.equals(suffix)) {
            return new HSSFWorkbook(inputStream);
        } else if (EXCEL_SUFFIX_XLSX.equals(suffix)) {
            return new XSSFWorkbook(inputStream);
        } else {
            throw new Exception("文件格式不正确");
        }
    }

    /**
     * 根据后缀创建excel
     *
     * @param suffix
     * @return
     * @throws Exception
     */
    private static Workbook createWorkBook(String suffix) throws Exception {

        if (EXCEL_SUFFIX_XLS.equals(suffix)) {
            return new HSSFWorkbook();
        } else if (EXCEL_SUFFIX_XLSX.equals(suffix)) {
            return new XSSFWorkbook();
        } else {
            throw new Exception("文件格式不正确");
        }
    }

    /**
     * 写入校验数据
     */
    private static void setHSSFValidation(Workbook workbook, Sheet sheet,
                                          String[] textList, int firstRow, int endRow, int column, String checkName, int sheetIndex) {
        Sheet hiddenSheet = workbook.createSheet(checkName);
        for (int i = 0, length = textList.length; i < length; i++) {
            String name = textList[i];
            Row row = hiddenSheet.createRow(i);
            Cell cell = row.createCell(0);
            cell.setCellValue(name);
        }
        Name namedCell = workbook.createName();
        namedCell.setNameName(checkName);
        namedCell.setRefersToFormula(checkName + "!$A$1:$A$" + textList.length);
        DVConstraint constraint = DVConstraint.createFormulaListConstraint(checkName);
        CellRangeAddressList addressList = new CellRangeAddressList(firstRow, endRow, column, column);
        HSSFDataValidation validation = new HSSFDataValidation(addressList, constraint);
        sheet.addValidationData(validation);
        workbook.setSheetHidden(sheetIndex, true);
    }

    /**
     * 获取sheet页数据
     *
     * @param sheet sheet页
     * @param clazz 要转换的实体类
     * @param <T>
     * @return
     * @throws Exception
     */
    private static <T> List<T> getExcelData(Sheet sheet, Class<T> clazz) throws Exception {
        //获取合并单元格
        List<CellRangeAddress> combineCellNumber = getCombineCell(sheet);

        //获取所有行
        List<Row> rowList = Stream.iterate(1, n -> n + 1).map(i -> getRow(i, sheet)).limit(sheet.getLastRowNum()).collect(toList());

        //使用并行流校验数据并收集所有的错误信息
        List<String> errorMessage = rowList.parallelStream()
                .filter(row -> checkRow(row, clazz)) //过滤获取所有不合法的行
                .map(row -> getErrorMessage(row, clazz)) //拿到所有不合法列的错误信息
                .flatMap(Collection::stream) //展开错误信息
                .limit(sheet.getLastRowNum())
                .collect(Collectors.toList()); //汇总错误信息

        //校验所有的错误信心
        if (CollectionUtils.isNotEmpty(errorMessage)) {
            throw new Exception(JSONArray.toJSONString(errorMessage));
        }

        //如果不存在合并的单元格
        if (CollectionUtils.isEmpty(combineCellNumber)) {
            return rowList.parallelStream().map(row -> row2Entity(row, clazz)).collect(toList());
        } else {
            return rowList.parallelStream().map(row -> row2Entity(sheet, combineCellNumber, row.getRowNum(), clazz)).collect(toList());
        }
    }

    private static Row getRow(int i, Sheet sheet) {
        return sheet.getRow(i);
    }

    /**
     * 校验数据是否合法
     *
     * @param row
     * @param clazz
     * @param <T>
     * @return
     */
    private static <T> boolean checkRow(Row row, Class<T> clazz) {
        Object value = null;
        boolean result = true;
        try {
            LinkedList auxiliaryList = getClassAuxiliary(clazz);
            Map<Integer, Boolean> requiredMap = (Map<Integer, Boolean>) auxiliaryList.get(2);
            Map<Integer, Integer> maxLengthMap = (Map<Integer, Integer>) auxiliaryList.get(3);
            Map<Integer, String> dictionaryMap = (Map<Integer, String>) auxiliaryList.get(4);
            Map<Integer, String> formatMap = (Map<Integer, String>) auxiliaryList.get(5);
            Map<Integer, String> returnTypeMap = (Map<Integer, String>) auxiliaryList.get(7);

            //检查每个单元格
            for (Integer key : requiredMap.keySet()) {
                try {
                    //获取单元格的值,这里已经对字典做了处理,配置了字典单元格一定不能为空（字典为枚举值的集合,所以必须要有值）
                    value = formatValue(row.getCell(key - 1), returnTypeMap.get(key), dictionaryMap.get(key));
                } catch (Exception e) {
                    result = false;
                }
                //如果本行有一个不合法，则无需再对其后的单元格做校验
                if (result) {
                    if (value == null) {
                        value = "";
                    }
                    result = result && checkRequire(value.toString(), key, requiredMap) && checkLength(value.toString(), key, maxLengthMap)
                            && checkDateFormat(value.toString(), key, formatMap, returnTypeMap.get(key));
                } else {
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return !result;
    }

    private static <T> List<String> getErrorMessage(Row row, Class<T> clazz) {
        List<String> list = new ArrayList<>();
        Object value = null;
        try {
            LinkedList auxiliaryList = getClassAuxiliary(clazz);

            Map<Integer, Boolean> requiredMap = (Map<Integer, Boolean>) auxiliaryList.get(2);
            Map<Integer, Integer> maxLengthMap = (Map<Integer, Integer>) auxiliaryList.get(3);
            Map<Integer, String> dictionaryMap = (Map<Integer, String>) auxiliaryList.get(4);
            Map<Integer, String> formatMap = (Map<Integer, String>) auxiliaryList.get(5);
            Map<Integer, String> titleMap = (Map<Integer, String>) auxiliaryList.get(6);
            Map<Integer, Object> returnTypeMap = (Map<Integer, Object>) auxiliaryList.get(7);

            for (Integer key : requiredMap.keySet()) {
                try {
                    value = formatValue(row.getCell(key - 1), returnTypeMap.get(key), dictionaryMap.get(key));
                } catch (Exception e) {
                    list.add("第" + (row.getRowNum() + 1) + "行," + titleMap.get(key) + e.getMessage());
                }

                if (value == null) {
                    value = "";
                }

                if (!checkRequire(value.toString(), key, requiredMap)) {
                    list.add("第" + (row.getRowNum() + 1) + "行," + titleMap.get(key) + "不能为空");
                }

                if (!checkLength(value.toString(), key, maxLengthMap)) {
                    list.add("第" + (row.getRowNum() + 1) + "行," + titleMap.get(key) + "长度过长,长度应限制在" + maxLengthMap.get(key) + "之内");
                }

                if (!checkDateFormat(value.toString(), key, formatMap, returnTypeMap.get(key))) {
                    list.add("第" + (row.getRowNum() + 1) + "行," + titleMap.get(key) + "填写的日期,格式不正确");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private static boolean checkRequire(String value, Integer column, Map<Integer, Boolean> requiredMap) {
        if (requiredMap.get(column)) {
            return StringUtils.isNotEmpty(value);
        }
        return true;
    }


    private static boolean checkLength(String value, Integer column, Map<Integer, Integer> maxLengthMap) {
        if (StringUtils.isNotEmpty(value) && maxLengthMap.get(column) < value.length() && maxLengthMap.get(column) != 0) {
            return false;
        }
        return true;
    }

    /**
     * 检查目标文本是否符合
     */
    private static boolean checkDateFormat(String value, Integer column, Map<Integer, String> formatMap, Object dataType) {
        if (StringUtils.isNotEmpty(value) && formatMap.get(column).length() != value.length() && dataType instanceof Date) {
            return false;
        }

        return true;
    }

    /**
     * 将Excel的每行转成需要的对象,无合并的单元格
     */
    private static <T> T row2Entity(Row row, Class<T> clazz) {
        try {
            //获取类的辅助信息
            LinkedList auxiliaryList = getClassAuxiliary(clazz);

            Map<Integer, Object> returnTypeMap = (Map<Integer, Object>) auxiliaryList.get(7);
            Map<Integer, Method> setKeyMap = (Map<Integer, Method>) auxiliaryList.get(1);
            Map<Integer, String> dictionaryMap = (Map<Integer, String>) auxiliaryList.get(4);
            Map<Integer, String> formatMap = (Map<Integer, String>) auxiliaryList.get(5);
            T entity = clazz.newInstance();
            //防止传过来的类型是基本类型
            for (Integer key : setKeyMap.keySet()) {
                //获取属性的值类型
                Object returnType = returnTypeMap.get(key);
                //如果值类型是基本类型和BigDecimal类型，直接解析
                if (checkReturnType(returnType)) {
                    Object value = formatValue(row.getCell(key - 1), returnType, dictionaryMap.get(key));

                    LOGGER.info("-------------------方法" + setKeyMap.get(key) + "拿到了" + key + "列的数据,数据的值为[----" + (String.valueOf(value)) + "----]--------------------");
                    //将对应列数据写入实体中
                    setKeyMap.get(key).invoke(entity, value);

                } else if (returnType.equals(Date.class)) {
                    Object value = formatValue(row.getCell(key - 1), returnType, dictionaryMap.get(key));
                    if (value != null) {
                        //解析时间格式的数据
                        setKeyMap.get(key).invoke(entity, ExcelDateUtil.string2Date(value.toString(), formatMap.get(key)));
                    }
                } else {
                    setKeyMap.get(key).invoke(entity, row2Entity(row, returnType.getClass()));
                }
            }
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 将Excel的每行转成需要的对象,存在合并单元格现象
     *
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */

    private static <T> T row2Entity(Sheet sheet, List<CellRangeAddress> cellRangeAddress, int rowNumber, Class<T> clazz) {
        try {
            //防止传过来的类型是基本类型
            T entity = clazz.newInstance();
            Row row = sheet.getRow(rowNumber);
            //获取类的辅助信息
            LinkedList auxiliaryList = getClassAuxiliary(clazz);
            Map<Integer, Object> returnTypeMap = (Map<Integer, Object>) auxiliaryList.get(7);
            Map<Integer, Method> setKeyMap = (Map<Integer, Method>) auxiliaryList.get(1);
            Map<Integer, String> dictionaryMap = (Map<Integer, String>) auxiliaryList.get(4);
            Map<Integer, String> formatMap = (Map<Integer, String>) auxiliaryList.get(5);
            for (Integer key : setKeyMap.keySet()) {
                //获取属性的值类型
                Object returnType = returnTypeMap.get(key);
                //如果值类型不是基本类型,则递归封装
                if (checkReturnType(returnType)) {
                    Object value = getValue(sheet, cellRangeAddress, rowNumber, key - 1, returnType, dictionaryMap.get(key));
                    //判断列值能否为空
                    setKeyMap.get(key).invoke(entity, value);
                } else if (returnType.equals(Date.class)) {
                    Object value = getValue(sheet, cellRangeAddress, rowNumber, key - 1, returnType, dictionaryMap.get(key));
                    if (value != null) {
                        setKeyMap.get(key).invoke(entity, ExcelDateUtil.string2Date(value.toString()));
                    }
                } else {
                    setKeyMap.get(key).invoke(entity, row2Entity(sheet, cellRangeAddress, rowNumber, returnType.getClass()));
                }
            }
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 合并单元格处理,获取合并行
     *
     * @param sheet
     * @return
     */
    private static List<CellRangeAddress> getCombineCell(Sheet sheet) {
        List<CellRangeAddress> list = new ArrayList<>();
        //获得一个 sheet 中合并单元格的数量
        int sheetCount = sheet.getNumMergedRegions();
        //遍历合并单元格
        for (int i = 0; i < sheetCount; i++) {
            //获得合并单元格加入list中
            CellRangeAddress ca = sheet.getMergedRegion(i);
            list.add(ca);
        }
        return list;
    }

    /**
     * @param cell       excel单元格
     * @param returnType 要返回的数据类型
     * @param dictionary 字典(可为空)
     * @return
     */
    private static Object formatValue(Cell cell, Object returnType, String dictionary) throws Exception {
        if (cell == null) {
            return null;
        }
        Object value;
        String cellValue = "";

        int cellType = cell.getCellType();

        if (Cell.CELL_TYPE_NUMERIC == cellType) {
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cellValue = cell.getStringCellValue();
        } else if (Cell.CELL_TYPE_STRING == cellType) {
            cellValue = cell.getStringCellValue();
        }

        try {

            if (Integer.class.equals(returnType) || int.class.equals(returnType)) {
                value = Integer.parseInt(cellValue);
            } else if (Double.class.equals(returnType) || double.class.equals(returnType)) {
                value = Double.parseDouble(cellValue);
            } else if (Long.class.equals(returnType) || long.class.equals(returnType)) {
                value = Long.parseLong(cellValue);
            } else if (Float.class.equals(returnType) || float.class.equals(returnType)) {
                value = Float.parseFloat(cellValue);
            } else if (BigDecimal.class.equals(returnType)) {
                if (StringUtils.isNotEmpty(cellValue)) {
                    value = new BigDecimal(cellValue);
                } else {
                    value = new BigDecimal("0");
                }

            } else {
                value = cellValue;
            }
        } catch (Exception e) {
            throw new Exception("数据填写不合法");
        }
        return value;
    }


    /**
     * 判断单元格是否为合并单元格，是的话则将单元格的值返回
     *
     * @param sheet           表格sheet页
     * @param listCombineCell 存放合并单元格的list
     * @param rowNumber       要取值单元格的行号
     * @param columnNumber    要取值单元格的列号
     * @param returnType      返回值类型
     * @param dictionary      字典
     * @return
     * @throws Exception
     */
    private static Object getValue(Sheet sheet, List<CellRangeAddress> listCombineCell, int rowNumber, int columnNumber, Object returnType, String dictionary) throws Exception {

        int firstColumnNumber;
        int lastColumnNumber;
        int firstRowNumber;
        int lastRowNumber;
        Cell cellValue = null;
        for (CellRangeAddress ca : listCombineCell) {
            //获得合并单元格的起始行, 结束行, 起始列, 结束列
            firstColumnNumber = ca.getFirstColumn();
            lastColumnNumber = ca.getLastColumn();
            firstRowNumber = ca.getFirstRow();
            lastRowNumber = ca.getLastRow();
            //判断要取得的位置是否在合并单元格内
            if (rowNumber >= firstRowNumber && rowNumber <= lastRowNumber) {
                if (columnNumber >= firstColumnNumber && columnNumber <= lastColumnNumber) {
                    Row fRow = sheet.getRow(firstRowNumber);
                    Cell fCell = fRow.getCell(firstColumnNumber);
                    cellValue = fCell;
                    break;
                }
            }
        }
        //获取当前位置的单元格
        Row row = sheet.getRow(rowNumber);
        if (row != null) {
            Cell nowCell = row.getCell(columnNumber);
            if (nowCell != null && cellValue == null) {
                cellValue = nowCell;
            } else {
                return null;
            }
        } else {
            return null;
        }
        return formatValue(cellValue, returnType, dictionary);
    }


    /**
     * 根据类型获取反射属性
     *
     * @param clazz
     * @return
     */
    private static LinkedList getClassAuxiliary(Class clazz) throws Exception {
        LinkedList auxiliaryList = classAuxiliaryMap.get(clazz);
        if (auxiliaryList == null) {
            //用于快捷取用get方法
            Map<Integer, Method> getKeyMap = new HashMap<>();
            //用于快捷取用set方法
            Map<Integer, Method> setKeyMap = new HashMap<>();
            //用于快捷获取必填属性
            Map<Integer, Boolean> requiredMap = new HashMap<>();
            //用于快捷获取隐藏属性
            Map<Integer, Boolean> hiddenMap = new HashMap<>();
            //用于快捷获取最大长度属性
            Map<Integer, Integer> maxLengthMap = new HashMap<>();
            //用于获取字典属性
            Map<Integer, String> dictionaryMap = new HashMap<>();
            //用于获取时间格式化属性
            Map<Integer, String> formatMap = new HashMap<>();
            //用于获取表格抬头
            Map<Integer, String> titleMap = new HashMap<>();
            //用于保存属性的返回值类型
            Map<Integer, Object> returnTypeMap = new HashMap<>();

            Map<String, Class> childMap = new HashMap<>();

            //用于保存列信息
            Set<Integer> columnSet = new HashSet<>();
            //用于获取子表对应类的属性
            LinkedList childAuxiliaryList = null;

            auxiliaryList = new LinkedList();
            PropertyDescriptor propertyDescriptor;
            //获取类clazz所有属性对应的Excel列和set方法
            for (Field field : clazz.getDeclaredFields()) {
                //获取Excel注解
                Excel excel = AnnotationUtils.findAnnotation(field, Excel.class);
                if (excel != null) {
                    Method getMethod;
                    Method setMethod;
                    //通过自省获取字段 get/set 方法
                    propertyDescriptor = new PropertyDescriptor(field.getName(), clazz);
                    getMethod = propertyDescriptor.getReadMethod();
                    setMethod = propertyDescriptor.getWriteMethod();
                    if (getMethod == null || setMethod == null) {
                        throw new Exception("属性" + field.getName() + "缺少get/set方法");
                    }
                    setKeyMap.put(excel.column(), setMethod);
                    getKeyMap.put(excel.column(), getMethod);
                    requiredMap.put(excel.column(), excel.required());
                    hiddenMap.put(excel.column(), excel.hidden());
                    maxLengthMap.put(excel.column(), excel.maxLength());
                    dictionaryMap.put(excel.column(), excel.dictionary());
                    formatMap.put(excel.column(), excel.format());
                    titleMap.put(excel.column(), excel.title());
                    returnTypeMap.put(excel.column(), getMethod.getReturnType());
                    if (!returnTypeMap.get(excel.column()).equals(List.class)) {
                        if (excel.column() < 0) {
                            throw new Exception("属性不是List的列若需要导出,列号最小配置为1");
                        }
                        columnSet.add(excel.column());
                    } else if (excel.column() > 0) {
                        throw new Exception("属性为List类型的属性需要导出请配置Child注解");
                    }
                }
                //处理子表对应Excel的属性
                ChildExcel childExcel = AnnotationUtils.findAnnotation(field, ChildExcel.class);
                if (childExcel != null) {
                    int starColumn = childExcel.startColumn();
                    int endColumn = childExcel.endColumn();
                    Method getMethod;
                    Method setMethod;
                    propertyDescriptor = new PropertyDescriptor(field.getName(), clazz);
                    getMethod = propertyDescriptor.getReadMethod();
                    setMethod = propertyDescriptor.getWriteMethod();
                    if (getMethod == null || setMethod == null) {
                        throw new Exception("属性" + field.getName() + "缺少get/set方法");
                    }
                    int column = (starColumn + endColumn) / 2;
                    setKeyMap.put(column, setMethod);
                    getKeyMap.put(column, getMethod);
                    returnTypeMap.put(column, getMethod.getReturnType());


                    Class childClazz = childExcel.clazz();

                    childMap.put(field.getName(), childClazz);

                    childAuxiliaryList = getClassAuxiliary(childClazz);
                    classAuxiliaryMap.put(childClazz, childAuxiliaryList);
                }
            }
            auxiliaryList.add(0, getKeyMap);
            auxiliaryList.add(1, setKeyMap);
            auxiliaryList.add(2, requiredMap);
            auxiliaryList.add(3, maxLengthMap);
            auxiliaryList.add(4, dictionaryMap);
            auxiliaryList.add(5, formatMap);
            auxiliaryList.add(6, titleMap);
            auxiliaryList.add(7, returnTypeMap);
            auxiliaryList.add(8, hiddenMap);
            auxiliaryList.add(9, columnSet);
            auxiliaryList.add(10, childMap);

            classAuxiliaryMap.put(clazz, auxiliaryList);
        }
        return auxiliaryList;
    }


    /**
     * 判断对象的类型是否是基本类型
     *
     * @param object
     * @return
     */
    public static boolean checkReturnType(Object object) {
        boolean result = object.equals(String.class) || object.equals(Integer.class) || object.equals(Double.class)
                || object.equals(Long.class) || object.equals(int.class) || object.equals(double.class) || object.equals(long.class)
                || object.equals(BigDecimal.class) || object.equals(Float.class) || object.equals(float.class);

        return result;
    }


    private static void writeTitleAndCheck(Workbook wb, Sheet sheet, Row row, Class clazz) throws Exception {
        LinkedList classLinkedList = getClassAuxiliary(clazz);
        Map<Integer, Boolean> requiredMap = (Map<Integer, Boolean>) classLinkedList.get(2);
        Map<Integer, String> titleMap = (Map<Integer, String>) classLinkedList.get(6);
        Map<Integer, Boolean> hiddenMap = (Map<Integer, Boolean>) classLinkedList.get(8);

        //创建第一行
        if (row == null) {
            row = sheet.createRow(0);
        }
        int sheetIndex = 0;
        //如果配置了字典,写入校验数据
        for (Integer key : titleMap.keySet()) {
            //如果是隐藏列,
            if (hiddenMap.get(key)) {
                sheet.setColumnHidden(key - 1, true);
            }

            if (requiredMap.get(key)) {
                wb.createCellStyle().setFillBackgroundColor((short) 10);
            }

            //设置列宽
            sheet.setColumnWidth(key - 1, 10000);
            //写入列头
            row.createCell(key - 1, Cell.CELL_TYPE_STRING).setCellValue(titleMap.get(key));
        }

        Map<String, Class> childMap = (Map<String, Class>) classLinkedList.get(10);
        //递归写入子类的头信息
        for (String child : childMap.keySet()) {
            writeTitleAndCheck(wb, sheet, row, childMap.get(child));
        }
    }
}
