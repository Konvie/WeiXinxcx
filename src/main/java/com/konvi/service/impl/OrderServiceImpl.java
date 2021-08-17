package com.konvi.service.impl;

import com.konvi.converter.OrderMaster2OrderDTOConverter;
import com.konvi.dao.IOrderDetailDAO;
import com.konvi.dao.IOrderMasterDAO;
import com.konvi.dto.CartDTO;
import com.konvi.dto.OrderDTO;
import com.konvi.entity.OrderDetail;
import com.konvi.entity.OrderMaster;
import com.konvi.entity.ProductInfo;
import com.konvi.enums.OrderStatusEnum;
import com.konvi.enums.PayStatusEnum;
import com.konvi.enums.ResultEnum;
import com.konvi.exception.SellException;
import com.konvi.service.IOrderService;
import com.konvi.service.IProductInfoService;
import com.konvi.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单Service 实现类
 * @author konvi
 * @version 1.0
 * @date 2021/8/12
 */
@Service
@Slf4j
public class OrderServiceImpl implements IOrderService
{
    // 新建商品信息Service
    @Autowired
    private IProductInfoService productInfoService;

    // 订单详情DAO
    @Autowired
    private IOrderDetailDAO orderDetailDAO;

    // 订单主表DAO
    @Autowired
    private IOrderMasterDAO orderMasterDAO;

    /**
     * 创建订单
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO)
    {
        // 随机生成orderId
        String orderId = KeyUtil.genUniqueKey();

        // 订单总价
        BigDecimal orderTotalPrice = new BigDecimal(BigInteger.ZERO);

        //List<CartDTO> cartDTOList = new ArrayList<CartDTO>(); //购物车列表

        // 1.查询商品（数量，价格）
        // 遍历订单主表中的每个订单详情（一个订单内可能含有多个订单详情）
        for(OrderDetail orderDetail : orderDTO.getOrderDetailList())
        {
            // 从订单详情中获取商品ID --> 通过商品ID获取商品信息
            ProductInfo productInfo = productInfoService.findById(orderDetail.getProductId());
            // 如果获取的商品信息为空 则抛出“商品不存在”异常
            if (productInfo == null)
            {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            // 2.计算订单总价
            // 一条订单详情的价格 = （商品信息中的）商品单价 * （订单详情中的）商品个数
            // productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity()));
            // 注意！orderAmount为orderMaster中的一个成员变量，也就是说总价之后会传入orderMaster中
            orderTotalPrice = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderTotalPrice);

            // 订单详情入库
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            // 将商品信息中的数据都传到 订单详情 中
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetailDAO.save(orderDetail);

            //CartDTO cartDTO = new CartDTO(orderDetail.getProductId(),orderDetail.getProductQuantity());
            //cartDTOList.add(cartDTO);
        } //end of for

        // 3.将订单信息写入数据库（OrderMaster及OrderDetail）
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId); //订单编号

        // 将orderDTO对象中的内容复制到OrderMaster对象中
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(orderTotalPrice); //订单总价
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());  //订单状态
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode()); //支付状态
        orderMasterDAO.save(orderMaster); //orderMasterDAO中的新增订单方法


        // 4.扣库存
        // Lambda表达式 实现将商品ID和商品数量传递到cartDTOList中
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e -> new CartDTO(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());

        // 商品信息Service中的减库存方法
        productInfoService.decreaseStock(cartDTOList);
        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    }

    /**
     * 查询单个订单
     * @param orderId
     * @return
     */
    @Override
    public OrderDTO findById(String orderId)
    {
        // 根据订单ID 查询 订单
        OrderMaster orderMaster = orderMasterDAO.findById(orderId).orElse(null);
        if (orderMaster == null)
        {
            // 抛出自定义异常 "订单不存在"
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        // 根据订单ID 在订单详情列表中查找 订单详情
        List<OrderDetail> orderDetailList = orderDetailDAO.queryByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList))
        {
            // 抛出自定义异常 "订单详情不存在"
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderDetailList(orderDetailList);
        BeanUtils.copyProperties(orderMaster, orderDTO);

        return orderDTO;
    }

