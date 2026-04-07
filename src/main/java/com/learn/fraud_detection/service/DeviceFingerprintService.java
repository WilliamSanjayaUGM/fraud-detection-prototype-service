package com.learn.fraud_detection.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.learn.fraud_detection.model.DeviceRequest;
import com.learn.fraud_detection.model.DeviceResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeviceFingerprintService {
	private final WebClient webClient;
	
	public boolean isSuspiciousDevice(String fingerprint) {
		DeviceRequest request = new DeviceRequest(fingerprint);

        DeviceResponse response = webClient.post()
                .uri("/device/check")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(DeviceResponse.class)
                .block();

        return response.isSuspicious();
	}
}
