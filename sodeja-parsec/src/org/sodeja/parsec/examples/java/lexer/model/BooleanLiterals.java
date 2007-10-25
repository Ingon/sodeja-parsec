package org.sodeja.parsec.examples.java.lexer.model;

public enum BooleanLiterals {
	TRUE,
	FALSE;
	
	public static BooleanLiterals find(String str) {
		BooleanLiterals[] values = values();
		for(int i = 0;i < values.length;i++) {
			if(values[i].name().toLowerCase().equals(str)) {
				return values[i];
			}
		}
		return null;
	}
}
