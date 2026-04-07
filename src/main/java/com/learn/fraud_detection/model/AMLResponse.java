package com.learn.fraud_detection.model;

import lombok.Data;

@Data
public class AMLResponse {
	private boolean highRisk;
	private String riskCategory;
	private String matchedList;
}
