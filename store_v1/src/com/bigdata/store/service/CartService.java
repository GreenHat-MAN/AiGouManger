package com.bigdata.store.service;

import java.util.List;

import com.bigdata.store.domain.Cart;
import com.bigdata.store.domain.CartInfo;

public interface CartService {

	List<CartInfo> getCartInfo(List<Cart> cartList);
}
