package com.konvi.enums;

import lombok.Getter;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * 支付状态枚举
 * @author konvi
 * @version 1.0
 * @date 2021/8/12
 */
@Getter
public enum PayStatusEnum implements CodeEnum<Integer>
{
    WAIT(0,"等待支付"),
    SUCCEED(1,"支付成功");

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
    PayStatusEnum(Integer code,String message)
    {
        this.code = code;
        this.message = message;
    }
}
