package com.learn.fraud_detection.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionContext {
	
	private UUID userId;
	private UUID merchantId;
	
	private BigDecimal amount;
	private String currency;
	private Instant transactionTime;
	
	private String ipAddress;
	private String country;
	private String city;
	
	private String deviceId;
	private String deviceFingerprint;
	private String userAgent;
	
	private int transactionLastMinute;
	private int transactionsLastHour;
    private int transactionsLastDay;
    
    private BigDecimal avgTransactionAmount;

    // ===== RISK FLAGS =====
    private boolean locationAnomaly;
    private boolean newDevice;
    private boolean vpnDetected;

    // ===== ACCOUNT STATUS =====
    private boolean blacklistedUser;
    private boolean highRiskCountry;
}
