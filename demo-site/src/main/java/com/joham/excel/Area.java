package com.joham.excel;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author joham
 */
@Data
public class Area {

    private String city;

    private String district;

    private String province;

    private String postCode;

    private BigDecimal price;
}
