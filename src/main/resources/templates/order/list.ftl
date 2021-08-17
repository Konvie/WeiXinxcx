<#--
<#list orderMasterPageList.content as orderMaster>
    ${orderMaster.orderId}<br/>
</#list>-->
<html>
    <head>
        <meta charset="UTF-8">
        <link href="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/3.0.1/css/bootstrap.css" rel="stylesheet">
    </head>

    <body>
        <div class="container">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <#--主要内容 content start-->
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>序号</th>
                                <th>订单号</th>
                                <th>姓名</th>
                                <th>手机号</th>
                                <th>地址</th>
                                <th>金额</th>
                                <th>订单状态</th>
                                <th>支付状态</th>
                                <th>创建时间</th>
                                <th colspan="2">操作</th>
                            </tr>
                        </thead>
                        <tbody>
                            <#list orderDTOPageList.content as orderDTO>
                                <tr>
                                    <td>${orderDTO_index+1}</td>
                                    <td>${orderDTO.orderId}</td>
                                    <td>${orderDTO.buyerName}</td>
                                    <td>${orderDTO.buyerPhone}</td>
                                    <td>${orderDTO.buyerAddress}</td>
                                    <td>${orderDTO.orderAmount}</td>
                                    <td>${orderDTO.getOrderStatusEnum().message}</td>
                                    <td>${orderDTO.getPayStatusEnum().message}</td>
                                    <td>${orderDTO.createTime}</td>
                                    <td><a href="/sell/seller/order/detail?orderId=${orderDTO.orderId}">详情</a></td>
                                    <td>
                                        <#if orderDTO.getOrderStatusEnum().message == "新订单">
                                            <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}">取消</a>
                                        </#if>
                                    </td>
                                </tr>
                            </#list>
                        </tbody>
                    </table>
                    <#--主要内容 content end-->
                </div>
                <#--分页 start-->
                <div class="col-md-12 column">
                    <ul class="pagination pull-right">  <#--让分页居右-->
                        <#--上一页处理 start-->
                        <#if currentPage lte 1>
                            <li>
                                <a href="#">上一页</a>
                            </li>
                        <#else>
                            <li>
                                <a href="/sell/seller/order/list?page=${currentPage - 1}&size=${size}">上一页</a>
                            </li>
                        </#if>
                        <#--上一页处理 end-->
                        <#--代循环遍历(根据DB中查询出来的带分页查询所有订单列表)-->
                        <#list 1..orderDTOPageList.getTotalPages() as index>
                            <#--当前页面置灰-->
                            <#if currentPage == index>
                                <li class="disabled">
                                    <a href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a>
                                </li>
                            <#else>
                                <li>
                                    <a href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a>
                                </li>
                            </#if>
                        </#list>
                        <#--代循环遍历(根据DB中查询出来的带分页查询所有订单列表)-->
                        <#-- 下一页处理 start-->
                        <#if currentPage gte orderDTOPageList.getTotalPages()>
                            <li>
                                <a href="#">下一页</a>
                            </li>
                        <#else>
                            <li>
                                <a href="/sell/seller/order/list?page=${currentPage + 1}&size=${size}">上一页</a>
                            </li>
                        </#if>
                        <#-- 下一页处理 end-->
                    </ul>
                </div>
                <#--分页 end-->
            </div>
        </div>
    </body>
</html>