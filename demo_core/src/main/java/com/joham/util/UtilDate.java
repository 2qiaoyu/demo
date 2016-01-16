package com.joham.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期格式化
 * 
 */
public final class UtilDate {
    private UtilDate() {
        
    }

    /**
     * 返回String字符串 格式：yyyy-MM-dd HH:mm:ss
     * 
     * @param date
     * @return String
     */
    public static String dataFormat(Date date) {
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.UK);
        return time.format(date);
    }

    /**
     * 返回String字符串 格式：yyyy-MM-dd
     * 
     * @param date
     * @return String
     */
    public static String todayFormat(Date date) {
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
        return time.format(date);
    }

    /**
     * 返回String字符串 格式：yyyy-MM-dd
     * 
     * @param date
     * @return String
     */
    public static String todayFormatString(Date date) {
        SimpleDateFormat time = new SimpleDateFormat("yyyyMMdd");
        return time.format(date);
    }

    /**
     * 返回String字符串 格式：yyyy-MM-dd
     * 
     * @param date
     * @return String
     */
    public static String yesterdayFormat(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());
    }

    public static String mathString(Date date) {
        SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmmss",
                Locale.UK);
        return time.format(date);
    }

    /**
     * String转Date
     * 
     * @param count
     * @return
     */
    public static Date stringToDate(String dateString) {
        SimpleDateFormat formatDate = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        Date time = null;
        try {
            time = formatDate.parse(dateString);
        } catch (ParseException e) {
        }

        return time;
    }
    /**
     * String转Date
     * 
     * @param count
     * @return
     */
    public static Date stringToDateMM(String dateString) {
        SimpleDateFormat formatDate = new SimpleDateFormat(
                "MM/dd/yyyy");
        Date time = null;
        try {
            time = formatDate.parse(dateString);
        } catch (ParseException e) {
        }
        
        return time;
    }

    /**
     * 当前时间加几天
     * 
     * @param number
     * @return String
     */
    public static String nextNumberDate(int number) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Date dd = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dd);
        calendar.add(Calendar.DAY_OF_MONTH, number);
        return format.format(calendar.getTime());
    }

}
