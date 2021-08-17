package com.konvi.dao;

import com.konvi.entity.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

/**
 * 订单主表 测试类
 * @author konvi
 * @version 1.0
 * @date 2021/8/12
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IOrderMasterDAOTest
{
    @Autowired
    private IOrderMasterDAO orderMasterDAO;

    private final String OPENID = "1111111";

    /**
     * 测试订单主表保存方法
     */
    @Test
    public void saveTest()
    {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("156514548952");
        orderMaster.setBuyerName("无过");
        orderMaster.setBuyerPhone("13851235896");
        orderMaster.setBuyerAddress("南京");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setOrderAmount(new BigDecimal(8));

        OrderMaster saveOrderMaster = orderMasterDAO.save(orderMaster);
        Assert.assertNotNull(saveOrderMaster);
    }

    /**
     * 根据微信端的openid查询订单主表信息
     */
    @Test
    public void queryByBuyerOpenid()
    {
        PageRequest pageRequest = PageRequest.of(0,2);
        Page<OrderMaster> orderMasterResult = orderMasterDAO.queryByBuyerOpenid(OPENID,pageRequest);
        Assert.assertNotEquals(0,orderMasterResult.getTotalElements());
        System.out.println(orderMasterResult.getTotalElements());
    }
}