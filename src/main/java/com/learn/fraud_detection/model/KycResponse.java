package com.learn.fraud_detection.model;

import lombok.Data;

@Data
public class KycResponse {
	private boolean verified;
	private String verificationLevel;
	private String status;
}
