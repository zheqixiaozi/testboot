package com.sun.testboot.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class HelloFilter2 implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("===HelloFilter2 init.....");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("===HelloFilter2 doFilter.....");
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpServletRequest.getRequestURI();
        System.out.println("===requestURI2:"+requestURI);
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("===HelloFilter2 doFilter.....");
    }

    @Override
    public void destroy() {
        System.out.println("===HelloFilter2 destroy.....");

    }
}
