package com.konvi.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 商品类
 * @author konvi
 * @version 1.0
 * @date 2021/8/9
 */
@Entity
@Data
@DynamicUpdate
public class ProductCategory implements Serializable
{
    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;

    /**
     *类Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    /**
     * 类名
     */
    private String categoryName;

    /**
     *类编号
     */
    private Integer categoryType;


    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
