package org.sodeja.parsec.examples.bp.expression;

import org.sodeja.lang.reflect.ReflectUtils;

public class ValueExpression extends Expression {
	public ValueExpression(final String element) {
		super(element);
	}
	
	@Override
	public Object read(Object context) {
		return ReflectUtils.getFieldValue(context, name);
	}

	@Override
	public String toString() {
		return name;
	}
}
