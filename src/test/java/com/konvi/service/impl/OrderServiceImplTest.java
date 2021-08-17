package com.konvi.service.impl;

import com.konvi.dto.OrderDTO;
import com.konvi.entity.OrderDetail;
import com.konvi.entity.OrderMaster;
import com.konvi.enums.OrderStatusEnum;
import com.konvi.enums.PayStatusEnum;
import com.konvi.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单Service实现类的测试类
 * @author konvi
 * @version 1.0
 * @date 2021/8/13
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest
{
    @Autowired
    private IOrderService orderService;

    private final String BUYER_OPENID = "1101110";

    //订单ID
    private final String ORDER_ID = "1628845703374479443";

    /**
     * 创建订单
      */
    @Test
    public void create()
    {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("无念");
        orderDTO.setBuyerAddress("武汉");
        orderDTO.setBuyerPhone("19351486984");
        orderDTO.setBuyerOpenid(BUYER_OPENID);
        List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("1");       // 设置商品ID 库中真实存在
        o1.setProductQuantity(1);   // 设置购买数量

        OrderDetail o2 = new OrderDetail();
        o2.setProductId("2");
        o2.setProductQuantity(2);

        orderDetailList.add(o1);
        orderDetailList.add(o2);

        orderDTO.setOrderDetailList(orderDetailList);       // 设置订单详情列表

        OrderDTO result = orderService.create(orderDTO);  // 创建订单

        log.info("[创建订单] result={}", result);
        Assert.assertNotNull(result);

    }


    /**
     * 查找单个订单
     */
    @Test
    public void findById()
    {
        OrderDTO orderDTO = orderService.findById(ORDER_ID);
        log.info("[查询单个订单] orderDTO={}", orderDTO);
        Assert.assertEquals(ORDER_ID,orderDTO.getOrderId());
    }

    /**
     * 查询订单列表
     */
    @Test
    public void findList()
    {
        PageRequest request = PageRequest.of(0,2);
        Page<OrderDTO> orderDTOPage = orderService.findList(BUYER_OPENID, request);
        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());
    }

    /**
     * 取消订单
     */
    @Test
    public void cancel()
    {
        OrderDTO orderDTO = orderService.findById(ORDER_ID);
        OrderDTO result = orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),result.getOrderStatus());
    }

    /**
     * 完成订单
     */
    @Test
    public void finish()
    {
        OrderDTO orderDTO = orderService.findById(ORDER_ID);
        OrderDTO result = orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),result.getOrderStatus());
    }

    /**
     * 支付订单
     */
    @Test
    public void paid()
    {
        OrderDTO orderDTO = orderService.findById(ORDER_ID);
        OrderDTO result = orderService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCEED.getCode(),result.getPayStatus());
    }

    /**
     * 带分页查询所有订单列表
     */
    @Test
    public void list()
    {
        PageRequest request = PageRequest.of(0,2);
        Page<OrderDTO> orderDTOPageList = orderService.findList(request);
        //Assert.assertNotEquals(0,orderMasterPageList.getTotalElements());

        //Assert.assertTrue(String message,boolean condition);
        Assert.assertTrue("带分页查询所有订单列表",orderDTOPageList.getTotalElements() > 0);
    }
}