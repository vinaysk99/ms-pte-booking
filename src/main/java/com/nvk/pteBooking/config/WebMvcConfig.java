package com.nvk.pteBooking.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebMvcConfig {

  /**
   * REST configuration for production environments.
   */
  @Configuration
  @ConditionalOnExpression("!'${spring.profiles.active}'.equals('dev')"
                             + " && !'${spring.profiles.active}'.equals('test') ")
  @Order(1)
  public static class ProdWebMvcConfig implements WebMvcConfigurer {

    /**
     * Configuring auth interceptor for security.
     * @return Auth Interceptor.
     */
    @Bean
    public AuthInterceptor authInterceptor() {
      return new AuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
      registry.addInterceptor(authInterceptor());
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
      configurer.setUseTrailingSlashMatch(true);
    }
  }
}