package com.bigdata.store.dao.impl;

import com.bigdata.store.dao.OrderItemDao;
import com.bigdata.store.domain.OrderItem;
import com.bigdata.store.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Author: duYang
 * @Date: 2021/7/19 18:54
 * @Version: 1.0
 */
public class OrderItemDaoImpl implements OrderItemDao {
    @Override
    public int insertOrderItem(OrderItem orderItem, Connection conn) throws SQLException {
        String sql="insert into orderitem values(?,?,?,?,?)";
        QueryRunner query = new QueryRunner();
        Object[] params = {orderItem.getItemid(),orderItem.getQuantity(),orderItem.getTotal()
                ,orderItem.getProduct().getPid(),orderItem.getOid()};
        return query.update(conn, sql, params);
    }

    @Override
    public List<Map<String, Object>> selectOrderItemByOid(String oid) throws SQLException {
        String sql="select quantity,total,pimage,pname,shop_price from orderitem,product where orderitem.pid=product.pid and orderitem.oid=?";
        QueryRunner query = new QueryRunner(JDBCUtils.getDataSource());
        Object[] params = {oid};
        return query.query(sql, new MapListHandler(), params);
    }
}
