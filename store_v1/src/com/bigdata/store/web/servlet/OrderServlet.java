package com.bigdata.store.web.servlet;

import com.alipay.api.domain.PaymentList;
import com.bigdata.store.dao.CartDao;
import com.bigdata.store.dao.impl.CartDaoImpl;
import com.bigdata.store.domain.*;
import com.bigdata.store.dto.PageDto;
import com.bigdata.store.quartz.AliPayProvider;
import com.bigdata.store.quartz.Payment;
import com.bigdata.store.service.CartService;
import com.bigdata.store.service.OrderService;
import com.bigdata.store.service.impl.CartServiceImpl;
import com.bigdata.store.service.impl.OrderServiceImpl;
import com.bigdata.store.utils.TimeUtil;
import com.bigdata.store.utils.UUIDUtils;
import com.bigdata.store.web.base.BaseServlet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: duYang
 * @Date: 2021/7/19 12:30
 * @Version: 1.0
 */
@WebServlet(value = "/OrderServlet")
public class OrderServlet extends BaseServlet {
    private static OrderService orderService = new OrderServiceImpl();
    private static CartDao cartDao=new CartDaoImpl();

    /**
     * 提交订单
     */
    public void submitOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        if (user==null) return;
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
		List<CartInfo> cartinfoList = null;
		if (cartList != null) {
			// 只获取当前登录用户的购物车数据
			cartList = cartList.stream().filter(item -> item.getUid().equals(user.getUid())).collect(Collectors.toList()); // +++++++++++++++++++++++++++++++++
			if (cartList.size() > 0) {
				// 查询商品信息
				CartService s = new CartServiceImpl();
				cartinfoList = s.getCartInfo(cartList);
				
			}

		}

        //定义对象，存放订单信息于session当中。
        Order order = new Order();
        order.setOid(UUIDUtils.getId());
        List<OrderItem> list = order.getList();
        double sumtotal=0;
		for(CartInfo cartInfo:cartinfoList){
			Product product= cartInfo.getProduct();
			double total= product.getShop_price()*cartInfo.getCount();
			sumtotal+=total;
			OrderItem orderItem = new OrderItem();
            orderItem.setItemid(UUIDUtils.getId());
            orderItem.setQuantity(cartInfo.getCount());
            orderItem.setTotal(total);
            orderItem.setProduct(product);
            orderItem.setOid(order.getOid());
            list.add(orderItem);
		}
		
        //order属性
        order.setOrdertime(TimeUtil.getTime());
        order.setTotal(sumtotal);
        order.setState(1);  //1为未确认订单
        order.setList(list);
        order.setUser(user);
        orderService.sumbitOrder(order);
        session.setAttribute("order", order);
        
        cookies = request.getCookies();
		cartList = null;
		g = new Gson();
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
		// 删除登录用户的购物车数据
		// 对购物车进行操作
		Iterator<Cart> it_b = cartList.iterator();
		
		while (it_b.hasNext()) {
			Cart obj = it_b.next();
			if (obj.getUid().equals(user.getUid())) { // 该用户该商品已存在 // +++++++++++++++++++++++++++
				it_b.remove();
			}
		}

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
        
        response.sendRedirect("jsp/order_info.jsp");
    }

    /**
     * 确认订单,订单详情
     */
    public void confirmOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Order order = (Order) session.getAttribute("order");
        if (order == null) return;
        String address = request.getParameter("address");
        String name = request.getParameter("name");
        String telephone = request.getParameter("telephone");
        order.setAddress(address);
        order.setName(name);
        order.setTelephone(telephone);
        //直接默认已完成
        order.setState(1);
        orderService.confirmOrder(order);
        Payment payment=new Payment(order.getOid(),order.getOrdertime());
        com.bigdata.store.quartz.PaymentList.addPayment(payment);
        StringBuilder stringBuilder=new StringBuilder();
        for(OrderItem orderItem:order.getList()){
        	stringBuilder.append(orderItem.getProduct().getPname()+" ");
        }
        String form = AliPayProvider.generateAliPay(order.getOid(), order.getTotal(), stringBuilder.toString());
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(form); //直接将完整的表单html输出到页面
        response.getWriter().flush();
        response.getWriter().close();
    }

    /**
     * 我的订单
     */
    public String findMyOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        int pageIndex;
        String index = request.getParameter("index");
        if (index == null || index.equals("")) pageIndex = 1;
        else pageIndex = Integer.parseInt(index);
        PageDto<Order> dto = orderService.selectLimitOrder(user.getUid(), pageIndex);
        session.setAttribute("dto", dto);
        return "jsp/order_list.jsp";
    }
}
