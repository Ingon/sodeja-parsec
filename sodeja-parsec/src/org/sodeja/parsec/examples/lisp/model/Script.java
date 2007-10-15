package org.sodeja.parsec.examples.lisp.model;

import java.util.List;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.VFunction1;

public class Script {
	public final List<SExpression> expressions;
	
	public Script(List<SExpression> expressions) {
		this.expressions = expressions;
	}
	
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		ListUtils.execute(expressions, new VFunction1<SExpression>() {
			@Override
			public void executeV(SExpression p) {
				sb.append(p.toString());
				sb.append("\r\n");
			}});
		sb.setLength(sb.length() - 1);
		return sb.toString();
	}
}
