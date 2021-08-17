package com.konvi.service.impl;

import com.konvi.entity.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * 商品类Service实现类的测试类
 * @author konvi
 * @version 1.0
 * @date 2021/8/9
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceImplTest
{
    @Autowired
    private ProductCategoryServiceImpl productCategoryService;

    /**
     * 根据某个商品类ID查询商品类
     */
    @Test
    public void findById() throws Exception
    {
        ProductCategory productCategory = productCategoryService.findById(1);
        Assert.assertEquals(new Integer(1),productCategory.getCategoryId());
    }

    /**
     *查询所有商品类
     */
    @Test
    public void findAll() throws Exception
    {
        List<ProductCategory> productCategoryList = productCategoryService.findAll();
        Assert.assertNotEquals(0,productCategoryList.size());
    }

    /**
     *根据商品类ID列表查询商品类列表
     */
    @Test
    public void findByCategoryTypeIn() throws Exception
    {
        List<ProductCategory> productCategoryList = productCategoryService.findByCategoryTypeIn(Arrays.asList(1,2));
        Assert.assertNotEquals(0,productCategoryList.size());
    }

    /**
     *新增商品类
     */
    @Test
    public void save() throws Exception
    {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("男生专享");
        productCategory.setCategoryType(6);

        ProductCategory saveProductCategory = productCategoryService.save(productCategory);
        Assert.assertNotNull(saveProductCategory);
    }
}