package com.konvi.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品对象（包含商品类）
 * @author konvi
 * @version 1.0
 * @date 2021/8/10
 */
@Data
public class ProductVO implements Serializable
{
    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;

    // 商品类名字
    @JsonProperty("name")
    private String categoryName;

    // 商品类编号
    @JsonProperty("type")
    private Integer categoryType;

    // 商品详情
    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}
