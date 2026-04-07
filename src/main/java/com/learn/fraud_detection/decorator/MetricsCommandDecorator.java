package com.learn.fraud_detection.decorator;

import java.util.concurrent.CompletableFuture;

import com.learn.fraud_detection.command.FraudCommand;
import com.learn.fraud_detection.model.FraudCheckResult;
import com.learn.fraud_detection.model.TransactionContext;

import io.micrometer.core.instrument.MeterRegistry;

public class MetricsCommandDecorator extends FraudCommandDecorator{
	
	private final MeterRegistry meterRegistry;
	
	public MetricsCommandDecorator(FraudCommand fraudComand, MeterRegistry meterRegistry) {
		super(fraudComand);
		this.meterRegistry = meterRegistry;
	}

	@Override
	public CompletableFuture<FraudCheckResult> execute(TransactionContext context) {
		long start = System.currentTimeMillis();

        return delegate.execute(context)
                .thenApply(result -> {

                    long duration = System.currentTimeMillis() - start;

                    meterRegistry.timer("fraud.command.latency",
                            "command", delegate.getClass().getSimpleName())
                            .record(duration, java.util.concurrent.TimeUnit.MILLISECONDS);

                    return result;
                });
	}
}
