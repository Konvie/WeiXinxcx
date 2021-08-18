package com.konvi.controller;

import com.konvi.entity.ProductCategory;
import com.konvi.exception.SellException;
import com.konvi.form.CategoryForm;
import com.konvi.service.IProductCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 卖家类目Controller
 * @author konvi
 * @version 1.0
 * @date 2021/8/18
 */
@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController
{
    @Autowired
    private IProductCategoryService categoryService;

    /**
     * 查询商品类目列表
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(Map<String,Object> map)
    {
        // 查询所有商品类目
        List<ProductCategory> categoryList = categoryService.findAll();
        // 设置商品类目列表
        map.put("categoryList", categoryList);
        return new ModelAndView("category/list",map);
    }

    /**
     * 弹出 商品类目修改页面
     * @param categoryId
     * @param map
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId",required = false)Integer categoryId,Map<String,Object>map)
    {
        if (categoryId !=null)
        {
            ProductCategory productCategory = categoryService.findById(categoryId);
            map.put("category",productCategory);
        }
        return new ModelAndView("category/index",map);
    }

    /**
     * 保存/更新 商品类目
     * @param form
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm form, BindingResult bindingResult,Map<String,Object>map)
    {
        if (bindingResult.hasErrors())
        {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/category/index");
            return new ModelAndView("common/error",map);
        }

        ProductCategory productCategory = new ProductCategory();
        try
        {
            // 若form中商品类目ID为空
            if (form.getCategoryId() != null)
            {
                productCategory = categoryService.findById(form.getCategoryId());
            }
            BeanUtils.copyProperties(form,productCategory);
            // 保存商品类目信息
            categoryService.save(productCategory);
        } catch (SellException e)
        {
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/category/index");
            return new ModelAndView("common/error",map);
        }

        // 返回商品类目列表界面
        map.put("url","/sell/seller/category/list");
        return new ModelAndView("common/success",map);
    }

}
