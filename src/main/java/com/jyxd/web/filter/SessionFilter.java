package com.jyxd.web.filter;

import net.sf.json.JSONObject;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "sessionFilter",urlPatterns = {"/*"})
public class SessionFilter implements Filter {

    //标示符：表示当前用户未登录(可根据自己项目需要改为json样式)
    String NO_LOGIN = "您还未登录";

    //不需要登录就可以访问的路径(比如:注册登录等)
    String[] includeUrls = new String[]{"/login","/user/login","/aaa"};


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        String uri = request.getRequestURI();

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        //response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", ":x-requested-with,content-type");
        //((HttpServletResponse)servletResponse).setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=utf-8");

        System.out.println("filter url:"+uri);
        //是否需要过滤
        boolean needFilter = isNeedFilter(uri);


        if (!needFilter) { //不需要过滤直接传给下一个过滤器
            filterChain.doFilter(servletRequest, servletResponse);
        } else { //需要过滤器
            // session中包含user对象,则是登录状态
            if(session!=null && session.getAttribute("user") != null){
                //System.out.println("user:"+session.getAttribute("user"));
                filterChain.doFilter(request, response);
            }else{
                String requestType = request.getHeader("X-Requested-With");
                System.out.println("requestType:"+requestType);
                //判断是否是ajax请求
                if(requestType!=null && "XMLHttpRequest".equals(requestType)){
                    response.getWriter().write(this.NO_LOGIN);
                }else{
                    //重定向到登录页(需要在static文件夹下建立此html文件)
                    //response.sendRedirect(request.getContextPath()+"/session");
                    JSONObject json=new JSONObject();
                    json.put("code","4");
                    json.put("msg","身份信息已失效，请重新登录");
                    response.getWriter().write(json.toString());
                }
                return;
            }
        }
    }

    /**
     * @Author: zhangjie
     * @Description: 是否需要过滤
     * @Date: 2020-07-16
     * @param uri
     */
    public boolean isNeedFilter(String uri) {
        for (String includeUrl : includeUrls) {
            if(includeUrl.equals(uri)) {
                return false;
            }
        }
        String[] str=uri.split("/");
        //静态资源不拦截
        if(str.length>1 && ("js".equals(str[1]) || "css".equals(str[1]) || "img".equals(str[1]))){
            return false;
        }
        return true;
        //return false;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

}
