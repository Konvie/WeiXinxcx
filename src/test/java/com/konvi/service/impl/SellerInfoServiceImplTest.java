package com.konvi.service.impl;

import com.konvi.entity.SellerInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 卖家信息Service实现类 测试类
 * @author konvi
 * @version 1.0
 * @date 2021/8/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SellerInfoServiceImplTest
{
    private static final String openid="1234567";

    /**
     * 卖家信息Service
     */
    @Autowired
    private SellerInfoServiceImpl sellerInfoService;

    /**
     * 通过openid查询卖家信息
     */
    @Test
    public void findSellerInfoByOpenid()
    {
        SellerInfo sellerInfo = sellerInfoService.findSellerInfoByOpenid(openid);
        Assert.assertEquals(openid,sellerInfo.getOpenid());
    }
}