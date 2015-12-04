package com.joham.goods.service.impl;

import com.joham.goods.bean.GoodsBrand;
import com.joham.goods.dao.GoodsBrandDao;
import com.joham.goods.service.GoodsBrandService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by joham on 2015/11/28.
 */
@Service("GoodsBrandService")
public class GoodsBrandServiceImpl implements GoodsBrandService {
    @Resource(name = "GoodsBrandDao")
    private GoodsBrandDao goodsBrandDao;

    @Override
    public GoodsBrandDao getGoodsBrandDao() {
        return goodsBrandDao;
    }

    @Override
    public void setGoodsBrandDao(GoodsBrandDao goodsBrandDao) {
        this.goodsBrandDao = goodsBrandDao;
    }

    @Override
    public GoodsBrand findGoodsBrand(Long brandId) {
        return this.goodsBrandDao.selectByPrimaryKey(brandId);
    }

    @Override
    public List<GoodsBrand> findGoodsBrandList() {
        return goodsBrandDao.findGoodsBrandList();
    }
}
