package com.bigdata.store.dao;

import com.bigdata.store.domain.OrderItem;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Author: duYang
 * @Date: 2021/7/19 18:54
 * @Version: 1.0
 */
public interface OrderItemDao {
    int insertOrderItem(OrderItem orderItem, Connection conn) throws SQLException;

    List<Map<String, Object>> selectOrderItemByOid(String oid) throws SQLException;
}
