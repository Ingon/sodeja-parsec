package org.sodeja.parsec.examples.bp.expression;

import java.util.Map;

import org.sodeja.lang.reflect.ReflectUtils;

public class MapAccessExpression extends Expression {
	public final String element;

	public MapAccessExpression(final String name, final String element) {
		super(name);
		this.element = element;
	}

	@Override
	public Object read(Object context) {
		Object obj = ReflectUtils.getFieldValue(context, name);
		if(obj instanceof Map) {
			return ((Map) obj).get(element);
		}
		
		throw new IllegalArgumentException("Object with name " + name + " is not valid in " + context);
	}
	
	@Override
	public String toString() {
		return name + "{" + element + "}";
	}
}
