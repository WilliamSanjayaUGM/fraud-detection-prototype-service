package com.learn.fraud_detection.command;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import com.learn.fraud_detection.model.FraudCheckResult;
import com.learn.fraud_detection.model.TransactionContext;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExternalFraudCheckerExecutor {
	private final List<FraudCommand> commands;
	
	public List<FraudCheckResult> execute(TransactionContext context) {
		List<CompletableFuture<FraudCheckResult>> futures = commands.stream()
				.map(c -> c.execute(context))
                .toList();
		
		CompletableFuture.allOf(
				futures.toArray(new CompletableFuture[0])
				).join();
		
		return futures.stream()
				.map(CompletableFuture::join)
                .toList();
	}
}
