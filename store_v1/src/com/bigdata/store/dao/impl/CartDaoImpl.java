package com.bigdata.store.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.bigdata.store.dao.CartDao;
import com.bigdata.store.domain.Cart;
import com.bigdata.store.domain.CartInfo;
import com.bigdata.store.domain.Product;
import com.bigdata.store.utils.JDBCUtils;

public class CartDaoImpl implements CartDao {

	@Override
	public List<CartInfo> getCartInfo(List<Cart> cartList) {
		// TODO Auto-generated method stub
		QueryRunner q=new QueryRunner(JDBCUtils.getDataSource());
		List<CartInfo> cartInfos=new ArrayList<CartInfo>();
		//获取要查询的数据
		StringBuffer pids=new StringBuffer();
		for(Cart item:cartList) {
			pids.append(item.getPid()+",");
		}
		pids.delete(pids.length()-1, pids.length());
		// 查询数据
		try {
			List<Product> query = q.query("select * from product where pid in ("+pids.toString()+")", new BeanListHandler<>(Product.class));
			
			//将数据转换成带数量的购物车
			for(Cart cart :cartList) {
				for(Product item :query) {
					if(cart.getPid().equals(Integer.parseInt(item.getPid()))) {
						//添加
						CartInfo c=new CartInfo();
						c.setProduct(item);
						c.setCount(cart.getCount());
						cartInfos.add(c);
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cartInfos;
	}

}
