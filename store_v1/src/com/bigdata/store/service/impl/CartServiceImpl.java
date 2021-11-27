package com.bigdata.store.service.impl;

import java.util.List;

import com.bigdata.store.dao.CartDao;
import com.bigdata.store.dao.impl.CartDaoImpl;
import com.bigdata.store.domain.Cart;
import com.bigdata.store.domain.CartInfo;
import com.bigdata.store.service.CartService;

public class CartServiceImpl implements CartService {

	CartDao dao=new CartDaoImpl();

	@Override
	public List<CartInfo> getCartInfo(List<Cart> cartList) {
		// TODO Auto-generated method stub
		return dao.getCartInfo(cartList);
	}

	
}
