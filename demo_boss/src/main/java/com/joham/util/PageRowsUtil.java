/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
/**
 * 
 */
package com.joham.util;

/**
 * 分页行数工具类
 * @author NINGPAI-WangHaiYang
 * @since 2014年5月26日下午4:30:17
 */
public final class PageRowsUtil {
    private PageRowsUtil() {
    }
    private static final int FIFTEEN = 15;
    /**每页行数*/
    private static Integer pageRows = FIFTEEN;

    public static Integer getPageRows() {
        return pageRows;
    }

    public static void setPageRows(Integer pageRows) {
        PageRowsUtil.pageRows = pageRows;
    }
    
    
    
}
