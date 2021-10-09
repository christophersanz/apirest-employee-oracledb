package pe.com.tarjetaoh.app.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration 
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
			.apiInfo(apiInfo())
	        .select()
			.apis(RequestHandlerSelectors.basePackage("pe.com.tarjetaoh.app"))
			.paths(PathSelectors.any())
			.build();
	}
	
	private ApiInfo apiInfo() {
	    return new ApiInfo(
	      "SWAGGER DEMO DOCUMENTATION", 
	      "Created by Christopher", 
	      "API V1.0", 
	      "TERMS OF SERVICE", 
	      new Contact("Christopher Sánchez", "https://www.linkedin.com/in/christophersanz/", "s.christopher3@gmail.com"), 
	      "License of API", 
	      "https://www.linkedin.com/in/christophersanz/", Collections.emptyList());
	}

    
}
