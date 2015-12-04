/*
 * Copyright 2013 joham, Inc.All rights reserved.
 * joham PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.joham.goods.dao;

import com.joham.goods.bean.Goods;
import com.joham.util.SelectBean;

import java.util.List;
import java.util.Map;

public interface GoodsDao {
	/**
	 * 查询物品列表
	 * @param map
	 * @return
	 */
	List<Object> findGoodsList(Map<String, Object> map);
	
	/**
	 * 获得商品总数
	 */
	int getGoodsCount(SelectBean selectBean);
	
	/**
	 * 删除商品
	 */
	int deleteGoodsById(Map<String, String> map);
	
	/**
	 * 修改商品
	 */
	int updateGoodsById(Goods goods);
	
	/**
	 * 根据ID查询
	 */
	Goods selectById(Long goodsId);
	/**
	 * 新增商品
	 */
	int addGoods(Goods goods);
	
	/**
	 * 查询全部商品信息用于导出
	 * @return
	 */
	List<Object> queryAllGoodsToExport();
	
	/**
	 * 根据id组查询查询商品信息
	 * @param map
	 * @return
	 */
	List<Object> queryGoodsListVoListForExportByGoodsIds(Map<String, Object> map);
}
