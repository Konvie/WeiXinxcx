package com.konvi.enums;

import lombok.Getter;

/**
 * 订单状态枚举
 * @author konvi
 * @version 1.0
 * @date 2021/8/12
 */

@Getter
public enum OrderStatusEnum implements CodeEnum<Integer>
{
    NEW(0,"新订单"),
    FINISHED(1,"已完成"),
    CANCEL(2,"已取消");

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
    OrderStatusEnum(Integer code, String message)
    {
        this.code = code;
        this.message = message;
    }
}
