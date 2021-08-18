package com.konvi.dao;

import com.konvi.entity.SellerInfo;
import com.konvi.utils.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 卖家信息DAO 测试类
 * @author konvi
 * @version 1.0
 * @date 2021/8/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoDAOTest
{
    @Autowired
    // 用户信息DAO
    private SellerInfoDAO sellerInfoDAO;

    /**
     * 添加用户信息
     */
    @Test
    public void save()
    {
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId(KeyUtil.genUniqueKey());
        sellerInfo.setUsername("aaa");
        sellerInfo.setPassword("123");
        sellerInfo.setOpenid("1234567");

        SellerInfo result = sellerInfoDAO.save(sellerInfo);
        Assert.assertNotNull(result);
    }

    /**
     * 根据openid查询用户信息
     */
    @Test
    public void queryByOpenid()
    {
        SellerInfo sellerInfo = sellerInfoDAO.queryByOpenid("1234567");
        Assert.assertEquals("1234567",sellerInfo.getOpenid());
    }
}