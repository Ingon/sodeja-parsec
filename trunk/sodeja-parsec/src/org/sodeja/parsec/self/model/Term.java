package org.sodeja.parsec.self.model;

public class Term {
	public final Factor factor;
	public final Factor exception;
	
	public Term(Factor factor, Factor exception) {
		this.factor = factor;
		this.exception = exception;
	}
}
