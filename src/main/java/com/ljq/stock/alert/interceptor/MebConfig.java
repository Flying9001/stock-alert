package com.ljq.stock.alert.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @Description: web 应用配置
 * @Author: junqiang.lu
 * @Date: 2020/9/4
 */
@Configuration
@EnableWebMvc
public class MebConfig implements WebMvcConfigurer {

    /**
     * 无需 token 的接口路径
     */
    private static String[] NO_TOKEN_API = {"/swagger-resources/*", "/webjars/*", "/v2/*", "/swagger-ui.html/*",
            "/swagger-ui/*", "/*/favicon.ico", "/*/download**"};

    @Bean
    WebInterceptor webInterceptor() {
        return new WebInterceptor();
    }

    /**
     * 添加拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(NO_TOKEN_API);
    }

    /**
     * 资源过滤
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 跨域处理
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET","HEAD","POST","PUT","PATCH","DELETE","OPTIONS","TRACE")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .maxAge(3600);
    }

}
