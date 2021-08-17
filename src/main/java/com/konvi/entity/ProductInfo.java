package com.konvi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.konvi.enums.ProductStatusEnum;
import com.konvi.utils.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品详情
 * @author konvi
 * @version 1.0
 * @date 2021/8/10
 */

@Entity
@Data
@DynamicUpdate
public class ProductInfo implements Serializable
{
    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;

    @Id
    /**
     * 商品ID 注意String类型无主键自增
     */
    private String productId;

    /**
     * 商品名
     */
    private String productName;

    /**
     * 商品单价
     */
    private BigDecimal productPrice;

    /**
     * 商品库存
     */
    private Integer productStock;

    /**
     * 商品描述
     */
    private String productDescription;


    /**
     * 商品图标
     */
    private String productIcon;


    /**
     * 商品状态 0-正常 1-下架
     */
    private Integer productStatus = ProductStatusEnum.UP.getCode();

    /**
     * 类编号
     */
    private Integer categoryType;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 获取产品枚举类: 商品的各个状态都在里面
     * @return
     */
    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum()
    {
        return EnumUtil.getByCode(productStatus, ProductStatusEnum.class);
    }

}
