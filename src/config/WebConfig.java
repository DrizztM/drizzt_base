package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

/**
 * WebConfig WebServlet配置类
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "cn.drizzt.controller", useDefaultFilters = false, includeFilters = {
        @ComponentScan.Filter(value = { Controller.class }) })
public class WebConfig extends WebMvcConfigurerAdapter {

    /**
     * 视图层解析器
     * 
     * @return
     */
    @Bean
    public ViewResolver freeMarkerViewResolver() {
        FreeMarkerViewResolver bean = new FreeMarkerViewResolver();
        bean.setOrder(0);
        bean.setViewClass(FreeMarkerView.class);
        bean.setPrefix("/page/");
        bean.setSuffix(".ftl");
        bean.setContentType("text/html;charset=UTF-8");
        return bean;
    }

}
