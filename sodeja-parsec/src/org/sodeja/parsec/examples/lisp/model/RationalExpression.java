package org.sodeja.parsec.examples.lisp.model;

import org.sodeja.math.Rational;

public class RationalExpression implements SimpleExpression {
	public final Rational value;
	
	public RationalExpression(Rational value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value.toString();
	}
}
