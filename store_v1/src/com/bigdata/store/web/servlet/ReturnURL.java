package com.bigdata.store.web.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bigdata.store.dao.OrderDao;
import com.bigdata.store.dao.impl.OrderDaoImpl;
import com.bigdata.store.quartz.AliPayProvider;

@WebServlet(value="/ReturnURL")
public class ReturnURL extends HttpServlet {
	private static OrderDao orderdao=new OrderDaoImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		boolean b = AliPayProvider.signVerification(req);
        System.out.println(b);
        String out_trade_no = req.getParameter("out_trade_no");// 商户订单号
        try {
			orderdao.updateStatu(out_trade_no);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        req.getRequestDispatcher("jsp/index.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
	
}
