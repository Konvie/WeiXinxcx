package com.konvi.dao;

import com.konvi.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 订单详情DAO
 * @author konvi
 * @version 1.0
 * @date 2021/8/12
 */
public interface IOrderDetailDAO extends JpaRepository<OrderDetail, String>
{

    /**
     * 根据订单ID查询订单详细信息
     * @param orderId
     * @return
     */
    List<OrderDetail> queryByOrderId(String orderId);
}
