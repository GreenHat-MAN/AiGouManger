package com.bigdata.store.service;

import com.bigdata.store.domain.PageBean;
import com.bigdata.store.domain.Product;

public interface PageModelService {
		
	public PageBean<Product> getDataByPage(int pageIndex,int pageSize);
	
	
}
