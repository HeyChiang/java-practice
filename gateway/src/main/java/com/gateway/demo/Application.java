package com.gateway.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


/**
 * 通过请求springboot项目com.spring.gateway包下的GatewayApplication，制作延迟调用fallback方法
 * @author Chiang
 */
@SpringBootApplication
@EnableConfigurationProperties(UriConfiguration.class)
@RestController
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder, UriConfiguration uriConfiguration) {
		String httpUri = uriConfiguration.getHttpUrl();
		return builder.routes()
			.route(p -> p
				.path("/get")
				.filters(f -> f.addRequestHeader("Hello", "World"))
				.uri(httpUri))
			.route(p -> p
				.host("*.hystrix.com")
				.filters(f -> f
					.hystrix(config -> config
						.setName("mycmd")
						.setFallbackUri("forward:/fallback"))) //默认超过1秒会调用应急方法
				.uri(httpUri))
			.build();
	}

	@RequestMapping("/fallback")
	public Mono<String> fallback() {
		return Mono.just("two fallback");
	}

}