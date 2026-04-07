package com.learn.fraud_detection.decorator;

import java.util.concurrent.CompletableFuture;

import com.learn.fraud_detection.command.FraudCommand;
import com.learn.fraud_detection.model.FraudCheckResult;
import com.learn.fraud_detection.model.TransactionContext;

import lombok.extern.slf4j.Slf4j;

// cross-cutting infrastructure concerns
@Slf4j
public class LoggingFraudCommandDecorator extends FraudCommandDecorator{
	
	public LoggingFraudCommandDecorator(FraudCommand delegate) {
		super(delegate);
	}

	@Override
	public CompletableFuture<FraudCheckResult> execute(TransactionContext context) {
		long start = System.currentTimeMillis();
		return delegate.execute(context)
                .thenApply(result -> {

                    long duration = System.currentTimeMillis() - start;
                    log.info("Fraud command {} took {} ms ", delegate.getClass().getSimpleName(), duration);

                    return result;
                });
	}
}
