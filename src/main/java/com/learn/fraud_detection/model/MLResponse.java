package com.learn.fraud_detection.model;

import lombok.Data;

@Data
public class MLResponse {
	private int riskScore;
	private String modelVersion;
	private String decision;
}
