package org.sodeja.parsec.examples.lisp.executor.primitive;

import org.sodeja.math.Rational;

public class SumProcedure extends AbstractNumericalProcedure {
	@Override
	public Object execute(Object... vals) {
		Rational result = Rational.ZERO;
		for(Object obj : vals) {
			result = result.add(convert(obj));
		}
		return result;
	}
}
