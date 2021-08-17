package com.konvi.controller;

import com.konvi.dto.OrderDTO;
import com.konvi.entity.OrderMaster;
import com.konvi.enums.ResultEnum;
import com.konvi.exception.SellException;
import com.konvi.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 卖家端订单Controller
 * @author konvi
 * @version 1.0
 * @date 2021/8/15
 */
@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController
{
    @Autowired
    private IOrderService orderService;

    /**
     * http://107.0.0.1:8080/seller/order/list
     * 订单列表
     * @param page 从第1页开始,必填(默认值1)
     * @param size 必填(默认值10)
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue ="1") Integer page, @RequestParam(value = "size",defaultValue = "10")Integer size, Map<String,Object> map)
    {
        PageRequest pageRequest = PageRequest.of(page - 1, size);

        // 带分页查询所有的订单列表
        Page<OrderDTO> orderDTOPageList = orderService.findList(pageRequest);

        // 带分页查询到的订单列表
        map.put("orderDTOPageList",orderDTOPageList);
        //设置当前页
        map.put("currentPage",page);
        //设置每页显示多少条数据
        map.put("size",size);

        return new ModelAndView("order/list",map);
    }

    /**
     * 取消订单
     * @param orderId
     * @param map
     * @param request
     * @return
     */
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId")String orderId, Map<String,Object>map, HttpServletRequest request)
    {
        String contextPath = "";
        try
        {
            // 根据订单ID查询相关订单信息
            OrderDTO orderDTO = orderService.findById(orderId);
            // 取消订单操作
            OrderDTO cancelOrderDTO = orderService.cancel(orderDTO);
        } catch (SellException e)
        {
            log.error("[卖家端取消订单]发生异常{}",e);
            contextPath = request.getContextPath();     //灵活获取应用名 如/sell
            map.put("url",contextPath + "/seller/order/list");
            map.put("msg", e.getMessage());
            return new ModelAndView("common/error",map);
        }

        map.put("msg",ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("common/success",map);
    }

    /**
     * 订单详情
     * http://127.0.0.1:8080/sell/seller/order/list 订单列表页面
     * 点击订单详情 http://127.0.0.1:8080/sell/seller/order/detail?orderId=xxxxx
     * @param orderId
     * @param map
     * @param request
     * @return
     */
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId,Map<String,Object>map,HttpServletRequest request)
    {
        String contextPath="";
        OrderDTO orderDTO = new OrderDTO();
        try
        {
            // 根据订单ID查询订单
            orderDTO = orderService.findById(orderId);
        } catch (Exception e) {
            log.error("[卖家端查询订单详情]发生异常{}",e);
            contextPath = request.getContextPath();
            map.put("url",contextPath+"/seller/order/list");
            map.put("msg",e.getMessage());
            return new ModelAndView("common/error",map);
        }
        map.put("orderDTO",orderDTO);
        return new ModelAndView("order/detail",map);
    }

    /**
     * 完结订单
     */
    @GetMapping("/finish")
    public ModelAndView finished(@RequestParam("orderId")String orderId,HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        try
        {
            OrderDTO orderDTO = orderService.findById(orderId);
            orderService.finish(orderDTO);
        } catch (SellException e)
        {
            log.error("[卖家端完成订单]发生异常{}",e);
            session.setAttribute("msg",e.getMessage());
            session.setAttribute("url","/sell/seller/order/list");
            return new ModelAndView("common/error");
        }

        session.setAttribute("msg",ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
        session.setAttribute("url","/sell/seller/order/list");
        return new ModelAndView("common/success");
    }
}
