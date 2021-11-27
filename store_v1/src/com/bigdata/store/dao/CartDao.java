package com.bigdata.store.dao;

import java.util.List;

import com.bigdata.store.domain.Cart;
import com.bigdata.store.domain.CartInfo;

public interface CartDao {

	List<CartInfo> getCartInfo(List<Cart> cartList);

}
