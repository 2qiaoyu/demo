/*
 * Copyright 2013 joham, Inc.All rights reserved.
 * joham PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.joham.goods.service;

import com.joham.goods.bean.Goods;
import com.joham.util.PageBean;
import com.joham.util.SelectBean;

import java.util.List;

/**
 * 商品service
 */
public interface GoodsService {
    PageBean findGoodsList(PageBean pb, SelectBean selectBean);

    int deleteGoodsById(Long goodsId);

    Goods findGoodsByGoodsId(Long goodsId);

    int find(SelectBean selectBean);

    int updateGoods(Goods goods);

    int batchDeleteGoods(Long[] goodsIds);

    int addGoods(Goods goods);

    List<Object> queryAllGoodsToExport();

    List<Object> queryGoodsListVoListForExportByGoodsIds(Long[] goodsIds);
}
