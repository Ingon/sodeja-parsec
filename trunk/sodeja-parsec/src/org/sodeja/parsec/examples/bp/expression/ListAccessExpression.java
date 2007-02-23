package org.sodeja.parsec.examples.bp.expression;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

import org.sodeja.lang.reflect.ReflectUtils;

public class ListAccessExpression extends Expression {
	public final Integer number;

	public ListAccessExpression(String name, final Integer number) {
		super(name);
		this.number = number;
	}

	@Override
	public Object read(Map<String, Object> context, Object exprContext) {
		Object obj = ReflectUtils.getFieldValue(exprContext, name);
		if(obj instanceof List) {
			return ((List) obj).get(number);
		} else if(obj.getClass().isArray()) {
			return Array.get(obj, number);
		}
		
		throw new IllegalArgumentException("Object with name " + name + " is not valid in " + exprContext);
	}
	
	@Override
	public String toString() {
		return name + "[" + number + "]";
	}
}
