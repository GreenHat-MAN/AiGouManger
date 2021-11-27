package com.bigdata.store.service.impl;

import java.util.List;

import com.bigdata.store.dao.ProductDao;
import com.bigdata.store.dao.impl.ProductDaoImpl;
import com.bigdata.store.domain.Product;
import com.bigdata.store.service.ProductService;

public class ProductServiceImpl implements ProductService {

	@Override
	//获取热门商品
		public List<Product> findHotProduct() {
			ProductDao dao = new ProductDaoImpl();
			List<Product> hotProList = null;
			try {
				hotProList = dao.findHotProduct();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return hotProList;
		}
	 
	@Override
		//获取最新商品
		public List<Product> findNewProduct() {
			ProductDao dao = new ProductDaoImpl();
			List<Product> newProList = null;
			try {
				newProList = dao.findNewProduct();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return newProList;
		}

	@Override
	public Product findByProId(String pid) {
		ProductDao productDao=new ProductDaoImpl();
		return productDao.findByProId(pid);
	}
	
}
