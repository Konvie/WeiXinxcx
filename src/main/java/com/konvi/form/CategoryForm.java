package com.konvi.form;

import lombok.Data;

/**
 * 封装 修改/新增商品类目
 * @author konvi
 * @version 1.0
 * @date 2021/8/18
 */
@Data
public class CategoryForm
{
    /**
     * 类目ID
     */
    private Integer categoryId;

    /**
     * 类目名称
     */
    private String categoryName;

    /**
     * 类目编号
     */
    private Integer categoryType;

}
