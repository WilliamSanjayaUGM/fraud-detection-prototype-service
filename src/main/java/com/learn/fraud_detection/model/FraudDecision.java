package com.learn.fraud_detection.model;

import java.util.List;

import com.learn.fraud_detection.enums.DecisionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class FraudDecision {
	private DecisionType decision;
    private int totalRiskScore;
    private List<String> reasons;

    public static FraudDecision allow() {
        return FraudDecision.builder()
                .decision(DecisionType.ALLOW)
                .totalRiskScore(0)
                .reasons(List.of())
                .build();
    }

    public static FraudDecision block(String reason) {
        return FraudDecision.builder()
                .decision(DecisionType.BLOCK)
                .totalRiskScore(100)
                .reasons(List.of(reason))
                .build();
    }

    public static FraudDecision review(int riskScore, List<String> reasons) {
        return FraudDecision.builder()
                .decision(DecisionType.REVIEW)
                .totalRiskScore(riskScore)
                .reasons(reasons)
                .build();
    }

    public boolean isBlocked() {
        return decision == DecisionType.BLOCK;
    }

    public boolean requiresReview() {
        return decision == DecisionType.REVIEW;
    }
}
