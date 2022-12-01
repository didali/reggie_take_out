package com.dida.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.dida.reggie.common.BaseContext;
import com.dida.reggie.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检查用户是否已经完成登录
 */
/*
    @WebFilter用于将一个类声明为一个过滤器，该注解将会在部署的时候被容器处理，容器会根据具体的属性配置将相应的类部署为过滤器
    filterName、urlPatterns 分别为配置过滤器名字以及拦截路径
 */
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    //AntPathMatcher 路径匹配器，支持通配符写法，专门用来进行路径比较的
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        //1. 获取本次请求的 URI
        String requestURI = request.getRequestURI();

        //相当于log.info("拦截到请求：" + requestURI;
        log.info("拦截到请求：{}", requestURI);

        //2. 判断本次请求是否需要处理
        //创建一个数组，定义不需要处理的请求路径
        String[] urls = new String[] {
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };
        //路径匹配
        boolean check = check(urls, requestURI);

        //3. 如果不需要处理，则直接放行
        if (check) {
            log.info("本次请求{}不需要处理", requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        //4-1 判断登录状态，如果已登录则直接放行
        if (request.getSession().getAttribute("employee") != null) {
            log.info("用户{}已登录", request.getSession().getAttribute("employee"));

            Long empId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);

            filterChain.doFilter(request, response);
            return;
        }

        //4-2 判断移动端登录状态，如果已登录则直接放行
        if (request.getSession().getAttribute("user") != null) {
            log.info("用户{}已登录", request.getSession().getAttribute("user"));

            Long userId = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);

            filterChain.doFilter(request, response);
            return;
        }

        //5. 如果未登录则返回登录界面，将Result对象转成JSON，通过输出流的方式向客户端页面响应数据
        log.info("用户未登录，即将跳转至登录界面");
        response.getWriter().write(JSON.toJSONString(Result.error("NOTLOGIN")));
        //return;

    }

    /**
     * 路径匹配，检查本次路径是否需要放行
     * @param urls 需要放行的路径
     * @param requestURI 请求路径
     * @return
     */
    public boolean check(String[] urls, String requestURI) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            //match 为 true 则匹配上了
            if (match) {
                return true;
            }
        }
        //遍历完还没匹配上则return false
        return false;
    }
}