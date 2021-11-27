package com.bigdata.store.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bigdata.store.constant.Constant;
import com.bigdata.store.domain.User;
import com.bigdata.store.service.UserService;
import com.bigdata.store.service.impl.UserServiceImpl;
import com.bigdata.store.utils.MailUtils;
import com.bigdata.store.utils.MyBeanUtils;
import com.bigdata.store.utils.UUIDUtils;
import com.bigdata.store.web.base.BaseServlet;

@WebServlet("/UserServlet")
public class UserServlet extends BaseServlet {
	
	public void findAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("findAll");
		
		
	}
	
	
	public String registUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		return "/jsp/register.jsp";
		
	}
	
	
	public String userRegist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//收集用户填写注册数据 
		// 接收表单参数
				Map<String, String[]> map = request.getParameterMap();
				User user = new User();
				MyBeanUtils.populate(user, map);
				// 为用户的其他属性赋值
				user.setUid(UUIDUtils.getId());
				user.setState(0);
				user.setCode(UUIDUtils.getId());
				System.out.println(user);
				
				// 调用业务层注册功能
				UserService UserService = new UserServiceImpl();
				try {
					UserService.userRegist(user);
					MailUtils.sendMail(user.getEmail(), user.getCode());
					// 注册成功,向用户邮箱发送信息,跳转到提示页面
					request.setAttribute("msg", "用户注册成功,请激活!");

				} catch (Exception e) {
					// 注册失败,跳转到提示页面
					request.setAttribute("msg", "用户注册失败,请重新注册!");

				}
				return "/jsp/info.jsp";
		
	}
	
	
	//用户登录
	 public String login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    try {
	            //1.获取用户名和密码
	            String username = request.getParameter("username");
	            String password = request.getParameter("password");  
	            //2.调用service完成登录 返回值:user
	            UserService us = new UserServiceImpl();
	            User user=us.login(username, password);   
	           //3.判断user 根据结果生成提示
	            if(user == null){
	                //用户名和密码不匹配
	                request.setAttribute("msg", "用户名和密码不匹配");;
	                return "jsp/login.jsp";
	            }  
	          //若用户不为空,继续判断是否激活
	              if(Constant.USER_IS_ACTIVE != user.getState()){
	                  //未激活
                  request.setAttribute("msg", "请先去邮箱激活,再登录!");
                  return "jsp/login.jsp";              }
	            //登录成功 保存用户登录状态
	            request.getSession().setAttribute("user", user);
	            //跳转到 index.jsp
	            response.sendRedirect("index.jsp");
	        } catch (Exception e) {
	            e.printStackTrace();
	            request.setAttribute("msg", "用户登录失败");
	            return "jsp/login.jsp";
	        }
        return null;
	     }
	 //用户激活
	 public String active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        try {
	            //1.接受code
	            String code = request.getParameter("code");        
	            //2.调用service完成激活 返回值:user
	            UserService us=new UserServiceImpl();
	            User user=us.active(code);
	            //3.判断user 生成不同的提示信息
	            if(user == null){
	                //没有找到这个用户,激活失败
	                request.setAttribute("msg", "激活失败,请重新激活或者重新注册~");
	                return "jsp/index.jsp";
	            } else {
	            	request.setAttribute("msg", "激活成功，请登录！ ~");
	            	return "jsp/login.jsp";
				}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	 	//退出
   public String logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       request.getSession().invalidate();
       response.sendRedirect(request.getContextPath());
       return null;
   }
	
}