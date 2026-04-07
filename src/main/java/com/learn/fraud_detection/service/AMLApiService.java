package com.learn.fraud_detection.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.learn.fraud_detection.model.AMLRequest;
import com.learn.fraud_detection.model.AMLResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AMLApiService {
	private final WebClient webClient;
	
	public boolean checkUser(UUID userId) {
		AMLRequest request = new AMLRequest(userId);

        AMLResponse response = webClient.post()
                .uri("/aml/check")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(AMLResponse.class)
                .block();

        return response.isHighRisk();
	}
}
