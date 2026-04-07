package com.learn.fraud_detection.command;

import java.util.concurrent.CompletableFuture;

import com.learn.fraud_detection.model.FraudCheckResult;
import com.learn.fraud_detection.model.TransactionContext;
import com.learn.fraud_detection.service.AMLApiService;

import lombok.RequiredArgsConstructor;

// No Need to put the @Component annotation so there won't be duplicate bean. Let the instantiation will be done through the decorator config
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
