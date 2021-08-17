package com.konvi.service.impl;

import com.konvi.entity.ProductInfo;
import com.konvi.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * 商品信息Service实现类的测试类
 * @author konvi
 * @version 1.0
 * @date 2021/8/10
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest
{
    @Autowired
    private ProductInfoServiceImpl productInfoService;

    /**
     * 根据商品ID查询商品信息
     */
    @Test
    public void findById()
    {
        ProductInfo productInfo = productInfoService.findById("1");
        Assert.assertNotEquals("1",productInfo.getProductId());
    }

    /**
     * 分页查询所有上架商品信息
     */
    @Test
    public void findUpAll()
    {
        Page<ProductInfo> productInfoPage = productInfoService.findUpAll();
        Assert.assertNotEquals(0,productInfoPage.getContent().size());
    }

    /**
     * 分页查询所有商品信息
     */
    @Test
    public void findAll()
    {
        PageRequest request = PageRequest.of(1,2);
        Page<ProductInfo> productInfoPage = productInfoService.findAll(request);
        Assert.assertNotEquals(0,productInfoPage.getTotalElements());
    }

    /**
     * 新增商品
     */
    @Test
    public void save()
    {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("5");
        productInfo.setProductName("生气的柠檬炸弹");
        productInfo.setProductPrice(new BigDecimal(16.5));
        productInfo.setProductStock(250);
        productInfo.setProductDescription("有钱不想买，没钱舍不得");
        productInfo.setProductIcon("http://yyz.jpg");
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfo.setCategoryType(2);

        ProductInfo result = productInfoService.save(productInfo);
        Assert.assertNotNull(result);
    }
}