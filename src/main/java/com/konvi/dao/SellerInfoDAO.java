package com.konvi.dao;

import com.konvi.entity.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 卖家信息DAO
 * @author konvi
 * @version 1.0
 * @date 2021/8/18
 */
public interface SellerInfoDAO extends JpaRepository<SellerInfo, String>
{
    /**
     * 根据openid查询用户信息
     * @param openid
     * @return
     */
    SellerInfo queryByOpenid(String openid);
}
