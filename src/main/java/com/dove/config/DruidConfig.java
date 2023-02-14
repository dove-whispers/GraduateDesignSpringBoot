package com.dove.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.servlet.Servlet;


/**
 * 德鲁伊配置
 *
 * @author dove_whispers
 * @date 2023-02-06
 */
@Configuration
public class DruidConfig {
    /**
     * 注册Servlet
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean<Servlet> druidServletRegistrationBean() {
        ServletRegistrationBean<Servlet> druidServlet = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        druidServlet.addInitParameter("loginUsername", "dove");
        druidServlet.addInitParameter("loginPassword", "cao100200");
        return druidServlet;
    }

    /**
     * 注册Filter
     * @return
     */
    @Bean
    public FilterRegistrationBean<Filter> druidFilterRegistrationBean() {
        FilterRegistrationBean<Filter> druidFilter = new FilterRegistrationBean<>();
        druidFilter.setFilter(new WebStatFilter());
        druidFilter.addUrlPatterns("/*");
        druidFilter.addInitParameter("exclusions", "/druid/*,*.js,*.gif,*.jpg,*.png ,*.css,*.ico");
        return druidFilter;
    }
}
