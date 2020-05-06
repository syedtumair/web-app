package com.microservices.faculty;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class Swagger {
	@Bean
	public Docket apiDocket() {
		Contact contact = new Contact("Umair", "https://github.com/syedtumair", "syedtumair@gmai.com");
		List<VendorExtension> vendorExtensions = new ArrayList<>();
		ApiInfo apiInfo = new ApiInfo("Service Documentation", "This pages documents service endpoints", "1.0",
				"https://github.com/syedtumair", contact, "", "", vendorExtensions);
		Docket docket = new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo).select()
				.apis(RequestHandlerSelectors.basePackage("com.microservices")).paths(PathSelectors.any()).build();
		return docket;

	}
}

