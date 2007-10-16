package org.sodeja.parsec.examples.lisp.executor.primitive;

import org.sodeja.math.Rational;

public abstract class AbstractNumericalProcedure implements PrimitiveProcedure {
	protected Rational convert(Object obj) {
		if(obj instanceof Rational) {
			return (Rational) obj;
		}
		
		throw new IllegalArgumentException("Wrong value type: " + obj.getClass());
	}
}
