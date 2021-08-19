package com.konvi.service;

import com.konvi.dto.CartDTO;
import com.konvi.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author konvi
 * @version 1.0
 * @date 2021/8/10
 */
public interface IProductInfoService
{
    /**
     * 根据商品ID查询商品信息
     * @param productId
     * @return
     */
    ProductInfo findById(String productId);

    /**
     * 查询所有上架商品信息
     * @return
     */
    List<ProductInfo> findUpAll();

    /**
     * 分页查询所有商品信息
     * @param pageable
     * @return
     */
    Page<ProductInfo> findAll(Pageable pageable);

    /**
     * 新增商品
     * @param productInfo
     * @return
     */
    ProductInfo save(ProductInfo productInfo);

    /**
     * 加库存
     * @param cartDTOList
     */
    void increaseStock(List<CartDTO> cartDTOList);

    /**
     * 减库存
     * @param cartDTOList
     */
    void decreaseStock(List<CartDTO> cartDTOList);

    /**
     * 商品上架
     * @param productId
     * @return
     */
    ProductInfo onSale(String productId);

    /**
     * 商品下架
     * @param prodcutId
     * @return
     */
    ProductInfo offSale(String productId);
}
