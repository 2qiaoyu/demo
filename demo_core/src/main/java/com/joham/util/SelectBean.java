package com.joham.util;

/**
 * 分页参数Bean
 */
public class SelectBean {

    //条件标记
    private String condition;
    //查询文本
    private String searchText;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

}
