package com.joham.goods.bean;

import java.util.Date;


/**
 * 商品品牌类
 * 
 * @author joham-YuanKangKang
 * @since 2013年12月16日 下午7:28:54
 * @version 1.0
 */
public class GoodsBrand {
    // 品牌ID
    private Long brandId;
    // 品牌别名
    private String brandNickname;
    // 品牌名称
    private String brandName;
    // 品牌网址
    private String brandUrl;
    // 品牌LOGO
    private String brandLogo;
    // 品牌排序
    private Integer brandSort;
    //推荐到首页
    private String promIndex;
    // SEO优化标题
    private String brandSeoTitle;
    // SEO优化关键字
    private String brandSeoKeyword;
    // SEO优化介绍
    private String brandSeoDesc;
    // 删除标记 默认0 不删除
    private String brandDelflag;
    // 品牌创建人名称
    private String brandCreateName;
    // 品牌创建时间
    private Date brandCreateTime;
    // 品牌修改人名称
    private String brandModifiedName;
    // 品牌修改时间
    private Date brandModifiedTime;
    // 品牌删除人名称
    private String brandDelName;
    // 品牌删除时间
    private Date brandDelTime;
    // 品牌详细介绍
    private String brandDesc;

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandNickname() {
        return brandNickname;
    }

    public void setBrandNickname(String brandNickname) {
        this.brandNickname = brandNickname.trim();
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName.trim();
    }

    public String getBrandUrl() {
        return brandUrl;
    }

    public void setBrandUrl(String brandUrl) {
        this.brandUrl = brandUrl.trim();
    }

    public String getBrandLogo() {
        return brandLogo;
    }

    public void setBrandLogo(String brandLogo) {
        this.brandLogo = brandLogo;
    }

    public Integer getBrandSort() {
        return brandSort;
    }

    public void setBrandSort(Integer brandSort) {
        this.brandSort = brandSort;
    }

    public String getBrandSeoTitle() {
        return brandSeoTitle;
    }

    public void setBrandSeoTitle(String brandSeoTitle) {
        this.brandSeoTitle = brandSeoTitle.trim();
    }

    public String getBrandSeoKeyword() {
        return brandSeoKeyword;
    }

    public void setBrandSeoKeyword(String brandSeoKeyword) {
        this.brandSeoKeyword = brandSeoKeyword.trim();
    }

    public String getBrandSeoDesc() {
        return brandSeoDesc;
    }

    public void setBrandSeoDesc(String brandSeoDesc) {
        this.brandSeoDesc = brandSeoDesc.trim();
    }

    public String getBrandDelflag() {
        return brandDelflag;
    }

    public void setBrandDelflag(String brandDelflag) {
        this.brandDelflag = brandDelflag;
    }

    public String getBrandCreateName() {
        return brandCreateName;
    }

    public void setBrandCreateName(String brandCreateName) {
        this.brandCreateName = brandCreateName;
    }

    public Date getBrandCreateTime() {
        if (this.brandCreateTime!=null) {
            return new Date(this.brandCreateTime.getTime());
        }
        return null;
    }

    public void setBrandCreateTime(Date brandCreateTime) {
        if (brandCreateTime != null) {
            Date tEmp = (Date) brandCreateTime.clone();
            if (tEmp != null) {
                this.brandCreateTime = tEmp;
            }
        }
    }

    public String getBrandModifiedName() {
        return brandModifiedName;
    }

    public void setBrandModifiedName(String brandModifiedName) {
        this.brandModifiedName = brandModifiedName;
    }

    public Date getBrandModifiedTime() {
        if (this.brandModifiedTime!=null) {
            return new Date(this.brandModifiedTime.getTime());
        }
        return null;
    }

    public void setBrandModifiedTime(Date brandModifiedTime) {
        if (brandModifiedTime != null) {
            Date tEmp = (Date) brandModifiedTime.clone();
            if (tEmp != null) {
                this.brandModifiedTime = tEmp;
            }
        }
    }

    public String getBrandDelName() {
        return brandDelName;
    }

    public void setBrandDelName(String brandDelName) {
        this.brandDelName = brandDelName;
    }

    public Date getBrandDelTime() {
        if (this.brandDelTime!=null) {
            return new Date(this.brandDelTime.getTime());
        }
        return null;
    }

    public void setBrandDelTime(Date brandDelTime) {
        if (brandDelTime != null) {
            Date tEmp = (Date) brandDelTime.clone();
            if (tEmp != null) {
                this.brandDelTime = tEmp;
            }
        }
    }

    public String getBrandDesc() {
        return brandDesc;
    }

    public void setBrandDesc(String brandDesc) {
        this.brandDesc = brandDesc;
    }

    public String getPromIndex() {
        return promIndex;
    }

    public void setPromIndex(String promIndex) {
        this.promIndex = promIndex;
    }
    
}