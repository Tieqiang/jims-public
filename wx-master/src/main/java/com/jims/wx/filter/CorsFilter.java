package com.jims.wx.filter;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by heren on 2014/10/13.
 */
public class CorsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletResponse.setCharacterEncoding("UTF-8");

        HttpServletResponse res = (HttpServletResponse) servletResponse;
        res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS");
        res.addHeader("Access-Control-Allow-Credentials", "true");
        res.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        res.addHeader("Access-Control-Max-Age", "600000");
        res.setCharacterEncoding("UTF-8");
        String path=((HttpServletRequest) request).getRequestURI();
//        if(path.contains("login.html") || path.contains("wx-service")){
             filterChain.doFilter(request, res);
//        }
//         servletResponse.getWriter().write("请求的路径不合法");
      }
    @Override
    public void destroy() {

    }
}

