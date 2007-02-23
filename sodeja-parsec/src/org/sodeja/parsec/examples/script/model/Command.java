package org.sodeja.parsec.examples.script.model;

import java.util.List;

public class Command {
	public final String name;
	public final List<Property> properties;
	
	public Command(final String name, final List<Property> properties) {
		this.name = name;
		this.properties = properties;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(name);
		for(Property prop : properties) {
			sb.append(" ");
			sb.append(prop);
		}
		sb.append(";");
		
		return sb.toString();
	}
}
