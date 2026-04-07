package com.learn.fraud_detection.model;

import com.learn.fraud_detection.enums.FraudStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FraudCheckResult {
	private FraudStatus status;
	private String reason;
	private int riskScore;
	
	public static FraudCheckResult pass() {
		return FraudCheckResult.builder()
				.status(FraudStatus.PASS)
				.riskScore(0)
				.build();
	}
	
	public static FraudCheckResult fail(String reason) {
		return FraudCheckResult.builder()
				.status(FraudStatus.FAIL)
				.reason(reason)
				.riskScore(100)
				.build();
	}
	
	public static FraudCheckResult review(String reason, int riskScore) {
        return FraudCheckResult.builder()
                .status(FraudStatus.REVIEW)
                .reason(reason)
                .riskScore(riskScore)
                .build();
    }

    public boolean isFailed() {
        return status == FraudStatus.FAIL;
    }
}
