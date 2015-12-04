/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.joham.goods.dao.impl;

import com.joham.goods.bean.Goods;
import com.joham.goods.dao.GoodsDao;
import com.joham.util.BasicSqlSupport;
import com.joham.util.SelectBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("GoodsDao")
public class GoodsDaoImpl extends BasicSqlSupport implements GoodsDao{

	public List<Object> findGoodsList(Map<String,Object> map) {
		return getSession().selectList("com.ningpai.goods.dao.GoodsMapper.findGoodsList",map);
	}

	public int getGoodsCount(SelectBean selectBean) {
		return getSession().selectOne("com.ningpai.goods.dao.GoodsMapper.getGoodsCount",selectBean);
	}

	public int deleteGoodsById(Map<String, String> map) {
		return getSession().delete("com.ningpai.goods.dao.GoodsMapper.deleteGoodsById",map);
	}

	public int updateGoodsById(Goods goods) {
		return getSession().update("com.ningpai.goods.dao.GoodsMapper.updateGoodsById",goods);
	}

	public Goods selectById(Long goodsId) {
		return getSession().selectOne("com.ningpai.goods.dao.GoodsMapper.selectById",goodsId);
	}

	public int addGoods(Goods goods) {
		return getSession().insert("com.ningpai.goods.dao.GoodsMapper.addGoods", goods);
	}

	public List<Object> queryAllGoodsToExport() {
		return getSession().selectList("com.ningpai.goods.dao.GoodsMapper.queryAllGoodsToExport");
	}

	public List<Object> queryGoodsListVoListForExportByGoodsIds(
			Map<String, Object> map) {
		return getSession().selectList("com.ningpai.goods.dao.GoodsMapper.queryGoodsListVoListForExportByGoodsIds",map);
	}
}
