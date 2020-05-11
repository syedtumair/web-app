package com.microservices.viewfinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

@EnableDiscoveryClient
@SpringBootApplication
@EnableEurekaClient
public class ViewfinderApplication {
	public static void main(String[] args) {
		SpringApplication.run(ViewfinderApplication.class, args);
	}
}