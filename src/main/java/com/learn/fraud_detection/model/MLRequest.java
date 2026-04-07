package com.learn.fraud_detection.model;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MLRequest {
	
	private BigDecimal amount;
	private int transactionsLastMinute;
	private boolean locationAnomaly;
	private boolean newDevice;
	private boolean vpnDetected;
	private boolean blacklistedUser;
	private boolean highRiskCountry;
	
	public static MLRequest from(TransactionContext context) {
	    return MLRequest.builder()
	            .amount(context.getAmount())
	            .transactionsLastMinute(context.getTransactionLastMinute())
	            .locationAnomaly(context.isLocationAnomaly())
	            .newDevice(context.isNewDevice())
	            .vpnDetected(context.isVpnDetected())
	            .blacklistedUser(context.isBlacklistedUser())
	            .highRiskCountry(context.isHighRiskCountry())
	            .build();
	}
}
