package com.bigdata.store.service;

import com.bigdata.store.domain.Order;
import com.bigdata.store.dto.PageDto;

/**
 * @Author: duYang
 * @Date: 2021/7/19 14:07
 * @Version: 1.0
 */
public interface OrderService {
    boolean sumbitOrder(Order order);
    boolean confirmOrder(Order order);
    PageDto<Order> selectLimitOrder(String uid, int pageIndex);
}
