package com.bigdata.store.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Author: duYang
 * @Date: 2021/6/26 18:46
 * @Version: 1.0
 */
@WebFilter(urlPatterns = {"/OrderServlet"})
public class AdminFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession();
        Object o = session.getAttribute("user");
        if (o != null) {
            filterChain.doFilter(req, resp);
        } else {
            resp.sendRedirect("jsp/login.jsp");
        }
    }
}
