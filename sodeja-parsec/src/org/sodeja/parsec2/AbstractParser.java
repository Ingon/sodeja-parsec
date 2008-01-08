package org.sodeja.parsec2;

public abstract class AbstractParser implements Parser {
	public final String name;

	public AbstractParser(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
}
