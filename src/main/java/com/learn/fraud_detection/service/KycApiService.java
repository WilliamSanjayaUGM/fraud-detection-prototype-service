package com.learn.fraud_detection.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.learn.fraud_detection.model.KycRequest;
import com.learn.fraud_detection.model.KycResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KycApiService {
	private final WebClient webClient;
	
	public boolean isUserVerified(UUID userId) {
		KycRequest request = new KycRequest(userId);

        KycResponse response = webClient.post()
                .uri("/kyc/status")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(KycResponse.class)
                .block();

        return response.isVerified();
	}
}
