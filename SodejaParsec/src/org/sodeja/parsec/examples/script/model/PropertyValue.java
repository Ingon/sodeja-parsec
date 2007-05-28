package org.sodeja.parsec.examples.script.model;

public class PropertyValue extends Property {
	public final String value;

	public PropertyValue(final String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}
