package com.konvi.utils;

import java.util.Random;

/**
 * @author konvi
 * @version 1.0
 * @date 2021/8/12
 */
public class KeyUtil
{
    /**
     * 生成唯一的主键
     * 格式： 时间+随机数
     * synchronized 考虑到多线程并发问题
     * @return
     */
    public static synchronized String genUniqueKey()
    {
        Random random = new Random();
        // 生成6位的随机数
        Integer number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }
}
