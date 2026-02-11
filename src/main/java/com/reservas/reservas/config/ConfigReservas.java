package com.reservas.reservas.config;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ConfigReservas {
	//crear bean para las llamadas con RestTemplate
	@LoadBalanced // para que reconozca eureka
	@Bean
    public WebClient.Builder webClient(){
		//builder si quiekres usasr el loadbalance
		return WebClient.builder();
    }
}
