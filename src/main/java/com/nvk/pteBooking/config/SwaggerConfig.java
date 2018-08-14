package com.nvk.pteBooking.config;

import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Example Swagger Configuration for the REST API.
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  /**
   * Swagger configuration.
   * @return Swagger Api configuration.
   */
  @Bean
  public Docket api() {
    ApiInfo apiInfo = new ApiInfo(
      "PTE Booking Micro-service API",
      "A Micro-service to handle PTE Bookings.",
      "Version 1",
      "http://termsOfServiceUrl",
      new Contact("Contact Name", "contactUrl", "contactAddress@vk.one"),
      "License of API",
      "API license URL",
      Collections.emptyList()
    );

    return new Docket(DocumentationType.SWAGGER_2)
             .select()
             .apis(RequestHandlerSelectors.basePackage("com.vk.pteBooking.controller"))
             .paths(PathSelectors.any())
             .build()
             .apiInfo(apiInfo);
  }
}
