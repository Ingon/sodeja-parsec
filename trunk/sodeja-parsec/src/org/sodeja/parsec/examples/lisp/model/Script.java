package org.sodeja.parsec.examples.lisp.model;

import java.util.List;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.VFunction1;

public class Script {
	public final List<Expression> expressions;
	
	public Script(List<Expression> expressions) {
		this.expressions = expressions;
	}
	
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		ListUtils.execute(expressions, new VFunction1<Expression>() {
			@Override
			public void executeV(Expression p) {
				sb.append(p.toString());
				sb.append("\r\n");
			}});
		sb.setLength(sb.length() - 1);
		return sb.toString();
	}
}
