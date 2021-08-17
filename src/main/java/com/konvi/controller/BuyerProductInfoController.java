package com.konvi.controller;

import com.konvi.entity.ProductCategory;
import com.konvi.entity.ProductInfo;
import com.konvi.service.IProductCategoryService;
import com.konvi.service.IProductInfoService;
import com.konvi.utils.ResultVOUtil;
import com.konvi.vo.ProductInfoVO;
import com.konvi.vo.ProductVO;
import com.konvi.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 买家商品信息
 * @author konvi
 * @version 1.0
 * @date 2021/8/10
 */

@RestController //返回JSON数据
@RequestMapping("/buyer/product")
public class BuyerProductInfoController
{
    // 商品Service
    @Autowired
    private IProductInfoService productInfoService;

    // 商品类Service
    @Autowired
    private IProductCategoryService productCategoryService;
    /**
     * 查询商品列表方法
     * @return
     */
    // http://127.0.0.1:8080/sell/buyer/product/list
    @GetMapping("/list")
    public ResultVO List()
    {
        // (1)查询所有上架商品
        Page<ProductInfo> productInfoPage = productInfoService.findUpAll();

        // (2)查询商品类
        List<Integer> categoryTypeList = new ArrayList<Integer>();
        for(ProductInfo productInfo : productInfoPage)
        {
            // 此举将查询到的 上架商品的 商品类编号 设置到 categoryTypeList中
            categoryTypeList.add(productInfo.getCategoryType());
        }

        // 查询商品类列表 （查询不要放到for循环中）
        List<ProductCategory> productCategoryList = productCategoryService.findByCategoryTypeIn(categoryTypeList);

    /*    // 查询商品类列表  2.0（java8,Lambada）
        List<Integer> categoryTypeList = productInfoPage.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());
    */
        // (3)拼接数据
        // 创建一个空的商品列表
        List<ProductVO> productVOList = new ArrayList<ProductVO>();

        // 遍历商品类列表（查询不要放到for循环中）
        for(ProductCategory productCategory : productCategoryList)
        {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            // 创建一个空的商品详情列表
            List<ProductInfoVO> productInfoVOList = new ArrayList<ProductInfoVO>();
            // 遍历商品列表
            // start of 内循环
            for(ProductInfo productInfo2 : productInfoPage)
            {
                // 判断商品类是否相同
                if(productInfo2.getCategoryType().equals(productCategory.getCategoryType()))
                {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    // 将prodectInfo2 的值赋给 prodcutInfoVO
                    BeanUtils.copyProperties(productInfo2, productInfoVO);
                    // 将商品详情对象 放置到 商品详情列表 中
                    productInfoVOList.add(productInfoVO);
                }
            } // end of 内循环
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }// end of 外循环



    /*    ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");

        //ProductVO productVO = new ProductVO();
        //将ProductVO设置到data中
        //resultVO.setData(productVO);
        resultVO.setData(productVOList);

        //ProductInfoVO productInfoVO = new ProductInfoVO();
        //将ProductInfoVO商品详情设置到ProductVO的foods中
        //productVO.setProductInfoVOList(Arrays.asList(productInfoVO));
    */
        return ResultVOUtil.success(productVOList);
    }

}
