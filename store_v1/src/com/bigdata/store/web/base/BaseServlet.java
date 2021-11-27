package com.bigdata.store.web.base;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet {
	
	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 获得请求参数method
		String method = req.getParameter("method");

		// 默认方法名
		if (null == method || "".equals(method) || method.trim().equals("")) {
			method = "execute";
		}

		// 注意:此处的this代表的是子类的对象
		// System.out.println(this);
		// 子类对象字节码对象
		Class clazz = this.getClass();

		try {
			// 2获得当前运行类， 需要指定的方法
			Method md = clazz.getMethod(method, HttpServletRequest.class, HttpServletResponse.class);

			// 3执行方法
			String jspPath = (String) md.invoke(this, req, resp);
			// 4如果子类有返回值， 将谐求到指定的jsp页而
			if (null != jspPath) {
				req.getRequestDispatcher(jspPath).forward(req, resp);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 默认方法， 用于子类复写
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		return null;
	}

}