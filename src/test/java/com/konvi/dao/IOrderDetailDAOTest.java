package com.konvi.dao;

import com.konvi.entity.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 订单详情DAO 测试类
 * @author konvi
 * @version 1.0
 * @date 2021/8/12
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IOrderDetailDAOTest
{
    @Autowired
    private IOrderDetailDAO orderDetailDAO;

    /**
     * 新增订单详情
     */
    @Test
    public void saveTest()
    {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("1234567812");
        orderDetail.setOrderId("1565145491");
        orderDetail.setProductIcon("http://xxx.jpg");
        orderDetail.setProductId("4");
        orderDetail.setProductName("快乐的双皮奶");
        orderDetail.setProductPrice(new BigDecimal(16));
        orderDetail.setProductQuantity(4);

        OrderDetail saveOrderDetail = orderDetailDAO.save(orderDetail);
        Assert.assertNotNull(saveOrderDetail);
    }

    /**
     * 根据订单ID查询订单详情
     */
    @Test
    public void queryByOrderId()
    {
        List<OrderDetail> orderDetailList = orderDetailDAO.queryByOrderId("1628845703374479443");
        Assert.assertNotEquals(0,orderDetailList.size());
    }
}