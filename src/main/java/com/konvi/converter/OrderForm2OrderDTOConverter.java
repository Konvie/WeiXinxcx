package com.konvi.converter;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.konvi.dto.OrderDTO;
import com.konvi.entity.OrderDetail;
import com.konvi.entity.OrderMaster;
import com.konvi.enums.ResultEnum;
import com.konvi.exception.SellException;
import com.konvi.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * OrderForm -> OrderMaster
 * @author konvi
 * @version 1.0
 * @date 2021/8/14
 */
@Slf4j
public class OrderForm2OrderDTOConverter
{
    /**
     * OrderForm -> OrderDTO
     */

    public static OrderDTO convert(OrderForm orderForm)
    {
        Gson gson = new Gson();
        List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        try {
            // OrderForm -> OrderMaster
            // gson.fromJson(String json,Type typeOfT);
            orderDetailList = gson.fromJson(orderForm.getItems(),new TypeToken<List<OrderDetail>>(){}.getType());
        }catch (JsonSyntaxException e)
        {
            log.error("[对象转换]错误,string={}",orderForm.getItems());
            // 抛出自定义异常"参数不正确"
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
