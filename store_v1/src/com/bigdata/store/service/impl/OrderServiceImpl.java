package com.bigdata.store.service.impl;

import com.bigdata.store.dao.OrderDao;
import com.bigdata.store.dao.OrderItemDao;
import com.bigdata.store.dao.impl.OrderDaoImpl;
import com.bigdata.store.dao.impl.OrderItemDaoImpl;
import com.bigdata.store.domain.Order;
import com.bigdata.store.domain.OrderItem;
import com.bigdata.store.domain.Product;
import com.bigdata.store.dto.PageDto;
import com.bigdata.store.service.OrderService;
import com.bigdata.store.utils.JDBCUtils;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Author: duYang
 * @Date: 2021/7/19 14:07
 * @Version: 1.0
 */
public class OrderServiceImpl implements OrderService {

    private static OrderDao orderDao = new OrderDaoImpl();
    private static OrderItemDao orderItemDao = new OrderItemDaoImpl();

    @Override
    public boolean sumbitOrder(Order order) {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);
            int i = orderDao.insertOrder(order, conn);
            if (i == 0) {
                conn.rollback();
                return false;
            }
            for (OrderItem orderItem : order.getList()) {
                int i1 = orderItemDao.insertOrderItem(orderItem, conn);
                if (i1 == 0) {
                    conn.rollback();
                    return false;
                }
            }
            conn.commit();
            return true;
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean confirmOrder(Order order) {
        try {
            int i = orderDao.confirmOrder(order);
            if (i > 0) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public PageDto<Order> selectLimitOrder(String uid, int pageIndex) {
        try {
            int pageSize = 4;
            int offSize = (pageIndex - 1) * pageSize;
            long l = orderDao.selectOrderCount(uid);
            List<Order> orderList = orderDao.selectByUid(uid, offSize, pageSize);
            for (Order order : orderList) {
                List<Map<String, Object>> maps = orderItemDao.selectOrderItemByOid(order.getOid());
                for (Map<String, Object> map : maps) {
                    OrderItem orderItem = new OrderItem();
                    BeanUtils.populate(orderItem, map);
                    Product product = new Product();
                    BeanUtils.populate(product, map);
                    orderItem.setProduct(product);
                    order.getList().add(orderItem);
                }
            }
            PageDto<Order> pageDto = new PageDto<Order>((int) l, pageIndex, pageSize);
            pageDto.setList(orderList);
            return pageDto;
        } catch (SQLException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
