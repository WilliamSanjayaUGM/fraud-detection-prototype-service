package com.learn.fraud_detection.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.learn.fraud_detection.command.ExternalFraudCheckerExecutor;
import com.learn.fraud_detection.handler.FraudHandler;
import com.learn.fraud_detection.model.FraudCheckResult;
import com.learn.fraud_detection.model.FraudDecision;
import com.learn.fraud_detection.model.TransactionContext;

import lombok.RequiredArgsConstructor;

// Main Entry Point service
@Service
@RequiredArgsConstructor
public class FraudOrchestrator {
	private final FraudHandler fraudChain;
	private final ExternalFraudCheckerExecutor externalExecutor;
	
	public FraudDecision evaluate(TransactionContext context) {
		// Rule Check
		boolean passed = fraudChain.check(context);
		if(!passed) {
            return FraudDecision.block("Rule violation detected");
        }
		
		// External Fraud Check
		List<FraudCheckResult> results = externalExecutor.execute(context);
		
		int riskScore = 0;
        List<String> reasons = new ArrayList<>();

        for(FraudCheckResult result : results) {

            if(result.isFailed()) {
                return FraudDecision.block(result.getReason());
            }

            riskScore += result.getRiskScore();

            if(result.getReason() != null) {
                reasons.add(result.getReason());
            }
        }
        
        // Decision
        if(riskScore > 80) {
            return FraudDecision.review(riskScore, reasons);
        }

        return FraudDecision.allow();
	}
}
