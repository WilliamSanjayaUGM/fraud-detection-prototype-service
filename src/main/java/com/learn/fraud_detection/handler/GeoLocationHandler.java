package com.learn.fraud_detection.handler;

import org.springframework.stereotype.Component;

import com.learn.fraud_detection.model.TransactionContext;

@Component
public class GeoLocationHandler extends FraudHandler{

	@Override
	protected boolean doCheck(TransactionContext tx) {
		if(tx.isLocationAnomaly()) {
			return false;
		}
		return true;
	}

}
