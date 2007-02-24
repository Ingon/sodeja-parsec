package org.sodeja.parsec.examples.bp.model;

import java.util.List;

public class BeanPath {
	public final PathElement start;
	public final List<PathElement> rest;
	
	public BeanPath(final PathElement start, final List<PathElement> rest) {
		this.start = start;
		this.rest = rest;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(start);
		for(PathElement el : rest) {
			sb.append(".");
			sb.append(el);
		}
		return sb.toString();
	}
	
}
