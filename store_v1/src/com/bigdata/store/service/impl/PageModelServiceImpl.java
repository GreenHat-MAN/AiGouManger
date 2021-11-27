package com.bigdata.store.service.impl;

import java.util.List;

import com.bigdata.store.dao.ProductDao;
import com.bigdata.store.dao.impl.ProductDaoImpl;
import com.bigdata.store.domain.PageBean;

import com.bigdata.store.domain.Product;
import com.bigdata.store.service.PageModelService;

public class PageModelServiceImpl implements PageModelService {

	@Override
	public PageBean<Product> getDataByPage(int pageIndex, int pageSize) {
		ProductDao productDao=new ProductDaoImpl();
		//查询第几页数据
		List<Product> products = productDao.findByPage(pageIndex, pageSize);
		
		long count = productDao.getCount();
		
		PageBean<Product> pageBean = new PageBean<>(pageIndex, pageSize, count, products);
		return pageBean;
	}



	
}
