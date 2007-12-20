package org.sodeja.parsec.self;

public class Factor {
	public final Integer repeat;
	public final Primary primary;
	
	public Factor(Integer repeat, Primary primary) {
		this.repeat = repeat;
		this.primary = primary;
	}
}
