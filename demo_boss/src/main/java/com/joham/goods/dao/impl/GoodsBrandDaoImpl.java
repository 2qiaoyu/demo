/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.joham.goods.dao.impl;

import com.joham.goods.bean.GoodsBrand;
import com.joham.goods.dao.GoodsBrandDao;
import com.joham.util.BasicSqlSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("GoodsBrandDao")
public class GoodsBrandDaoImpl extends BasicSqlSupport implements GoodsBrandDao{

	public GoodsBrand selectByPrimaryKey(Long brandId) {
		return getSession().selectOne("com.ningpai.goods.dao.GoodsBrandMapper.selectByPrimaryKey", brandId);
	}

	public List<GoodsBrand> findGoodsBrandList() {
		return getSession().selectList("com.ningpai.goods.dao.GoodsBrandMapper.findGoodsBrandList");
	}

}
