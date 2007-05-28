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
	public Object read(Map<String, Object> rootContext) {
		return readFromMap(ROOT_CONTEXT, rootContext.get(name));
	}
	
	@Override
	public Object read(Map<String, Object> rootContext, Object exprContext) {
		Object obj = ReflectUtils.getFieldValue(exprContext, name);
		return readFromMap(exprContext, obj);
	}
	
	private Object readFromMap(Object exprContext, Object obj) {
		if(obj instanceof Map) {
			return ((Map) obj).get(element);
		}
		
		throw new IllegalArgumentException("Object with name " + name + " is not valid in " + exprContext);
	}
	
	@Override
	public String toString() {
		return name + "{" + element + "}";
	}
}
