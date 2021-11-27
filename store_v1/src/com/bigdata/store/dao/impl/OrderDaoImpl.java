package com.bigdata.store.dao.impl;

import com.bigdata.store.dao.OrderDao;
import com.bigdata.store.domain.Order;
import com.bigdata.store.quartz.Payment;
import com.bigdata.store.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author: duYang
 * @Date: 2021/7/19 14:05
 * @Version: 1.0
 */
public class OrderDaoImpl implements OrderDao {
    @Override
    public int insertOrder(Order order, Connection conn) throws SQLException {
        String sql = "insert into orders(oid,ordertime,total,state,uid) values(?,?,?,?,?)";
        QueryRunner query = new QueryRunner();
        Object[] params = {order.getOid(), order.getOrdertime(), order.getTotal(), order.getState(), order.getUser().getUid()};
        return query.update(conn, sql, params);
    }

    @Override
    public int confirmOrder(Order order) throws SQLException {
        String sql = "update orders set state=?,address=?,name=?,telephone=? where oid=?";
        QueryRunner query = new QueryRunner(JDBCUtils.getDataSource());
        Object[] params = {order.getState(), order.getAddress(), order.getName()
                , order.getTelephone(), order.getOid()};
        return query.update(sql, params);
    }

    @Override
    public long selectOrderCount(String uid) throws SQLException {
        String sql = "select count(1) from orders where uid=?";
        QueryRunner query = new QueryRunner(JDBCUtils.getDataSource());
        Object[] params = {uid};
        long l = (long) query.query(sql, new ScalarHandler(), params);
        return l;
    }

    @Override
    public List<Order> selectByUid(String uid, int offSize, int pageSize) throws SQLException {
        String sql = "select oid,total from orders where uid=? order by ordertime desc limit ?,?";
        QueryRunner query = new QueryRunner(JDBCUtils.getDataSource());
        Object[] params = {uid, offSize, pageSize};
        return query.query(sql, new BeanListHandler<>(Order.class), params);
    }

	@Override
	public int updateStatu(String oid) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "update orders set state=4 where oid=?";
        QueryRunner query = new QueryRunner(JDBCUtils.getDataSource());
        Object[] params = {oid};
        return query.update(sql, params);
	}

	@Override
	public List<Payment> selectTimeOrder() throws SQLException {
		// TODO Auto-generated method stub
		String sql = "select oid,ordertime from orders where state=1";
		QueryRunner query = new QueryRunner(JDBCUtils.getDataSource());
		return query.query(sql, new BeanListHandler<>(Payment.class));
	}
    
	
    
}
