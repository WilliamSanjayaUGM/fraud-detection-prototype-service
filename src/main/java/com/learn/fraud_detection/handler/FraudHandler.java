package com.learn.fraud_detection.handler;

import com.learn.fraud_detection.model.TransactionContext;

public abstract class FraudHandler {
	protected FraudHandler next;
	
	public void setNext(FraudHandler next) {
        this.next = next;
    }
	
	public boolean check(TransactionContext tx) {
		if(!doCheck(tx)) 
			return false;
		
		if(next != null)
			return next.check(tx);
		
		return true;
	}
	
	protected abstract boolean doCheck(TransactionContext tx);
}
