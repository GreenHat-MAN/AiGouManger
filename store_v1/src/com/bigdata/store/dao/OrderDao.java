package com.bigdata.store.dao;

import com.bigdata.store.domain.Order;
import com.bigdata.store.quartz.Payment;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author: duYang
 * @Date: 2021/7/19 14:04
 * @Version: 1.0
 */
public interface OrderDao {
    int insertOrder(Order order, Connection conn) throws SQLException;

    int confirmOrder(Order order) throws SQLException;

    long selectOrderCount(String uid) throws SQLException;

    List<Order> selectByUid(String uid, int offSize, int pageSize) throws SQLException;
    
    int updateStatu(String oid) throws SQLException;
    
    List<Payment> selectTimeOrder() throws SQLException;
}
