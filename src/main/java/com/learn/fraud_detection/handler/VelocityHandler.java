package com.learn.fraud_detection.handler;

import org.springframework.stereotype.Component;

import com.learn.fraud_detection.model.TransactionContext;

@Component
public class VelocityHandler extends FraudHandler{

	@Override
	protected boolean doCheck(TransactionContext tx) {
		if(tx.getTransactionLastMinute() >5) {
			return false;
		}
		return true;
	}

}
