package org.sodeja.parsec.examples.bp.model;

import java.util.List;

public class Path {
	public final PathElement start;
	public final List<PathElement> rest;
	
	public Path(final PathElement start, final List<PathElement> rest) {
		this.start = start;
		this.rest = rest;
	}
}
