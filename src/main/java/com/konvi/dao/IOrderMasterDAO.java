package com.konvi.dao;

import com.konvi.entity.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 订单主表DAO
 * @author konvi
 * @version 1.0
 * @date 2021/8/12
 */
public interface IOrderMasterDAO extends JpaRepository<OrderMaster, String>
{
    /**
     * 根据微信端的openid查询主表信息
     * @param buyerOpenid
     * @param pageable
     * @return
     */
    Page<OrderMaster> queryByBuyerOpenid(String buyerOpenid, Pageable pageable);
}
