/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.joham.goods.service;

import com.joham.goods.bean.GoodsBrand;
import com.joham.goods.dao.GoodsBrandDao;

import java.util.List;

public interface GoodsBrandService {

    GoodsBrandDao getGoodsBrandDao();

    void setGoodsBrandDao(GoodsBrandDao goodsBrandDao);

    GoodsBrand findGoodsBrand(Long brandId);

    List<GoodsBrand> findGoodsBrandList();
}
