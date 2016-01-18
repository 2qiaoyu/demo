package com.joham.util;

/**
 * 分页行数工具类
 */
public final class PageRowsUtil {
    private PageRowsUtil() {
    }

    private static final int FIFTEEN = 15;
    /**
     * 每页行数
     */
    private static Integer pageRows = FIFTEEN;

    public static Integer getPageRows() {
        return pageRows;
    }

    public static void setPageRows(Integer pageRows) {
        PageRowsUtil.pageRows = pageRows;
    }

}
