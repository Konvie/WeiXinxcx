package com.konvi.entity;

import com.konvi.enums.ProductStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

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


}
