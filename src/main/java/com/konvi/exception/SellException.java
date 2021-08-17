package com.konvi.exception;

import com.konvi.enums.ResultEnum;
import lombok.Getter;

/**
 * 自定义异常类
 * @author konvi
 * @version 1.0
 * @date 2021/8/12
 */
@Getter
public class SellException extends RuntimeException
{
    private Integer code;

    /**
     * 单参构造器
     * @param resultEnum
     */
    public SellException(ResultEnum resultEnum)
    {
        super(resultEnum.getMessage());

        this.code = resultEnum.getCode();
    }

    /**
     * 两参构造器
     * @param code
     * @param message
     */
    public SellException(Integer code, String message)
    {
        super(message);
        this.code = code;
    }

}
