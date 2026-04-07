package com.learn.fraud_detection.command;

import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Component;

import com.learn.fraud_detection.model.FraudCheckResult;
import com.learn.fraud_detection.model.TransactionContext;
import com.learn.fraud_detection.service.KycApiService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KYCCheckCommand implements FraudCommand{
	private final KycApiService kycApiService;

	@Override
	public CompletableFuture<FraudCheckResult> execute(TransactionContext context) {
		return CompletableFuture.supplyAsync(() -> {

            boolean verified = kycApiService.isUserVerified(context.getUserId());

            if (!verified) {
                return FraudCheckResult.fail("KYC verification failed");
            }

            return FraudCheckResult.pass();
        });
	}
	
}
