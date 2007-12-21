package org.sodeja.parsec.self;

public class JSONField {
	public final String name;
	public final Object value;
	
	public JSONField(String name, Object value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public String toString() {
		return name + ":" + value;
	}
}
