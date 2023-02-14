package com.dove.config;

import com.google.code.kaptcha.servlet.KaptchaServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Servlet;

/**
 * kaptcha配置
 *
 * @author dove_whispers
 * @date 2023-02-12
 */
@Configuration
public class KaptchaConfig {
    @Bean
    public ServletRegistrationBean<Servlet> kaptchaServletRegistrationBean() {
        ServletRegistrationBean<Servlet> kaptcha = new ServletRegistrationBean<>();
        KaptchaServlet kaptchaServlet = new KaptchaServlet();
        kaptcha.setServlet(kaptchaServlet);
        kaptcha.addInitParameter("kaptcha.border", "yes");
        kaptcha.addInitParameter("kaptcha.border.color", "105,179,90");
        kaptcha.addInitParameter("kaptcha.textproducer.font.names", "Arial");
        kaptcha.addInitParameter("kaptcha.textproducer.font.color", "orange");
        kaptcha.addInitParameter("kaptcha.textproducer.font.size", "43");
        kaptcha.addInitParameter("kaptcha.textproducer.char.length",  "4");
        kaptcha.addInitParameter("kaptcha.textproducer.char.string", "ABCDEFGHIJKLMNOPQRSTUVWXYZ2345678923456789");
        kaptcha.addInitParameter("kaptcha.image.width", "135");
        kaptcha.addInitParameter("kaptcha.image.height", "50");
        kaptcha.addInitParameter("kaptcha.noise.color", "green");
        kaptcha.addUrlMappings("/getKaptchaImg");
        return kaptcha;
    }
}
