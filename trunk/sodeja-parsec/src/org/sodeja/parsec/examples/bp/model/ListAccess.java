package org.sodeja.parsec.examples.bp.model;

public class ListAccess implements Access {
	public final Integer number;
	
	public ListAccess(final Integer number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "[" + number + "]";
	}
}
