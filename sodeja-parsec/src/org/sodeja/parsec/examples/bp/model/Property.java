package org.sodeja.parsec.examples.bp.model;

import java.util.Map;

import org.sodeja.lang.reflect.ReflectUtils;

public class Property implements Expression {
	public final String name;

	public Property(final String name) {
		this.name = name;
	}
	
	public Object read(Map<String, Object> rootContext) {
		return rootContext.get(name);
	}
	
	public Object read(Map<String, Object> rootContext, Object context) {
		return ReflectUtils.getFieldValue(context, name);
	}

	@Override
	public String toString() {
		return name;
	}
}
