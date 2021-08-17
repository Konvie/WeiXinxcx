package com.konvi.service.impl;

import com.konvi.dao.IProductCategoryDAO;
import com.konvi.entity.ProductCategory;
import com.konvi.service.IProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品类 实现类
 * @author konvi
 * @version 1.0
 * @date 2021/8/9
 */
@Service
public class ProductCategoryServiceImpl implements IProductCategoryService
{
    @Autowired
    private IProductCategoryDAO productCategoryDAO;

    /**
     * 根据某个商品类ID查询商品类
     * @param categoryId
     * @return
     */
    @Override
    public ProductCategory findById(Integer categoryId)
    {
        return productCategoryDAO.findById(categoryId).orElse(null);
    }

    /**
     * 查询所有商品类
     * @return
     */
    @Override
    public List<ProductCategory> findAll()
    {
        return productCategoryDAO.findAll();
    }

    /**
     * 根据商品类ID列表查询商品类列表
     * @param categoryTypeList
     * @return
     */
    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList)
    {
        return productCategoryDAO.queryByCategoryTypeIn(categoryTypeList);
    }

    /**
     * 新增商品类
     * @param productCategory
     * @return
     */
    @Override
    public ProductCategory save(ProductCategory productCategory)
    {
        return productCategoryDAO.save(productCategory);
    }
}
