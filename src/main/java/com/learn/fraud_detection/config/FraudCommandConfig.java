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

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.micrometer.core.instrument.MeterRegistry;

@Configuration
public class FraudCommandConfig {
	
	@Bean
    public FraudCommand amlCommand(AMLCheckCommand amlCommand, MeterRegistry meterRegistry,
    		CircuitBreakerRegistry cbRegistry) {

        FraudCommand decorated = amlCommand;
        
        decorated = new LoggingFraudCommandDecorator(decorated);
        decorated = new RetryCommandDecorator(decorated, 2);
        decorated = new TimeoutCommandDecorator(decorated, Duration.ofMillis(500));
        decorated = new CircuitBreakerCommandDecorator(decorated,
        		cbRegistry.circuitBreaker("amlService"));
        decorated = new MetricsCommandDecorator(decorated, meterRegistry);

        return decorated;
    }
	
	@Bean
    public FraudCommand deviceFingerprintCommand(DeviceFingerprintCommand deviceFingerprintCommand, MeterRegistry meterRegistry,
    		CircuitBreakerRegistry cbRegistry) {

        FraudCommand decorated = deviceFingerprintCommand;
        
        decorated = new LoggingFraudCommandDecorator(decorated);
        decorated = new RetryCommandDecorator(decorated, 2);
        decorated = new TimeoutCommandDecorator(decorated, Duration.ofMillis(500));
        decorated = new CircuitBreakerCommandDecorator(decorated,
        		cbRegistry.circuitBreaker("deviceFingerprint"));
        decorated = new MetricsCommandDecorator(decorated, meterRegistry);

        return decorated;
    }
	
	@Bean
    public FraudCommand kycCommand(KYCCheckCommand kycCommand, MeterRegistry meterRegistry,
    		CircuitBreakerRegistry cbRegistry) {

        FraudCommand decorated = kycCommand;
        
        decorated = new LoggingFraudCommandDecorator(decorated);
        decorated = new RetryCommandDecorator(decorated, 2);
        decorated = new TimeoutCommandDecorator(decorated, Duration.ofMillis(500));
        decorated = new CircuitBreakerCommandDecorator(decorated,
        		cbRegistry.circuitBreaker("kycService"));
        decorated = new MetricsCommandDecorator(decorated, meterRegistry);

        return decorated;
    }
	
	@Bean
    public FraudCommand mlScoreCommand(MLScoreCommand mlScoreCommand, MeterRegistry meterRegistry,
    		CircuitBreakerRegistry cbRegistry) {

        FraudCommand decorated = mlScoreCommand;
        
        decorated = new LoggingFraudCommandDecorator(decorated);
        decorated = new RetryCommandDecorator(decorated, 2);
        decorated = new TimeoutCommandDecorator(decorated, Duration.ofMillis(500));
        decorated = new CircuitBreakerCommandDecorator(decorated,
        		cbRegistry.circuitBreaker("kycService"));
        decorated = new MetricsCommandDecorator(decorated, meterRegistry);

        return decorated;
    }
}
