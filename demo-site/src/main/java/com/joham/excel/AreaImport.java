package com.joham.excel;

import com.joham.excel.annotaion.Excel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 地区
 *
 * @author joham
 */
@Data
public class AreaImport {

    /**
     * 城市
     */
    @Excel(column = 1, required = true, title = "城市")
    private String city;

    /**
     * 区县
     */
    @Excel(column = 2, title = "区县")
    private String district;

    /**
     * 省
     */
    @Excel(column = 3, title = "省")
    private String province;

    /**
     * 邮编
     */
    @Excel(column = 4, title = "邮编")
    private String postCode;

    /**
     * 价格
     */
    @Excel(column = 5, title = "价格")
    private BigDecimal price;
}
