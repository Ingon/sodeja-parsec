package org.sodeja.parsec.self;

public class Term {
	public final Factor factor;
	public final Factor exception;
	
	public Term(Factor factor, Factor exception) {
		this.factor = factor;
		this.exception = exception;
	}
}
