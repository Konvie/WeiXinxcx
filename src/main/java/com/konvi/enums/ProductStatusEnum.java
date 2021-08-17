package com.konvi.enums;

import lombok.Getter;

/**
 * 上下架状态枚举
 * @author konvi
 * @version 1.0
 * @date 2021/8/10
 */
@Getter
public enum ProductStatusEnum
{
    UP(0,"上架"),
    DOWN(1,"下架");


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
    ProductStatusEnum(Integer code, String message)
    {
        this.code = code;
        this.message = message;
    }
}
