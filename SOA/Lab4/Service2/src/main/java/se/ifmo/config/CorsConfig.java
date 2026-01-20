package se.ifmo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")  // 允许所有路径
                        .allowedOriginPatterns("*")  // 允许所有域名（生产环境建议指定具体域名）
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的 HTTP 方法
                        .allowedHeaders("*")  // 允许所有请求头
                        .allowCredentials(true) // 允许携带 Cookie（当 `allowedOrigins` 不是 `*` 时）
                        .maxAge(3600); // 预检请求的缓存时间，单位为秒
            }
        };
    }
}

