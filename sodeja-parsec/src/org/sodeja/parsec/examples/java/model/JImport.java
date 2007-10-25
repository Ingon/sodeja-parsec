package org.sodeja.parsec.examples.java.model;

public class JImport {
	public final boolean isStatic;
	public final JQualifiedIdentifier name;
	public final boolean isAll;
	
	public JImport(Boolean isStatic, JQualifiedIdentifier name, Boolean isAll) {
		this.isStatic = isStatic;
		this.name = name;
		this.isAll = isAll;
	}

	@Override
	public String toString() {
		return "import " + (isStatic ? "static " : "") + name + (isAll ? ".*" : "") + ";";
	}
}
