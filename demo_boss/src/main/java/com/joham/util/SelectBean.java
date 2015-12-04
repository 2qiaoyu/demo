/*
 * Copyright 2013 joham, Inc.All rights reserved.
 * joham PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.joham.util;
/**
 * 分页参数Bean
 * @author joham-LiHaoZe
 * @since 2013年12月18日 下午2:56:14 
 * @version 1.0
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
