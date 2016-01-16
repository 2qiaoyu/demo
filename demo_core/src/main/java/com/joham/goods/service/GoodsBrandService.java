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
