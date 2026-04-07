package com.learn.fraud_detection.command;

import java.util.concurrent.CompletableFuture;

import com.learn.fraud_detection.model.FraudCheckResult;
import com.learn.fraud_detection.model.TransactionContext;
import com.learn.fraud_detection.service.DeviceFingerprintService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeviceFingerprintCommand implements FraudCommand {
	
	private final DeviceFingerprintService deviceService;

	@Override
	public CompletableFuture<FraudCheckResult> execute(TransactionContext context) {
		return CompletableFuture.supplyAsync(() -> {

            boolean suspicious = deviceService.isSuspiciousDevice(
                    context.getDeviceFingerprint()
            );

            if (suspicious) {
                return FraudCheckResult.review(
                        "Suspicious device detected", 60
                );
            }

            return FraudCheckResult.pass();
        });
	}
	
}
