package com.konvi.service.impl;

import com.konvi.dao.SellerInfoDAO;
import com.konvi.entity.SellerInfo;
import com.konvi.service.ISellerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 卖家信息Service实现类
 * @author konvi
 * @version 1.0
 * @date 2021/8/18
 */
@Service
@Transactional
public class SellerInfoServiceImpl implements ISellerInfoService
{
    /**
     * 卖家信息DAO
     */
    @Autowired
    private SellerInfoDAO sellerInfoDAO;

    /**
     * 通过openi查询卖家信息
     * @param openid
     * @return
     */
    public SellerInfo findSellerInfoByOpenid(String openid)
    {
        return sellerInfoDAO.queryByOpenid(openid);
    }
}
