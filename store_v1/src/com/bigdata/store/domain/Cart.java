package com.bigdata.store.domain;

import java.util.List;

public class Cart {
	// 用户编号
	private String uid;
	// 商品编号
	private Integer pid;
	// 商品数量
	private Integer count;
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	public Cart(String uid, Integer pid, Integer count) {
		super();
		this.uid = uid;
		this.pid = pid;
		this.count = count;
	}
	public Cart() {
		// TODO Auto-generated constructor stub
	}

}
