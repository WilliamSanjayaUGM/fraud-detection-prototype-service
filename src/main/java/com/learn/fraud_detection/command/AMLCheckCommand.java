package com.learn.fraud_detection.command;

import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Component;

import com.learn.fraud_detection.model.FraudCheckResult;
import com.learn.fraud_detection.model.TransactionContext;
import com.learn.fraud_detection.service.AMLApiService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AMLCheckCommand implements FraudCommand{
	
	private final AMLApiService amlApiService;
	
	@Override
	public CompletableFuture<FraudCheckResult> execute(TransactionContext context) {
		return CompletableFuture.supplyAsync(() -> {
			boolean risky = amlApiService.checkUser(context.getUserId());

            if (risky) {
                return FraudCheckResult.fail("AML risk detected");
            }

            return FraudCheckResult.pass();
		});
	}

}
