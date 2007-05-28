package org.sodeja.parsec.examples.bp.model;

import java.util.List;
import java.util.Map;

import org.sodeja.collections.ArrayUtils;
import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Function1;
import org.sodeja.lang.reflect.ReflectUtils;

public class Method implements Expression {
	public final String name;
	public List<BeanPath> params;
	
	public Method(final String name, final List<BeanPath> params) {
		this.name = name;
		this.params = params;
	}

	public Object read(Map<String, Object> rootContext) {
		throw new IllegalStateException("Can't be part of a starting expression");
	}
	
	public Object read(final Map<String, Object> rootContext, Object context) {
		List<Object> executionResultsList = ListUtils.map(params, new Function1<Object, BeanPath>(){
			public Object execute(BeanPath p) {
				return p.read(rootContext);
			}});
		
		Object[] executionResults = ListUtils.asArray(executionResultsList);
		// TODO generic search, nulls check
		Class[] expectedClasses = ArrayUtils.map(executionResults, new Function1<Class, Object>() {
			public Class execute(Object p) {
				return p.getClass();
			}});
		
		return ReflectUtils.executeMethod(context, name, expectedClasses, executionResults);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		sb.append("(");
		for(BeanPath bp : params) {
			sb.append(bp);
			sb.append(", ");
		}
		if(params.size() > 0) {
			sb.setLength(sb.length() - 2);
		}
		sb.append(")");
		return sb.toString();
	}
}
