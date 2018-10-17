package com.joham.excel.utils;

import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Excel导入导出用到的日期工具类
 *
 * @author joham
 */
public final class ExcelDateUtil {

    public static Date string2Date(String dateStr) {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        dateStr = dateStr.trim();
        Date date;
        switch (dateStr.length()) {
            case 8:
                date = string2Date(dateStr, DateConstants.DATE_FORMAT_8);
                break;
            case 10:
                date = string2Date(dateStr,
                        dateStr.indexOf("/") == -1 ? DateConstants.DATE_FORMAT_10 : DateConstants.DATE_FORMAT_10_2);
                break;
            case 11:
                date = string2Date(dateStr, DateConstants.DATE_FORMAT_11);
                break;
            case 14:
                date = string2Date(dateStr, DateConstants.DATETIME_FORMAT_14);
                break;
            case 19:
                date = string2Date(dateStr,
                        dateStr.indexOf("/") == -1 ? DateConstants.DATETIME_FORMAT_19 : DateConstants.DATETIME_FORMAT_19_2);
                break;
            case 21:
                date = string2Date(dateStr, DateConstants.DATETIME_FORMAT_21);
                break;
            case 23:
                date = string2Date(dateStr,
                        dateStr.indexOf("/") == -1 ? DateConstants.DATETIME_FORMAT_23 : DateConstants.DATETIME_FORMAT_23_2);
                break;
            default:
                throw new IllegalArgumentException(dateStr + "不支持的时间格式");
        }
        return date;
    }

    public static Date string2Date(String date, String format) {
        if (StringUtils.isEmpty(format)) {
            throw new IllegalArgumentException("the date format string is null!");
        }
        DateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(date.trim());
        } catch (ParseException e) {
            throw new IllegalArgumentException("the date string " + date + " is not matching format: " + format, e);
        }
    }

    public static String date2String(Date date) {
        return date2String(date, DateConstants.DATETIME_FORMAT_19);
    }

    public static String date2String(Date date, String format) {
        String result = null;
        if (date != null) {
            DateFormat sdf = new SimpleDateFormat(format);
            result = sdf.format(date);
        }
        return result;
    }
}
