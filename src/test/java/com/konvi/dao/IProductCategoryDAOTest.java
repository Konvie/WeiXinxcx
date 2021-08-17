package com.konvi.dao;

import com.konvi.entity.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

/**
 * 商品类DAO 测试类
 * @author konvi
 * @version 1.0
 * @date 2021/8/9
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class IProductCategoryDAOTest
{
    @Autowired
    IProductCategoryDAO productCategoryDAO;

    /**
     * 根据商品类ID查询商品类信息
     */
    @Test
    public void findByIdTest()
    {
        ProductCategory productCategory = productCategoryDAO.findById(1).orElse(null);
        System.out.println(productCategory.toString());
    }

    /**
     * 新增商品类
     */
    @Test
    public void saveTest()
    {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("女生最爱");
        productCategory.setCategoryType(10);
        ProductCategory queryProductCategory = productCategoryDAO.save(productCategory);
        Assert.assertNotNull(queryProductCategory);
        //Assert.assertArrayEquals(null, queryProductCategory);
    }

    /**
     * 修改商品类
     * 更新/修改 也是调用save方法，只不过需要更新主键categryId
     */
    @Test
    @Transactional //回滚数据（测试完回滚）
    public void saveTest2()
    {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryId(2);
        productCategory.setCategoryName("男生最爱");
        productCategory.setCategoryType(30);
        productCategoryDAO.save(productCategory);
    }

    /**
     * 验证updateTime修改时间没有自动更新
     */
    @Test
    public void saveTest3()
    {
        ProductCategory productCategory = productCategoryDAO.findById(2).orElse(null);
        productCategory.setCategoryType(7);
        productCategoryDAO.save(productCategory);
    }

    @Test
    public void queryCategoryTypeInTest()
    {
        List<Integer> list = Arrays.asList(1,2);

        List<ProductCategory>result = productCategoryDAO.queryByCategoryTypeIn(list);
        Assert.assertNotEquals(0,result.size());
    }
}
