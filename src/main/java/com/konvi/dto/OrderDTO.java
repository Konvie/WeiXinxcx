package com.konvi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.konvi.entity.OrderDetail;
import com.konvi.enums.OrderStatusEnum;
import com.konvi.enums.PayStatusEnum;
import com.konvi.utils.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author konvi
 * @version 1.0
 * @date 2021/8/16
 */

@Data
@DynamicUpdate
public class OrderDTO implements Serializable
{
    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 买家名称
     */
    private String buyerName;

    /**
     * 买家手机号
     */
    private String buyerPhone;

    /**
     * 买家地址
     */
    private String buyerAddress;

    /**
     * 买家微信Openid
     */
    private String buyerOpenid;

    /**
     * 订单总金额
     */
    private BigDecimal orderAmount;

    /**
     * 订单状态 ，默认为0新下单
     */
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /**
     * 支付状态，默认为0等待支付
     */
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


    /**
     * 订单详情列表 实现方式1
     * 字段在表中并不存在
     * 一个 订单 下可能会有多个 订单详情
     */
    @Transient
    private List<OrderDetail> orderDetailList;

    /**
     * 获取 订单状态 枚举常量类
     * @return
     */
    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum()
    {
        return EnumUtil.getByCode(orderStatus,OrderStatusEnum.class);
    }

    /**
     * 获取 支付装填 枚举常量类
     * @return
     */
    @JsonIgnore
    public PayStatusEnum getPayStatusEnum()
    {
        return EnumUtil.getByCode(payStatus,PayStatusEnum.class);
    }
}
