package org.sodeja.parsec.examples.lisp.model;

import java.util.List;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.VFunction1;

public class SExpression implements Expression {
	public final List<Expression> expressions;
	
	public SExpression(List<Expression> expressions) {
		this.expressions = expressions;
	}
	
	public Object apply() {
		return null;
	}
	
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("(");
		ListUtils.execute(expressions, new VFunction1<Expression>() {
			@Override
			public void executeV(Expression p) {
				sb.append(p.toString());
				sb.append(" ");
			}});
		sb.setLength(sb.length() - 1);
		sb.append(")");
		return sb.toString();
	}
}
