package com.konvi.form;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 封装商品修改、商品新增 传递的数据
 * @author konvi
 * @version 1.0
 * @date 2021/8/17
 */
@Data
public class ProductForm
{
    /**
     * 商品ID
     */
    private String productId;

    /**
     * 商品名字
     */
    private String productName;

    /**
     * 商品价格
     */
    private BigDecimal productPrice;

    /**
     * 商品库存
     */
    private Integer productStock;

    /**
     * 商品描述
     */
    private String productDescription;

    /**
     * 商品图标
     */
    private String productIcon;

    /**
     * 商品类目编号
     */
    private Integer categoryType;

}
