package com.konvi.service;

import com.konvi.entity.ProductCategory;

import java.util.List;

/**
 * 商品类 Service
 * @author konvi
 * @version 1.0
 * @date 2021/8/9
 */
public interface IProductCategoryService
{
    /**
     * 根据某个商品类ID查询商品类
     * @param categoryId
     * @return
     */
    ProductCategory findById(Integer categoryId);

    /**
     * 查询所有商品类
     * @return
     */
    List<ProductCategory> findAll();

    /**
     * 根据商品类ID列表查询商品类列表
     * @param categoryTypeList
     * @return
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    /**
     * 新增商品类
     * @param productCategory
     * @return
     */
    ProductCategory save(ProductCategory productCategory);
}
