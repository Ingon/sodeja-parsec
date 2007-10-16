package org.sodeja.parsec.examples.lisp.executor.primitive;

import org.sodeja.collections.ArrayUtils;
import org.sodeja.math.Rational;

public abstract class AbstractCompareProcedure implements PrimitiveProcedure {
	@Override
	public Object execute(Object... vals) {
		if(ArrayUtils.isEmpty(vals) || vals.length != 2) {
			throw new IllegalArgumentException("Wrong number of arguments!");
		}

		if(! (vals[0] instanceof Rational)) {
			throw new IllegalArgumentException("Wrong value type: " + vals[0].getClass());
		}

		if(! (vals[1] instanceof Rational)) {
			throw new IllegalArgumentException("Wrong value type: " + vals[1].getClass());
		}
		
		Rational first = (Rational) vals[0];
		Rational second = (Rational) vals[1];
		return compare(first.compareTo(second));
	}
	
	public abstract Boolean compare(int val);
}
