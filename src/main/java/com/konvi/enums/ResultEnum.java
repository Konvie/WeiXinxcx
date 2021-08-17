package com.konvi.enums;

import lombok.Getter;

/**
 * @author konvi
 * @version 1.0
 * @date 2021/8/12
 */
@Getter
public enum ResultEnum
{
    PRODUCT_NOT_EXIST(100,"该商品不存在"),

    PRODUCT_STOCK_ERROR(101,"商品库存不正确"),

    ORDER_NOT_EXIST(102,"订单不存在"),

    ORDERDETAIL_NOT_EXIST(103,"订单详情不存在"),

    ORDER_STATUS_ERROR(104,"订单状态不正确"),

    ORDER_UPDATE_FAIL(105,"订单更新失败"),

    ORDER_DETAIL_EMPTY(106,"订单中无订单详情"),

    ORDER_PAY_STATUS_ERROR(107,"订单支付状态不正确"),

    PARAM_ERROR(108,"参数不正确"),

    CART_EMPTY(109,"购物车为空"),

    ORDER_OWNER_ERROR(110,"该订单不属于当前用户"),

    ORDER_CANCEL_SUCCESS(111,"订单取消成功"),

    ORDER_FINISH_SUCCESS(112,"订单完成成功");

    /**
     * 编号
     */
    private Integer code;

    /**
     * 代表信息
     */
    private String message;

    /**
     * 重构枚举类
     * @param code
     * @param message
     */
    ResultEnum(Integer code, String message)
    {
        this.code = code;
        this.message = message;
    }
}
