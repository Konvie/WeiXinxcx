package com.konvi.service.impl;

import com.konvi.dto.OrderDTO;
import com.konvi.entity.OrderMaster;
import com.konvi.enums.ResultEnum;
import com.konvi.exception.SellException;
import com.konvi.service.IBuyerService;
import com.konvi.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 买家Service 实现类
 * @author konvi
 * @version 1.0
 * @date 2021/8/14
 */
@Service
@Slf4j
public class BuyerServiceImpl implements IBuyerService
{
    @Autowired
    private IOrderService orderService;

    /**
     * 查询一个订单
     * @param openid
     * @param orderId
     * @return
     */
    @Override
    public OrderDTO findOrderOne(String openid, String orderId)
    {
        return checkOrderOwner(openid, orderId);
    }

    /**
     * 判断订单所属用户
     * @param openid
     * @param orderId
     * @return
     */
    public OrderDTO checkOrderOwner(String openid, String orderId) {
        // 根据订单ID查询单个订单
        OrderDTO orderDTO = orderService.findById(orderId);
        if (orderDTO == null)
        {
            return null;
        }
        // 判断是否是该用户订单
        if (!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid))
        {
            log.error("[查询订单]订单的openid不一致,openid={},orderDTO={}", openid,orderDTO);
            // 抛出自定义异常:"该订单不属于该用户"
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }

    /**
     * 取消订单
     * @param openid
     * @param orderId
     * @return
     */
    @Override
    public OrderDTO cancelOrder(String openid, String orderId)
    {
        // 根据订单ID查询单个订单
        OrderDTO orderDTO = orderService.findById(orderId);
        if (orderDTO == null)
        {
            log.error("[取消订单]查不到该订单,orderId={}",orderId);
            return null;
        }
        return orderService.cancel(orderDTO);
    }
}
