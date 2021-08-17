package com.konvi.dto;

import lombok.Data;

/**
 * 购物车DTO -- 购物车数据传输对象
 * @author konvi
 * @version 1.0
 * @date 2021/8/12
 */
@Data
public class CartDTO
{
    private String productId;

    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity)
    {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
