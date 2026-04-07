package com.learn.fraud_detection.command;

import java.util.concurrent.CompletableFuture;

import com.learn.fraud_detection.model.FraudCheckResult;
import com.learn.fraud_detection.model.TransactionContext;
import com.learn.fraud_detection.service.FraudMLService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MLScoreCommand implements FraudCommand {
	
	private final FraudMLService mlService;

	@Override
	public CompletableFuture<FraudCheckResult> execute(TransactionContext context) {
		return CompletableFuture.supplyAsync(() -> {

            int score = mlService.getRiskScore(context);

            if (score > 80) {
                return FraudCheckResult.fail("High ML fraud score");
            }

            if (score > 60) {
                return FraudCheckResult.review("Medium ML fraud score", score);
            }

            return FraudCheckResult.pass();
        });
	}
}
