package com.konvi.controller;

import com.konvi.converter.OrderForm2OrderDTOConverter;
import com.konvi.dto.OrderDTO;
import com.konvi.entity.OrderMaster;
import com.konvi.enums.ResultEnum;
import com.konvi.exception.SellException;
import com.konvi.form.OrderForm;
import com.konvi.service.IBuyerService;
import com.konvi.service.IOrderService;
import com.konvi.utils.ResultVOUtil;
import com.konvi.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 买家端订单Controller
 * @author konvi
 * @version 1.0
 * @date 2021/8/14
 */
@Slf4j
@RestController
@RequestMapping("/buyer/order")
public class BuyerOrderController
{
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IBuyerService buyerService;

    // (1)创建订单 POST /sell/buyer/order/create
    // BindingResult 用在实体校验信息返回结果绑定
    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            log.error("[创建订单] 参数不正确, orderFor={}",orderForm);
            // 抛出自定义异常 "参数不正确"
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }

        // OrderForm -> OrderDTO
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);

        // 判断订单详情列表是否为空
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList()))
        {
            log.error("[创建订单] 购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO createResult = orderService.create(orderDTO); //创建订单
        Map<String,String>map = new HashMap<String,String>();
        map.put("orderId",createResult.getOrderId());   //设置orderId
        return ResultVOUtil.success(map);
    }

    // (2)订单列表 GET /sell/buyer/order/list
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid")String openid, @RequestParam("page")Integer page, @RequestParam("size")Integer size)
    {
        if (!StringUtils.hasText(openid))
        {
            log.error("[查询订单列表]openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        PageRequest pageRequest = PageRequest.of(page,size);
        // 查询订单列表
        Page<OrderDTO> orderDTOPage = orderService.findList(openid,pageRequest);

        return ResultVOUtil.success(orderDTOPage.getContent());

        //测试必须返回字段默认值及空字段
        //return ResultVOUtil.success();
        //ResultVO resultVO = new ResultVO();
        //resultVO.setCode(0);
        //return resultVO;
    }

    // (3)订单详情 GET /sell/buyer/order/detail
    @GetMapping("/detail")
    public ResultVO<OrderMaster> detail(@RequestParam("openid")String openid, @RequestParam("orderId")String orderId)
    {

        OrderDTO orderDTO = buyerService.findOrderOne(openid,orderId);
        //不安全的方法
        //OrderMaster orderMaster = orderService.findById(orderId);
        return ResultVOUtil.success(orderDTO);
    }

    // (4)取消订单 POST /sell/buyer/order/cancel
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid")String openid,@RequestParam("orderId")String orderId)
    {
        OrderDTO orderDTO = buyerService.cancelOrder(openid,orderId);
        // 不安全的方法
        //OrderMaster orderMaster = orderService.findById(orderId);
        //orderService.cancel(orderMaster);
        return ResultVOUtil.success();
    }

}
