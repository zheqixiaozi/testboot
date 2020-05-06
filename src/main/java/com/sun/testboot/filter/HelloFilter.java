package com.sun.testboot.filter;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
@WebFilter(urlPatterns = "/hello/*", filterName="filter1")
@Order(1)
public class HelloFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("===HelloFilter init.....");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("===HelloFilter doFilter.....");
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpServletRequest.getRequestURI();
        System.out.println("===requestURI:"+requestURI);
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("===HelloFilter doFilter.....");
    }

    @Override
    public void destroy() {
        System.out.println("===HelloFilter destroy.....");

    }
}
