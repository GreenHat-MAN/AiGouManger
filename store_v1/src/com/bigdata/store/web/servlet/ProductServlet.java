package com.bigdata.store.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bigdata.store.domain.PageBean;
import com.bigdata.store.domain.Product;
import com.bigdata.store.service.PageModelService;
import com.bigdata.store.service.ProductService;
import com.bigdata.store.service.impl.PageModelServiceImpl;
import com.bigdata.store.service.impl.ProductServiceImpl;
import com.bigdata.store.web.base.BaseServlet;

@SuppressWarnings("serial")
@WebServlet("/ProductServlet")
public class ProductServlet extends BaseServlet {
	
	
	//首页动态获取最新商品和热门商品
		public void index(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
			ProductService service = new ProductServiceImpl();
			//获取热门商品
			List<Product> hotProList = service.findHotProduct();
			//获取最新商品
			List<Product> newProList = service.findNewProduct();
			//传递到request域，并转发到index.jsp
			request.setAttribute("hotProList", hotProList);
			request.setAttribute("newProList", newProList);
			request.getRequestDispatcher("/jsp/index.jsp").forward(request, response);
			
		}

		
	public void ProductList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
				//数据传递页码 session request
				request.setCharacterEncoding("utf-8");
				response.setContentType("text/html;charset=utf8");
				
				//接受页码传过来数据 第几1页，每页显示多少10条
				String spageIndex=request.getParameter("pageIndex");//第几页 1
				String spageSize=request.getParameter("pageSize");//10条
				
				int pageIndex=1;
				int pageSize=10;
				if(spageIndex!=null) {
					pageIndex=Integer.valueOf(spageIndex);
					
				}
				if(spageSize!=null) {
					pageSize=Integer.valueOf(spageSize);
					
				}
			
				 //调用业务层方法
				PageModelService pageModelService=new PageModelServiceImpl();
				PageBean<Product> pageBean = pageModelService.getDataByPage(pageIndex, pageSize);
				//把数据放入request
				if(pageBean!=null) {	
					request.setAttribute("pageBean", pageBean);
				}else {
					
					request.setAttribute("msg", "数据为空");
				}
				request.getRequestDispatcher("/jsp/product_list.jsp").forward(request, response);
				
		
	}
	
	public void ProductInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
				//数据传递页码 session request
				request.setCharacterEncoding("utf-8");
				response.setContentType("text/html;charset=utf8");
				
				String pid=request.getParameter("pid");
				ProductService productService=new ProductServiceImpl();
				Product product=productService.findByProId(pid);
				List<Product> prolist=new ArrayList<Product>();
				prolist.add(product);
				request.setAttribute("prolist", prolist);
				request.getRequestDispatcher("/jsp/product_info.jsp").forward(request, response);;
				
	}
	
}
