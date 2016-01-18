package com.joham.goods.service.impl;

import com.joham.goods.bean.Goods;
import com.joham.goods.dao.GoodsDao;
import com.joham.goods.service.GoodsService;
import com.joham.util.PageBean;
import com.joham.util.SelectBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by joham on 2015/11/28.
 */
@Service("GoodsService")
public class GoodsServiceImpl implements GoodsService {
    @Resource(name = "GoodsDao")
    private GoodsDao goodsDao;

    @Override
    public PageBean findGoodsList(PageBean pb, SelectBean selectBean) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Object> goodsList = new ArrayList<Object>();
        try {
            map.put("startRowNum", pb.getStartRowNum());
            map.put("endRowNum", pb.getEndRowNum());
            map.put("condition", selectBean.getCondition());
            map.put("searchText", selectBean.getSearchText());
            goodsList = this.goodsDao.findGoodsList(map);
            pb.setList(goodsList);
            pb.setRows(this.goodsDao.getGoodsCount(selectBean));
        } finally {
            map = null;
            goodsList = null;
        }
        return pb;
    }

    @Override
    public int deleteGoodsById(Long goodsId) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("goodsId", goodsId.toString());
        return this.goodsDao.deleteGoodsById(map);
    }

    @Override
    public Goods findGoodsByGoodsId(Long goodsId) {
        return this.goodsDao.selectById(goodsId);
    }

    public int find(SelectBean selectBean) {
        return this.goodsDao.getGoodsCount(selectBean);
    }

    @Override
    @Transactional
    public int updateGoods(Goods goods) {
        return this.goodsDao.updateGoodsById(goods);
    }

    @Override
    public int batchDeleteGoods(Long[] goodsIds) {
        Map<String, String> map = new HashMap<String, String>();
        int count = 0;
        for (int i = 0; i < goodsIds.length; i++) {
            map.put("goodsId", goodsIds[i].toString());
            count += this.goodsDao.deleteGoodsById(map);
        }
        return count;
    }

    @Override
    public int addGoods(Goods goods) {
        return this.goodsDao.addGoods(goods);
    }

    @Override
    public List<Object> queryAllGoodsToExport() {
        return this.goodsDao.queryAllGoodsToExport();
    }

    @Override
    public List<Object> queryGoodsListVoListForExportByGoodsIds(Long[] goodsIds) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("goodsIds", goodsIds);
        List<Object> list = this.goodsDao
                .queryGoodsListVoListForExportByGoodsIds(map);
        return list;
    }
}
