package org.sodeja.parsec.examples.bp.expression;

import java.util.List;
import java.util.Map;

import org.sodeja.collections.ArrayUtils;
import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Function1;
import org.sodeja.lang.reflect.ReflectUtils;

public class ApplyExpression extends Expression {

	public final List<BeanPath> parameters;
	
	public ApplyExpression(final String name, final List<BeanPath> parameters) {
		super(name);
		this.parameters = parameters;
	}

	@Override
	public Object read(final Map<String, Object> rootContext, final Object exprContext) {
		List<Object> executionResultsList = ListUtils.map(parameters, new Function1<Object, BeanPath>(){
			public Object execute(BeanPath p) {
				return p.read(rootContext);
			}});
		
		Object[] executionResults = ListUtils.asArray(executionResultsList);
		// TODO generic search, nulls check
		Class[] expectedClasses = ArrayUtils.map(executionResults, new Function1<Class, Object>() {
			public Class execute(Object p) {
				return p.getClass();
			}});
		
		return ReflectUtils.executeMethod(exprContext, name, expectedClasses, executionResults);
	}
}
