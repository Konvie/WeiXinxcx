package com.konvi.service;

import com.konvi.entity.SellerInfo;

/**
 * 卖家端用户信息 Service
 * @author konvi
 * @version 1.0
 * @date 2021/8/18
 */
public interface ISellerInfoService
{
    /**
     * 通过openid查询卖家信息
     * @param openid
     * @return
     */
    SellerInfo findSellerInfoByOpenid(String openid);
}
