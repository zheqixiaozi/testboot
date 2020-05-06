package com.sun.testboot.config;

import com.sun.testboot.filter.HelloFilter2;
import com.sun.testboot.interceptor.HelloInterceptor;
import com.sun.testboot.listener.SessionListener;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyConfig implements WebMvcConfigurer {

    /**
     * 添加过滤器链
     * @return
     */
    @Bean
    @Order(2)
    public FilterRegistrationBean getBean(){
        FilterRegistrationBean filterBean = new FilterRegistrationBean();
        filterBean.setFilter(new HelloFilter2());
        filterBean.addUrlPatterns("/hello/*");
        filterBean.setName("filter2");
        return filterBean;
    }

    /**
     * 添加监听器链
     * @return
     */
    @Bean
    public ServletListenerRegistrationBean getServletBean(){
        ServletListenerRegistrationBean listenerBean = new ServletListenerRegistrationBean();
        listenerBean.setListener(new SessionListener());
        return listenerBean;
    }

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HelloInterceptor())
                .addPathPatterns("/hello/*");
    }

}
