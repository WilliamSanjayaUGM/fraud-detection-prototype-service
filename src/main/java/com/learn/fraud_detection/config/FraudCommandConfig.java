package com.learn.fraud_detection.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.learn.fraud_detection.command.AMLCheckCommand;
import com.learn.fraud_detection.command.DeviceFingerprintCommand;
import com.learn.fraud_detection.command.FraudCommand;
import com.learn.fraud_detection.command.KYCCheckCommand;
import com.learn.fraud_detection.command.MLScoreCommand;
import com.learn.fraud_detection.decorator.CircuitBreakerCommandDecorator;
import com.learn.fraud_detection.decorator.LoggingFraudCommandDecorator;
import com.learn.fraud_detection.decorator.MetricsCommandDecorator;
import com.learn.fraud_detection.decorator.RetryCommandDecorator;
import com.learn.fraud_detection.decorator.TimeoutCommandDecorator;
import com.learn.fraud_detection.service.AMLApiService;
import com.learn.fraud_detection.service.DeviceFingerprintService;
import com.learn.fraud_detection.service.FraudMLService;
import com.learn.fraud_detection.service.KycApiService;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.micrometer.core.instrument.MeterRegistry;

@Configuration
public class FraudCommandConfig {
	
	private FraudCommand decorate(FraudCommand command, MeterRegistry meterRegistry,
			CircuitBreaker cb) {

	    command = new LoggingFraudCommandDecorator(command);
	    command = new RetryCommandDecorator(command, 2);
	    command = new TimeoutCommandDecorator(command, Duration.ofMillis(500));
	    command = new CircuitBreakerCommandDecorator(command, cb);
	    command = new MetricsCommandDecorator(command, meterRegistry);

	    return command;
	}
	
	@Bean
    public FraudCommand amlCommand(AMLApiService amlApiService, MeterRegistry meterRegistry,
    		CircuitBreakerRegistry cbRegistry) {

        FraudCommand decorated = new AMLCheckCommand(amlApiService);
        
        return decorate(decorated, meterRegistry, cbRegistry.circuitBreaker("amlService"));
    }
	
	@Bean
    public FraudCommand deviceFingerprintCommand(DeviceFingerprintService deviceFingerprintService, MeterRegistry meterRegistry,
    		CircuitBreakerRegistry cbRegistry) {

        FraudCommand decorated = new DeviceFingerprintCommand(deviceFingerprintService);
        
        return decorate(decorated, meterRegistry, cbRegistry.circuitBreaker("deviceFingerprint"));
    }
	
	@Bean
    public FraudCommand kycCommand(KycApiService kycApiService, MeterRegistry meterRegistry,
    		CircuitBreakerRegistry cbRegistry) {

        FraudCommand decorated = new KYCCheckCommand(kycApiService);
        
        return decorate(decorated, meterRegistry, cbRegistry.circuitBreaker("kycService"));
    }
	
	@Bean
    public FraudCommand mlScoreCommand(FraudMLService fraudMLService, MeterRegistry meterRegistry,
    		CircuitBreakerRegistry cbRegistry) {

        FraudCommand decorated = new MLScoreCommand(fraudMLService);
        
        return decorate(decorated, meterRegistry, cbRegistry.circuitBreaker("mlService"));
    }
}
