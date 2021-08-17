package com.konvi.service;

import com.konvi.dto.OrderDTO;

/**
 * 买家Service
 * @author konvi
 * @version 1.0
 * @date 2021/8/14
 */
public interface IBuyerService
{
    /**
     * 查询一个订单
     * @param openid
     * @param orderId
     * @return
     */
    OrderDTO findOrderOne(String openid, String orderId);

    /**
     * 取消订单
     * @param openid
     * @param orderId
     * @return
     */
    OrderDTO cancelOrder(String openid, String orderId);
}
