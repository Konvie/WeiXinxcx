package com.konvi.enums;

/**
 * 枚举类都必须实现CodeEnum中的方法
 * @author konvi
 * @version 1.0
 * @date 2021/8/15
 */
public interface CodeEnum<T>
{
    T getCode();
}
