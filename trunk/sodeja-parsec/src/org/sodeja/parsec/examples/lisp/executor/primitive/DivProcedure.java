package org.sodeja.parsec.examples.lisp.executor.primitive;

import org.sodeja.math.Rational;

public class DivProcedure extends AbstractNumericalProcedure {
	@Override
	public Object execute(Object... vals) {
		Rational div = convert(vals[0]);
		for(int i = 1, n = vals.length;i < n;i++) {
			div = div.divide(convert(vals[i]));
		}
		return div;
	}
}
