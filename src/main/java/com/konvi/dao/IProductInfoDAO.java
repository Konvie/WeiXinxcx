package com.konvi.dao;

import com.konvi.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author konvi
 * @version 1.0
 * @date 2021/8/10
 */
public interface IProductInfoDAO extends JpaRepository<ProductInfo, String>
{
    /**
     * 通过商品状态来查询上架的商品
     * productStatus代表商品状态，pageable为分页的限制
     * @param productStatus
     * @return
     */
    Page<ProductInfo> queryByProductStatus(Integer productStatus, Pageable pageable);
}
