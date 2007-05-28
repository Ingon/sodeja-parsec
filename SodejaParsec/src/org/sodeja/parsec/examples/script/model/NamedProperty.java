package org.sodeja.parsec.examples.script.model;

public class NamedProperty extends Property {
	public final String name;
	public final PropertyValue value;
	
	public NamedProperty(final String name, final PropertyValue value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public String toString() {
		return name + "=" + value;
	}
}
