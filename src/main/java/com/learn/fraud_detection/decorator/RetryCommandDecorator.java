package com.learn.fraud_detection.decorator;

import java.util.concurrent.CompletableFuture;

import com.learn.fraud_detection.command.FraudCommand;
import com.learn.fraud_detection.model.FraudCheckResult;
import com.learn.fraud_detection.model.TransactionContext;

public class RetryCommandDecorator extends FraudCommandDecorator{
	
	private final int maxRetries;
	
	public RetryCommandDecorator(FraudCommand delegate, int maxRetries) {
		super(delegate);
		this.maxRetries = maxRetries;
	}

	@Override
	public CompletableFuture<FraudCheckResult> execute(TransactionContext context) {
		return attempt(context, 0);
	}
	
	private CompletableFuture<FraudCheckResult> attempt(TransactionContext context, int retry) {

        return delegate.execute(context)
                .exceptionallyCompose(ex -> {

                    if (retry < maxRetries) {
                        return attempt(context, retry + 1);
                    }

                    return CompletableFuture.completedFuture(
                            FraudCheckResult.review("Retry limit reached", 40)
                    );
                });
    }
}
