package com.konvi.controller;

import com.konvi.entity.ProductCategory;
import com.konvi.entity.ProductInfo;
import com.konvi.exception.SellException;
import com.konvi.form.ProductForm;
import com.konvi.service.IProductCategoryService;
import com.konvi.service.IProductInfoService;
import com.konvi.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 卖家端商品 Controller
 * @author konvi
 * @version 1.0
 * @date 2021/8/17
 */
@Controller
@RequestMapping("/seller/product")
public class SellerProductInfoController
{
    /**
     * 商品Service
     */
    @Autowired
    private IProductInfoService productInfoService;

    /**
     * 商品类Service
     */
    @Autowired
    private IProductCategoryService productCategoryService;

    /**
     * 买家端 商品列表
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1")Integer page, @RequestParam(value = "size",defaultValue = "10")Integer size, Map<String,Object> map)
    {
        PageRequest request = PageRequest.of(page - 1,size);


        // 调用分页查询商品列表方式
        Page<ProductInfo>productInfoPageList = productInfoService.findAll(request);
        // 设置商品分页列表
        map.put("productInfoPageList",productInfoPageList);
        // 设置当前页
        map.put("currentPage",page);
        // 设置每页显示多少条数据
        map.put("pageSize",size);

        return new ModelAndView("product/list",map);
    }

    /**
     * 商品上架 http://127.0.0.1:8080/sell/seller/product/on_sale?productId=1
     * @param productId
     * @param map
     * @return
     */
    @RequestMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId")String productId,Map<String,Object>map)
    {
        try
        {
            productInfoService.onSale(productId);
        } catch (Exception e)
        {
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("/common/success",map);
    }

    /**
     * 商品下架 http://127.0.0.1:8080/sell/seller/product/off_sale?productId=1
     * @param productId
     * @param map
     * @return
     */
    @RequestMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("productId")String productId,Map<String,Object>map)
    {
        try
        {
            productInfoService.offSale(productId);
        } catch (Exception e)
        {
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("/common/success",map);
    }

    /**
     * 展示卖家商品/新增和更新页面
     * 新增商品和修改商品共用一个页面
     * 区别在于是否有商品ID
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId",required = false)String productId,Map<String,Object> map)
    {
        if (StringUtils.hasText(productId))
        {
            // 根据商品ID 查询某个商品的信息
            ProductInfo productInfo = productInfoService.findById(productId);
            // 设置产品信息
            map.put("product",productInfo);
        }
        // 查询所有的商品类目
        List<ProductCategory> productCategoryList = productCategoryService.findAll();
        map.put("categoryList",productCategoryList);

        return new ModelAndView("product/index",map);
    }

    /**
     * 商品新增/修改 功能
     * @param form
     * @param bindingResult
     * @param request
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm form, BindingResult bindingResult, HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        // 如果校验信息出错,则返回错误
        if (bindingResult.hasErrors())
        {
            session.setAttribute("msg",bindingResult.getFieldError().getDefaultMessage());
            session.setAttribute("url",request.getContextPath() + "/seller/product/index");
            return new ModelAndView("common/error");
        }
        ProductInfo productInfo = new ProductInfo();
        try
        {
            // 若product有值,说明是修改商品
            if (StringUtils.hasText(form.getProductId()))
            {
                productInfo = productInfoService.findById(form.getProductId());
            } else  // 若productId为空,说明是新增商品
            {
                form.setProductId(KeyUtil.genUniqueKey());
            }

            // form中的数据复制给productInfo
            BeanUtils.copyProperties(form,productInfo);
            // 保存/更新 商品信息
            productInfoService.save(productInfo);
        } catch (SellException e)
        {
            session.setAttribute("msg",e.getMessage());
            session.setAttribute("url",request.getContextPath() + "/seller/product/index");
        }
        session.setAttribute("url",request.getContextPath() + "/seller/product/list");
        return new ModelAndView("common/success");
    }
}
