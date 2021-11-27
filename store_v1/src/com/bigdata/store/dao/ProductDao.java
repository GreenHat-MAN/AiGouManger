package com.bigdata.store.dao;

import java.util.List;

import com.bigdata.store.domain.Product;


public interface ProductDao {
	
	public List<Product> findByPage(int pageIndex,int pageSize);
	
	//总条数
	public long getCount();
	
	//查询
	public List<Product> findAll(); 
	
	 //通过id查询
    public Product findByProId(String pid);
	
	public List<Product> findHotProduct();
	public List<Product> findNewProduct();//最新
	
}
