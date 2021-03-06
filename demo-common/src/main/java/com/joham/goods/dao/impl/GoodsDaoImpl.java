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

	@Override
    public List<Object> findGoodsList(Map<String,Object> map) {
		return getSession().selectList("com.joham.goods.dao.GoodsMapper.findGoodsList",map);
	}

	@Override
    public int getGoodsCount(SelectBean selectBean) {
		return getSession().selectOne("com.joham.goods.dao.GoodsMapper.getGoodsCount",selectBean);
	}

	@Override
    public int deleteGoodsById(Map<String, String> map) {
		return getSession().delete("com.joham.goods.dao.GoodsMapper.deleteGoodsById",map);
	}

	@Override
    public int updateGoodsById(Goods goods) {
		return getSession().update("com.joham.goods.dao.GoodsMapper.updateGoodsById",goods);
	}

	@Override
    public Goods selectById(Long goodsId) {
		return getSession().selectOne("com.joham.goods.dao.GoodsMapper.selectById",goodsId);
	}

	@Override
    public int addGoods(Goods goods) {
		return getSession().insert("com.joham.goods.dao.GoodsMapper.addGoods", goods);
	}

	@Override
    public List<Object> queryAllGoodsToExport() {
		return getSession().selectList("com.joham.goods.dao.GoodsMapper.queryAllGoodsToExport");
	}

	@Override
    public List<Object> queryGoodsListVoListForExportByGoodsIds(
			Map<String, Object> map) {
		return getSession().selectList("com.joham.goods.dao.GoodsMapper.queryGoodsListVoListForExportByGoodsIds",map);
	}
}
