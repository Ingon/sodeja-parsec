package org.sodeja.parsec.examples.lisp.model;

import java.math.BigInteger;

public class BigIntegerExpression implements SimpleExpression {
	public final BigInteger value;
	
	public BigIntegerExpression(BigInteger value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value.toString();
	}
}
