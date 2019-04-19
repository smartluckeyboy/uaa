package com.hcl.cloud.uaa.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

       @SuppressWarnings("deprecation")
       /*public static final ApiInfo DEFAULT_CUSTOM_INFO = new ApiInfo("UAA Microservice API Documents",
                     "UAA Microservice API Documents", "1.0.0", "urn:tos", "pcf@hcl.com", "Cloud Foundry",
                     "http://www.pivotal.org/licenses/LICENSE-2.0");
*/
       @Bean
       public Docket postsApi() {

              return new Docket(DocumentationType.SWAGGER_2);

       }

}

