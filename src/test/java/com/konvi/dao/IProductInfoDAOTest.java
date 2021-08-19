package com.konvi.dao;

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
import java.util.List;

import static org.junit.Assert.*;

/**
 * 商品信息DAO 测试类
 * @author konvi
 * @version 1.0
 * @date 2021/8/10
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IProductInfoDAOTest
{
    @Autowired
    private IProductInfoDAO productInfoDAO;

    /**
     * 测试新增商品
     * @throws Exception
     */
    @Test
    public void saveTest() throws Exception
    {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("1");
        productInfo.setProductName("快乐的双皮奶");
        productInfo.setProductPrice(new BigDecimal(4.0));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("性价比高");
        productInfo.setProductIcon("http://xxx.jpg");
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        productInfo.setCategoryType(2);

        ProductInfo productInfoSave = productInfoDAO.save(productInfo);
        Assert.assertNotNull(productInfoSave);
    }

    /**
     * 通过商品的状态查看上架的商品
     */
    @Test
    public void queryByProductStatus()
    {

        List<ProductInfo> productInfoList = productInfoDAO.queryByProductStatus(0);
        Assert.assertNotEquals(0,productInfoList.size());
    }
}