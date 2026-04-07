package com.learn.fraud_detection.model;

import lombok.Data;

@Data
public class DeviceResponse {
	private boolean suspicious;
	private int trustScore;
	private String deviceRiskLevel;
}
