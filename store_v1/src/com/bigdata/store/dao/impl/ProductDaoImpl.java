package com.bigdata.store.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.bigdata.store.dao.ProductDao;
import com.bigdata.store.domain.Product;
import com.bigdata.store.utils.JDBCUtils;

public class ProductDaoImpl implements ProductDao {

	@Override
	public List<Product> findByPage(int pageIndex, int pageSize) {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		
		 try {
			return qr.query("select * from product limit ?,?", new BeanListHandler<>(Product.class),(pageIndex-1)*pageSize,pageSize);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public long getCount() {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		try {
		   return 	(long) queryRunner.query("select count(*) from product ", new ScalarHandler());
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<Product> findAll() {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		
		List<Product> list = new ArrayList<Product>();
		
		try {
			list= qr.query("select * from product", new BeanListHandler<>(Product.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}


	// 获取热门商品
		public List<Product> findHotProduct() {
			QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
			String sql = "select * from product where is_hot=? limit ?,?";
			List<Product> query = null;
			try {
				query = runner.query(sql, new BeanListHandler<Product>(Product.class), 1,0,9);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return query;
		}
	
	//获取最新商品
		public List<Product> findNewProduct()  {
			QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
			String sql ="select * from product order by pdate desc limit ?,?";
			List<Product> query = null;
			try {
				query = runner.query(sql, new BeanListHandler<Product>(Product.class), 0,9);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return query;
		}

		@Override
		public Product findByProId(String pid) {
			String sql="select * from product where pid=?";
			QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
			try {
				return queryRunner.query(sql,new BeanHandler<>(Product.class),pid);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	
		
}
