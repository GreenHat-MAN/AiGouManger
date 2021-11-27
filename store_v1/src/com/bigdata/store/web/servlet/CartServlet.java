package com.bigdata.store.web.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ObjectUtils.Null;

import com.bigdata.store.domain.Cart;
import com.bigdata.store.domain.CartInfo;
import com.bigdata.store.domain.User;
import com.bigdata.store.service.CartService;
import com.bigdata.store.service.impl.CartServiceImpl;
import com.bigdata.store.web.base.BaseServlet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@WebServlet("/CartServlet")
public class CartServlet extends BaseServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 添加购物车
	public void cartAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 判断用户是否登录
		if (request.getSession().getAttribute("user") != null) { // +++++++++ !=null
			// 拿到之前购物车数据 有 拿 没有 创建
			// 获取到该用户的购物车数据
			List<Cart> cartList = getCartList(request);
			Cart cart = new Cart();
			// 判断是否为空
			if (cartList == null) {
				cartList = new ArrayList<Cart>();
			}
			// 获取要添加的数据
			BeanUtils.populate(cart, request.getParameterMap());
			// 设置那个用户添加的
			User user = (User) request.getSession().getAttribute("user");
			cart.setUid(user.getUid()); // session获取用户数据 ++++++++++++++++++++++++++++++++++++++++
			// 对购物车进行操作
			boolean flag = false;
			for (Cart item : cartList) {
				if (item.getUid().equals(user.getUid()) && item.getPid().equals(cart.getPid())) { // 该用户该商品已存在 //// +++++++++++++++++++++++++++
					item.setCount(item.getCount() + cart.getCount());
					flag = true; // 代表存在不添加了
					break;
				}
			}
			// 不存在该信息 添加
			if (!flag) {
				cartList.add(cart);
			}
			//写入
			writeCartListToCookies(response, cartList);
			response.getWriter().println("加入购物车成功");
		} else {
			response.getWriter().println("您还未登录！请先登录");
		}

	}

	// 购物车商品删除
	public void cartDel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int pid = Integer.parseInt(request.getParameter("pid"));
		// 获取到该用户的购物车数据
		List<Cart> cartList = getCartList(request);
		// 删除登录用户的购物车数据
		// 对购物车进行操作
		Iterator<Cart> it_b = cartList.iterator();
		User user = (User) request.getSession().getAttribute("user");
		while (it_b.hasNext()) {
			Cart obj = it_b.next();
			if (obj.getUid().equals(user.getUid()) && obj.getPid().equals(pid)) { // 该用户该商品已存在 //// +++++++++++++++++++++++++++
				it_b.remove();
			}
		}
		//写入
		writeCartListToCookies(response, cartList);
		response.getWriter().println("购物车商品删除成功！");
	}

	// 购物车商品数量修改
	public void cartCount(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pid = Integer.parseInt(request.getParameter("pid"));
		int count = Integer.parseInt(request.getParameter("count"));
		// 获取到该用户的购物车数据
		List<Cart> cartList = getCartList(request);
		// 修改登录用户的购物车数据
		// 对购物车进行操作
		User user = (User) request.getSession().getAttribute("user");
		for (Cart item : cartList) {
			if (item.getUid().equals(user.getUid()) && item.getPid().equals(pid)) { // 该用户该商品已存在
				item.setCount(count);
				break;
			}
		}
		//写入
		writeCartListToCookies(response, cartList);
		response.getWriter().println("数量修改成功！");
	}

	// 清空购物车
	public void cartEmpty(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 获取到该用户的购物车数据
		List<Cart> cartList = getCartList(request);
		// 删除登录用户的购物车数据
		// 对购物车进行操作
		Iterator<Cart> it_b = cartList.iterator();
		User user = (User) request.getSession().getAttribute("user");
		while (it_b.hasNext()) {
			Cart obj = it_b.next();
			if (obj.getUid().equals(user.getUid())) { // 该用户该商品已存在 // +++++++++++++++++++++++++++
				it_b.remove();
			}
		}
		writeCartListToCookies(response, cartList);
		response.getWriter().println("<script language='javascript'>alert('清空购物车成功！');window.location.href='"
				+ request.getServletContext().getContextPath() + "/CartServlet?method=getCartInfo'" + "</script>");
	}

	// 查询购物车数据
	public void getCartInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Cart> cartList = getCartList(request);
		if (cartList != null) {
			// 只获取当前登录用户的购物车数据
			User user = (User) request.getSession().getAttribute("user");
			cartList = cartList.stream().filter(item -> item.getUid().equals(user.getUid()))
					.collect(Collectors.toList()); // +++++++++++++++++++++++++++++++++
		}
		if (cartList.size() > 0) {
			// 查询商品信息
			CartService s = new CartServiceImpl();
			List<CartInfo> cartinfo = s.getCartInfo(cartList);
			// 存
			request.setAttribute("cartList", cartinfo);
		}
		// 查询所有商品信息
		request.getRequestDispatcher("/jsp/cart.jsp").forward(request, response);

	}

	// 拿数据
	public List<Cart> getCartList(HttpServletRequest request) throws Exception {
		// 获取到该用户的购物车数据
		Cookie[] cookies = request.getCookies();
		List<Cart> cartList = null;
		Gson g = new Gson();
		// 拿到购物车数据
		for (Cookie item : cookies) {
			if (item.getName().contains("cart")) {
				Type userListType = new TypeToken<ArrayList<Cart>>() {
				}.getType();
				String decode = URLDecoder.decode(item.getValue(), "utf-8");
				cartList = g.fromJson(decode, userListType);
				break;
			}
		}
		return cartList;
	}

	// 写数据
	public void writeCartListToCookies(HttpServletResponse response,List<Cart> cartList) throws Exception {
		Gson g = new Gson();
		// 序列化数据
		String json = g.toJson(cartList);
		System.out.println(json);
		// 写入cookie
		String encode = URLEncoder.encode(json, "utf-8");
		Cookie cookie = new Cookie("cart", encode);
		cookie.setMaxAge(60 * 60 * 60);
		cookie.setHttpOnly(true);
		cookie.setVersion(1);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
}