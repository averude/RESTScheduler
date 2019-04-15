package com.averude.uksatse.scheduler.microservice.statistics;

import com.averude.uksatse.scheduler.core.security.filter.CustomCorsFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.filter.CorsFilter;

@EnableDiscoveryClient
// https://github.com/spring-projects/spring-security-oauth2-boot/issues/51
// @EnableOAuth2Client
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EntityScan("com.averude.uksatse.scheduler.core.entity")
@SpringBootApplication(scanBasePackages = {
        "com.averude.uksatse.scheduler.core.extractor",
        "com.averude.uksatse.scheduler.shared.service",
        "com.averude.uksatse.scheduler.microservice.statistics",
        "com.averude.uksatse.scheduler.core.errorhandler"
})
@EnableJpaRepositories(basePackages = "com.averude.uksatse.scheduler.shared.repository")
public class StatisticsServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(StatisticsServiceApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean customCorsFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean<CorsFilter>(new CustomCorsFilter());
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}