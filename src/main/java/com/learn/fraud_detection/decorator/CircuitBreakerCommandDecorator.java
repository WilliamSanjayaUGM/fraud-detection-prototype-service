package com.learn.fraud_detection.decorator;

import java.util.concurrent.CompletableFuture;

import com.learn.fraud_detection.command.FraudCommand;
import com.learn.fraud_detection.model.FraudCheckResult;
import com.learn.fraud_detection.model.TransactionContext;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;

public class CircuitBreakerCommandDecorator extends FraudCommandDecorator {
	
	private final CircuitBreaker circuitBreaker;
	
	public CircuitBreakerCommandDecorator(FraudCommand fraudCommand, CircuitBreaker circuitBreaker) {
		super(fraudCommand);
		this.circuitBreaker = circuitBreaker;
	}

	@Override
	public CompletableFuture<FraudCheckResult> execute(TransactionContext context) {
		return circuitBreaker.executeCompletionStage(
                () -> delegate.execute(context)
        ).toCompletableFuture()
         .exceptionally(ex ->
                 FraudCheckResult.review("External fraud API unavailable", 40)
         );
	}
	
}
