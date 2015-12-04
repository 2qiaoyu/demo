/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.joham.goods.bean;

import java.math.BigDecimal;
import java.util.Date;

public class Goods {
	private Long goodsId;
    //分类ID
    private Long catId;
    //类型ID
    private Long typeId;
    //商品名称
    private String goodsName;
    //商品副标题
    private String goodsSubtitle;
    //商品编号
    private String goodsNo;
    //商品关键字
    private String goodsKeywords;
    //品牌ID
    private Long brandId;
    //商品简单介绍
    private String goodsBrief;
    //是否立即上架0不上架  1上架
    private String goodsAdded;
    //上架时间
    private Date goodsUptime;
    //销售价格
    private BigDecimal goodsPrice;
    //商品推荐0不推荐  1推荐
    private String goodsRecom;
    //默认的商品图片
    private String goodsImg;
    //商品评分
    private BigDecimal goodsScore;
    //商品计价单位
    private String goodsDeno;
    //SEOtitle
    private String goodsSeoTitle;
    //SEO keyword
    private String goodsSeoKeyword;
    //SEO desc
    private String goodsSeoDesc;
    //是否进行促销  0：不促销 1促销
    private String goodsProm;
    //无库存是否也可销售
    private String goodsInfoInstocksell;
    //是否可以使用优惠劵
    private String goodsUsecoupon;
    //创建商品人的名称
    private String goodsCreateName;
    //商品的创建时间
    private Date goodsCreateTime;
    //修改商品信息的名称
    private String goodsModifiedName;
    //修改商品信息的时间
    private Date goodsModifiedTime;
    //删除商品信息人的名称
    private String goodsDelName;
    //删除商品信息的时间
    private Date goodsDelTime;
    //删除标记
    private String goodsDelflag;
    //商品详细介绍
    private String goodsDetailDesc;
    //货品编号
    private String goodsInfoItemNo;
    //商品所属的商家Id
    private Long goodsBelo;
    //商品品牌
    private GoodsBrand goodsBrand;
    public GoodsBrand getGoodsBrand() {
		return goodsBrand;
	}
	public void setGoodsBrand(GoodsBrand goodsBrand) {
		this.goodsBrand = goodsBrand;
	}
	public Long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
	public Long getCatId() {
		return catId;
	}
	public void setCatId(Long catId) {
		this.catId = catId;
	}
	public Long getTypeId() {
		return typeId;
	}
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsSubtitle() {
		return goodsSubtitle;
	}
	public void setGoodsSubtitle(String goodsSubtitle) {
		this.goodsSubtitle = goodsSubtitle;
	}
	public String getGoodsNo() {
		return goodsNo;
	}
	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}
	public String getGoodsKeywords() {
		return goodsKeywords;
	}
	public void setGoodsKeywords(String goodsKeywords) {
		this.goodsKeywords = goodsKeywords;
	}
	public Long getBrandId() {
		return brandId;
	}
	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}
	public String getGoodsBrief() {
		return goodsBrief;
	}
	public void setGoodsBrief(String goodsBrief) {
		this.goodsBrief = goodsBrief;
	}
	public String getGoodsAdded() {
		return goodsAdded;
	}
	public void setGoodsAdded(String goodsAdded) {
		this.goodsAdded = goodsAdded;
	}
	public Date getGoodsUptime() {
		return goodsUptime;
	}
	public void setGoodsUptime(Date goodsUptime) {
		this.goodsUptime = goodsUptime;
	}
	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public String getGoodsRecom() {
		return goodsRecom;
	}
	public void setGoodsRecom(String goodsRecom) {
		this.goodsRecom = goodsRecom;
	}
	public String getGoodsImg() {
		return goodsImg;
	}
	public void setGoodsImg(String goodsImg) {
		this.goodsImg = goodsImg;
	}
	public BigDecimal getGoodsScore() {
		return goodsScore;
	}
	public void setGoodsScore(BigDecimal goodsScore) {
		this.goodsScore = goodsScore;
	}
	public String getGoodsDeno() {
		return goodsDeno;
	}
	public void setGoodsDeno(String goodsDeno) {
		this.goodsDeno = goodsDeno;
	}
	public String getGoodsSeoTitle() {
		return goodsSeoTitle;
	}
	public void setGoodsSeoTitle(String goodsSeoTitle) {
		this.goodsSeoTitle = goodsSeoTitle;
	}
	public String getGoodsSeoKeyword() {
		return goodsSeoKeyword;
	}
	public void setGoodsSeoKeyword(String goodsSeoKeyword) {
		this.goodsSeoKeyword = goodsSeoKeyword;
	}
	public String getGoodsSeoDesc() {
		return goodsSeoDesc;
	}
	public void setGoodsSeoDesc(String goodsSeoDesc) {
		this.goodsSeoDesc = goodsSeoDesc;
	}
	public String getGoodsProm() {
		return goodsProm;
	}
	public void setGoodsProm(String goodsProm) {
		this.goodsProm = goodsProm;
	}
	public String getGoodsInfoInstocksell() {
		return goodsInfoInstocksell;
	}
	public void setGoodsInfoInstocksell(String goodsInfoInstocksell) {
		this.goodsInfoInstocksell = goodsInfoInstocksell;
	}
	public String getGoodsUsecoupon() {
		return goodsUsecoupon;
	}
	public void setGoodsUsecoupon(String goodsUsecoupon) {
		this.goodsUsecoupon = goodsUsecoupon;
	}
	public String getGoodsCreateName() {
		return goodsCreateName;
	}
	public void setGoodsCreateName(String goodsCreateName) {
		this.goodsCreateName = goodsCreateName;
	}
	public Date getGoodsCreateTime() {
		return goodsCreateTime;
	}
	public void setGoodsCreateTime(Date goodsCreateTime) {
		this.goodsCreateTime = goodsCreateTime;
	}
	public String getGoodsModifiedName() {
		return goodsModifiedName;
	}
	public void setGoodsModifiedName(String goodsModifiedName) {
		this.goodsModifiedName = goodsModifiedName;
	}
	public Date getGoodsModifiedTime() {
		return goodsModifiedTime;
	}
	public void setGoodsModifiedTime(Date goodsModifiedTime) {
		this.goodsModifiedTime = goodsModifiedTime;
	}
	public String getGoodsDelName() {
		return goodsDelName;
	}
	public void setGoodsDelName(String goodsDelName) {
		this.goodsDelName = goodsDelName;
	}
	public Date getGoodsDelTime() {
		return goodsDelTime;
	}
	public void setGoodsDelTime(Date goodsDelTime) {
		this.goodsDelTime = goodsDelTime;
	}
	public String getGoodsDelflag() {
		return goodsDelflag;
	}
	public void setGoodsDelflag(String goodsDelflag) {
		this.goodsDelflag = goodsDelflag;
	}
	public String getGoodsDetailDesc() {
		return goodsDetailDesc;
	}
	public void setGoodsDetailDesc(String goodsDetailDesc) {
		this.goodsDetailDesc = goodsDetailDesc;
	}
	public String getGoodsInfoItemNo() {
		return goodsInfoItemNo;
	}
	public void setGoodsInfoItemNo(String goodsInfoItemNo) {
		this.goodsInfoItemNo = goodsInfoItemNo;
	}
	public Long getGoodsBelo() {
		return goodsBelo;
	}
	public void setGoodsBelo(Long goodsBelo) {
		this.goodsBelo = goodsBelo;
	}
	public String getGoodsBeloName() {
		return goodsBeloName;
	}
	public void setGoodsBeloName(String goodsBeloName) {
		this.goodsBeloName = goodsBeloName;
	}
	public String getIsThird() {
		return isThird;
	}
	public void setIsThird(String isThird) {
		this.isThird = isThird;
	}
	public Long getThirdCateId() {
		return thirdCateId;
	}
	public void setThirdCateId(Long thirdCateId) {
		this.thirdCateId = thirdCateId;
	}
	public String getMobileDesc() {
		return mobileDesc;
	}
	public void setMobileDesc(String mobileDesc) {
		this.mobileDesc = mobileDesc;
	}
	//商品所属的商家名称
    private String goodsBeloName;
    //是否是第三方商品
    private String isThird;
    //商家分类ID
    private Long thirdCateId;
    //手机端详细介绍
    private String mobileDesc;
}
