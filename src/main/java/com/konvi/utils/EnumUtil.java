package com.konvi.utils;

import com.konvi.enums.CodeEnum;

/**
 * 枚举常量工具类
 * @author konvi
 * @version 1.0
 * @date 2021/8/15
 */
public class EnumUtil
{
    public static<T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass)
    {
        // 遍历枚举
        for (T eachEnum : enumClass.getEnumConstants())
        {
            // 判断Code是否相等
            if (code.equals(eachEnum.getCode()))
            {
                return eachEnum;
            }
        }
        return null;
    }
}
