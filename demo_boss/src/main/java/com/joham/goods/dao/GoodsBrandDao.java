/*
 * Copyright 2013 joham, Inc.All rights reserved.
 * joham PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.joham.goods.dao;

import com.joham.goods.bean.GoodsBrand;

import java.util.List;

public interface GoodsBrandDao {
	GoodsBrand selectByPrimaryKey(Long brandId);
	
	/**
	 * 获得品牌列表
	 */
	List<GoodsBrand> findGoodsBrandList();
}
