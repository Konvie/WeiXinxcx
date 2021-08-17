package com.konvi.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 商品详情
 * @author konvi
 * @version 1.0
 * @date 2021/8/10
 */
@Data
public class ProductInfoVO implements Serializable
{
    /**
     * 商品ID 注意String类型无主键自增
     */
    @JsonProperty("id")
    private String productId;

    /**
     * 商品名
     */
    @JsonProperty("name")
    private String productName;

    /**
     * 商品单价
     */
    @JsonProperty("price")
    private BigDecimal productPrice;

    /**
     * 商品描述
     */
    @JsonProperty("description")
    private String productDescription;


    /**
     * 商品图标
     */
    @JsonProperty("icon")
    private String productIcon;

}
