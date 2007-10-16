package org.sodeja.parsec.examples.lisp.executor.primitive;

import org.sodeja.math.Rational;

public class DivProcedure extends AbstractNumericalProcedure {
	@Override
	public Object execute(Object... vals) {
		Rational div = convert(vals[0]);
		System.out.println("DIVIDER: " + div);
		for(int i = 1, n = vals.length;i < n;i++) {
			Rational converted = convert(vals[i]);
			System.out.println("DIVISOR: " + converted);
			div = div.divide(converted);
		}
		return div;
	}
}
