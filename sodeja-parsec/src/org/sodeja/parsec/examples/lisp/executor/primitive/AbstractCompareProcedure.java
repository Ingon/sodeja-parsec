package org.sodeja.parsec.examples.lisp.executor.primitive;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.sodeja.collections.ArrayUtils;

public abstract class AbstractCompareProcedure implements PrimitiveProcedure {
	@Override
	public Object execute(Object... vals) {
		if(ArrayUtils.isEmpty(vals) || vals.length != 2) {
			throw new IllegalArgumentException("Wrong number of arguments!");
		}

		Object first = vals[0];
		Object second = vals[1];
		if(first instanceof BigDecimal && second instanceof BigDecimal) {
			BigDecimal firstConv = ((BigDecimal) first);
			BigDecimal secondConv = (BigDecimal) second;
			return compare(firstConv.compareTo(secondConv));
		} 
		
		if(first instanceof BigDecimal && second instanceof BigInteger) {
			BigDecimal firstConv = ((BigDecimal) first);
			BigInteger secondConv = (BigInteger) second;
			return compare(firstConv.compareTo(new BigDecimal(secondConv)));
		} 

		if(first instanceof BigInteger && second instanceof BigDecimal) {
			BigInteger firstConv = ((BigInteger) first);
			BigDecimal secondConv = (BigDecimal) second;
			return compare((new BigDecimal(firstConv)).compareTo(secondConv));
		}
		
		BigInteger firstConv = ((BigInteger) first);
		BigInteger secondConv = (BigInteger) second;
		return compare(firstConv.compareTo(secondConv));
	}
	
	public abstract Boolean compare(int val);
}
