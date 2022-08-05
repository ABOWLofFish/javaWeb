package javaWeb.test.config;

import javaWeb.test.common.GlobalFormDateConvert;
import javaWeb.test.common.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.core.convert.converter.Converter;
import java.util.HashSet;
import java.util.Set;

/**
 * @Desciprtion: 将拦截器和全局日期转换器配置注入系统中
 */
@Configuration
public class SysMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/api/**")//定义请求的拦截地址
                .excludePathPatterns("/api","/api/login","api/Register");//排除不被拦截的地址，根目录，登录，注册页面
    }
    @Bean
    public LoginInterceptor LoginInterceptor() {
        return new LoginInterceptor();
    }

    //注入表单格式 全局日期转换器
    @Bean
    @Autowired
    public ConversionService getConversionService(GlobalFormDateConvert globalDateConvert){
        ConversionServiceFactoryBean factoryBean = new ConversionServiceFactoryBean();
        Set<Converter> converters = new HashSet<>();
        converters.add(globalDateConvert);
        factoryBean.setConverters(converters);
        return factoryBean.getObject();
    }
}