    /**
     * 查询订单列表
     * @param buyerOpenid
     * @param pageable
     * @return
     */
    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable)
    {
        // 根据微信端的openid 查询订单主表的信息
        Page<OrderMaster> orderMasterPage = orderMasterDAO.queryByBuyerOpenid(buyerOpenid,pageable);

        // OrderMaster -> OrderDTO
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());

        return new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }

    /**
     * 取消订单
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO)
    {
        // (1)判断订单状态 若不是新订单,则不能取消
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode()))
        {
            log.error("[取消订单] 订单状态不正确, orderId={} orderStatus={}", orderDTO.getOrderId(),orderDTO.getOrderStatus());

            // 抛出自定义异常 "不是新订单"
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // (2)修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateOrderMaster = orderMasterDAO.save(orderMaster);

        if (updateOrderMaster == null)
        {
            log.error("[取消订单] 更新失败, orderMaster={}", orderMaster);
            //抛出自定义异常"订单更新失败"
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        // 返回库存
        List<OrderDetail> orderDetailList = orderDetailDAO.queryByOrderId(orderMaster.getOrderId());
        orderDTO.setOrderDetailList(orderDetailList);
        // 如果订单详情列表为空 则爆出自定义异常"订单详情为空"
        if (CollectionUtils.isEmpty(orderDetailList))
        {
            log.error("[取消订单] 订单中无订单详情, orderDTO={}",orderDTO);
            // 抛出自定义异常 "订单详情为空"
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }

        // 返回购物车列表
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e -> new CartDTO(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());

        // (3)加库存(订单被取消后,原来扣除的库存需要被返回)
        productInfoService.increaseStock(cartDTOList);

        // (4)如果已经支付, 需要给用户退款
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCEED.getCode()))
        {
            // TODO
        }
        return orderDTO;
    }

    /**
     * 完成订单
     * @param orderDTO
     * @return
     */
    @Override
    public OrderDTO finish(OrderDTO orderDTO)
    {
        // (1)判断订单状态 若不是新订单,则不能完成
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode()))
        {
            log.error("[完成订单] 订单状态不正确, orderId={},oderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            // 抛出自定义异常 "不是新订单"
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // (2)修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterDAO.save(orderMaster);
        if(updateResult == null)
        {
            log.error("[完成订单] 更新失败,orderMaster={}",orderMaster);
            //抛出自定义异常"订单更新失败"
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    /**
     * 支付订单
     * @param orderDTO
     * @return
     */
    @Override
    public OrderDTO paid(OrderDTO orderDTO)
    {

        // (1)判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode()))
        {
            log.error("[取消订单] 订单状态不正确, orderId={} orderStatus={}", orderDTO.getOrderId(),orderDTO.getOrderStatus());

            // 抛出自定义异常 "不是新订单"
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // (2)判断支付状态 如果不是等待支付,则抛出异常
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode()))
        {
            log.error("[订单支付完成] 订单支付状态不正确,orderDTO={}",orderDTO);
            // 抛出自定义异常 "订单支付状态不正确"
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        // (3)修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCEED.getCode()); //修改订单支付状态字段
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterDAO.save(orderMaster);    //修改支付状态
        if (updateResult == null)
        {
            log.error("[取消订单] 更新失败, orderMaster={}", orderMaster);
            //抛出自定义异常"订单更新失败"
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        return orderDTO;
    }

    /**
     * 带分页查询所有的订单列表
     * @param pageable
     * @return
     */
    @Override
    public Page<OrderDTO> findList(Pageable pageable)
    {
        // 带分页查询所有的订单列表
        Page<OrderMaster> orderMasterPageList = orderMasterDAO.findAll(pageable);

        // OrderMaster -> OrderDTO
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPageList.getContent());
        return new PageImpl<>(orderDTOList,pageable,orderMasterPageList.getTotalElements());
    }
}
