package com.bigdata.store.service;

import java.util.List;

import com.bigdata.store.domain.Product;

public interface ProductService {
	public List<Product> findHotProduct();//最热
	public List<Product> findNewProduct();//最新
	//通过id查询
    public Product findByProId(String pid);
}
