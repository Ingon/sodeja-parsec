package org.sodeja.parsec.examples.bp.expression;

import java.util.Map;

import org.sodeja.lang.reflect.ReflectUtils;

public class ValueExpression extends Expression {
	public ValueExpression(final String element) {
		super(element);
	}
	
	@Override
	public Object read(Map<String, Object> rootContext) {
		return rootContext.get(name);
	}

	@Override
	public Object read(Map<String, Object> rootContext, Object exprContext) {
		return ReflectUtils.getFieldValue(exprContext, name);
	}

	@Override
	public String toString() {
		return name;
	}
}
