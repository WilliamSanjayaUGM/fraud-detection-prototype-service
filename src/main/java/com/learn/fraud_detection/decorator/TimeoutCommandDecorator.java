package com.learn.fraud_detection.decorator;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import com.learn.fraud_detection.command.FraudCommand;
import com.learn.fraud_detection.model.FraudCheckResult;
import com.learn.fraud_detection.model.TransactionContext;

public class TimeoutCommandDecorator extends FraudCommandDecorator{
	private final Duration timeout;
	
	public TimeoutCommandDecorator(FraudCommand delegate, Duration timeout) {
        super(delegate);
        this.timeout = timeout;
    }

	@Override
	public CompletableFuture<FraudCheckResult> execute(TransactionContext context) {
		return delegate.execute(context)
                .orTimeout(timeout.toMillis(), java.util.concurrent.TimeUnit.MILLISECONDS)
                .exceptionally(ex ->
                        FraudCheckResult.review("Fraud check timeout", 30)
                );
	}
}
