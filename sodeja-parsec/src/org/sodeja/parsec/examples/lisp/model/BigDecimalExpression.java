package org.sodeja.parsec.examples.lisp.model;

import java.math.BigDecimal;

public class BigDecimalExpression implements SimpleExpression {
	public final BigDecimal value;
	
	public BigDecimalExpression(BigDecimal value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value.toString();
	}
}
