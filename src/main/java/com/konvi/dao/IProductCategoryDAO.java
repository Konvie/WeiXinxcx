package com.konvi.dao;

import com.konvi.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * @author konvi
 * @version 1.0
 * @date 2021/8/9
 */
public interface IProductCategoryDAO extends JpaRepository<ProductCategory, Integer>
{

    /**
     * 一次性 根据商品类查询
     * @param categoryTypeList
     * @return
     */
    List<ProductCategory> queryByCategoryTypeIn(List<Integer>categoryTypeList);
}
