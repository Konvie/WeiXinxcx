package com.konvi.service.impl;

import com.konvi.dao.IProductInfoDAO;
import com.konvi.dto.CartDTO;
import com.konvi.entity.ProductInfo;
import com.konvi.enums.ProductStatusEnum;
import com.konvi.enums.ResultEnum;
import com.konvi.exception.SellException;
import com.konvi.service.IProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.SpringVersion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author konvi
 * @version 1.0
 * @date 2021/8/10
 */
@Service
@Transactional
public class ProductInfoServiceImpl implements IProductInfoService
{
    @Autowired
    private IProductInfoDAO productInfoDAO;

    /**
     * 根据商品ID查询商品信息
     * @param productId
     * @return
     */
    @Override
    public ProductInfo findById(String productId)
    {
        return productInfoDAO.findById(productId).orElse(null);
    }


    /**
     * 分页查询所有上架商品信息
     * request为PageRequest类型参数，表示分页的限制，从0开始，共有2页
     * @return
     */
    @Override
    public Page<ProductInfo> findUpAll()
    {
        PageRequest request = PageRequest.of(0,2);
        return productInfoDAO.queryByProductStatus(ProductStatusEnum.UP.getCode(),request);
    }

    /**
     * 分页查询所有商品信息
     * @param pageable
     * @return
     */
    @Override
    public Page<ProductInfo> findAll(Pageable pageable)
    {
        return productInfoDAO.findAll(pageable);
    }

    /**
     * 新增商品
     * @param productInfo
     * @return
     */
    @Override
    public ProductInfo save(ProductInfo productInfo)
    {
        return productInfoDAO.save(productInfo);
    }

    /**
     * 加库存
     * @param cartDTOList
     */
    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList)
    {
        for (CartDTO cartDTO : cartDTOList)
        {
            ProductInfo productInfo = productInfoDAO.findById(cartDTO.getProductId()).orElse(null);
            // 根据商品ID查询 商品信息
            if (productInfo == null)
            {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            //增加库存 增加后的库存= (商品信息中的)库存数量 + (购物车中的)商品数量
            Integer stockNum = productInfo.getProductStock() + cartDTO.getProductQuantity();
            // 把增加后的库存传入商品信息中的商品库存中
            productInfo.setProductStock(stockNum);
            // 用商品信息DAO方法更新商品库存
            productInfoDAO.save(productInfo);
        }
    }

    /**
     * 减库存
     * @param cartDTOList
     */
    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList)
    {
        for (CartDTO cartDTO : cartDTOList)
        {
            ProductInfo productInfo = productInfoDAO.findById(cartDTO.getProductId()).orElse(null);
            // 如果获取的商品信息为空 则抛出“商品不存在”异常
            if (productInfo == null)
            {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            // 剩余库存 剩余库存= (商品信息中的)库存数量 - (购物车中的)商品数量
            Integer remainStock = productInfo.getProductStock() - cartDTO.getProductQuantity();
            // 如果计算出的库存数量小于0 则抛出“商品库存不正确”异常
            if (remainStock < 0)
            {
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            // 把剩余库存传入 商品信息 中的“商品库存”中
            productInfo.setProductStock(remainStock);
            // 使用商品信息DAO的修改数据方法
            productInfoDAO.save(productInfo);
        }
    }

    /**
     * 商品上架
     * @param productId
     * @return
     */
    @Override
    public ProductInfo onSale(String productId)
    {
        // 根据商品ID查询 商品信息
        ProductInfo productInfo = productInfoDAO.findById(productId).orElse(null);

        // 如果商品不存在 则抛出"该商品不存在异常"
        if (productInfo == null)
        {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }

        // 更新商品状态 下架(1) -> 上架(0)
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        // 更新商品状态为 上架
        return productInfoDAO.save(productInfo);
    }

    /**
     * 商品下架
     * @param productId
     * @return
     */
    @Override
    public ProductInfo offSale(String productId)
    {
        // 根据商品ID查询 商品信息
        ProductInfo productInfo = productInfoDAO.findById(productId).orElse(null);
        // 如果商品不存在 则抛出"该商品不存在异常"
        if (productInfo == null)
        {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        // 更新商品状态 上架(0) -> 下架(1)
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        // 更新商品状态为 下架
        return productInfoDAO.save(productInfo);
    }
}
