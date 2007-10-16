package org.sodeja.parsec.examples.lisp.executor.primitive;

import org.sodeja.math.Rational;

public class MulProcedure extends AbstractNumericalProcedure {
	@Override
	public Object execute(Object... vals) {
		Rational result = Rational.ONE;
		for(Object obj : vals) {
			result = result.multiply(convert(obj));
		}
		return result;
	}
}
