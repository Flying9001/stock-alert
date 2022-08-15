package com.ljq.stock.alert.web.interceptor;

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
public class WebConfig implements WebMvcConfigurer {

    /**
     * 无需 token 的接口路径
     */
    private static String[] NO_TOKEN_API = {"/swagger-ui/**", "/webjars/**", "/v3/**", "/swagger-resources**",
            "/swagger-resources/**",
            "/api/user/info/checkCode", "/api/user/info/register", "/api/user/info/login",
            "/api/user/info/login/wechat/mini", "/api/common/wechat/minMsg",
            "/admin/admin/user/login"};

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
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:META-INF/resources/webjars/springfox-swagger-ui/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:META-INF/resources/webjars/");
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:META-INF/resources/","classpath:resources/", "classpath:static/",
                        "classpath:public/");
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
