package com.learn.fraud_detection.decorator;

import com.learn.fraud_detection.command.FraudCommand;

public abstract class FraudCommandDecorator implements FraudCommand{
	protected final FraudCommand delegate;
	
	protected FraudCommandDecorator(FraudCommand delegate) {
        this.delegate = delegate;
    }
}
