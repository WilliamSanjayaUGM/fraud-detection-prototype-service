package com.learn.fraud_detection.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.learn.fraud_detection.model.MLRequest;
import com.learn.fraud_detection.model.MLResponse;
import com.learn.fraud_detection.model.TransactionContext;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FraudMLService {
	
	private final WebClient webClient;
	
	public int getRiskScore(TransactionContext context) {
        MLRequest request = MLRequest.from(context);

        MLResponse response = webClient.post()
                .uri("/ml/fraud-score")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(MLResponse.class)
                .block();

        return response.getRiskScore();
	}
	
}