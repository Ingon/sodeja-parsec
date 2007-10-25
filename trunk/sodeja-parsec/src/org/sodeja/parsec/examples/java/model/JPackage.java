package org.sodeja.parsec.examples.java.model;

public class JPackage {
	public final JQualifiedIdentifier name;

	public JPackage(JQualifiedIdentifier name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "package " + name + ";";
	}
}
