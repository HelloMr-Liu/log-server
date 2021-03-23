package org.king2.log.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 描述:系统访问过滤器配置
 * @author 刘梓江
 * @Date 2021/3/23 15:45
 */
@Configuration
public class SystemFilterConfiguration implements Filter {

    //存储本次请求线程请求信息
    public static final ThreadLocal<HttpServletRequest> contentRequest = new ThreadLocal<>();

    @Override
    public void init(FilterConfig filterConfig)  {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        contentRequest.set((HttpServletRequest) request);
        filterChain.doFilter(request, response);
        contentRequest.remove();
    }

    @Override
    public void destroy() {

    }
}

