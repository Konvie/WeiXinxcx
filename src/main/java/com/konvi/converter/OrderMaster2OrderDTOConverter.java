package com.konvi.converter;

import com.konvi.dto.OrderDTO;
import com.konvi.entity.OrderMaster;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author konvi
 * @version 1.0
 * @date 2021/8/16
 */
public class OrderMaster2OrderDTOConverter
{
    /**
     * 将OrderMaster 转换成 OrderDTO
     * @param orderMaster
     * @return
     */
    public static OrderDTO convert(OrderMaster orderMaster)
    {
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    }

    /**
     * 将 List<OrderMaster> 转换成 List<OrderDTO>
     * @param orderMasterList
     * @return
     */
    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList)
    {
        return orderMasterList.stream().map(e ->convert(e)).collect(Collectors.toList());
    }
}
