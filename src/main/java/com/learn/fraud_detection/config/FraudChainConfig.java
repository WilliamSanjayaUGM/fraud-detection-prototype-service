package com.learn.fraud_detection.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.learn.fraud_detection.handler.FraudHandler;
import com.learn.fraud_detection.handler.GeoLocationHandler;
import com.learn.fraud_detection.handler.VelocityHandler;

@Configuration
public class FraudChainConfig {
	
	@Bean
	public FraudHandler fraudChain(VelocityHandler velocity,
			GeoLocationHandler geo) {
		
		velocity.setNext(geo);
		
		return velocity;
	}
}
