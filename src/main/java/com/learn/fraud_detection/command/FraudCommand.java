package com.learn.fraud_detection.command;

import java.util.concurrent.CompletableFuture;

import com.learn.fraud_detection.model.FraudCheckResult;
import com.learn.fraud_detection.model.TransactionContext;

// Commands are used for external services
public interface FraudCommand {
	CompletableFuture<FraudCheckResult> execute(TransactionContext context);
}
