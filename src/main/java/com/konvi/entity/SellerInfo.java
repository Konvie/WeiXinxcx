package com.konvi.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 卖家端信息表
 * @author konvi
 * @version 1.0
 * @date 2021/8/18
 */
@Entity
@Data
public class SellerInfo implements Serializable
{
    /**
     * 卖家信息表
     */
    @Id
    private String sellerId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 扫码登录
     */
    private String openid;
}
