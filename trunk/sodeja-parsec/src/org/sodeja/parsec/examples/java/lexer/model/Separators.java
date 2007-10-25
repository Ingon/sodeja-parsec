package org.sodeja.parsec.examples.java.lexer.model;

public enum Separators {
	OPEN_BRACE("("),
	CLOSE_BRACE(")"),
	
	OPEN_CURLY("{"),
	CLOSE_CURLY("}"),

	OPEN_RECT("["),
	CLOSE_RECT("]"),
	
	SEMI_COLON(";"),
	COMMA(","),
	DOT(".");
	
	private final String value;
	
	private Separators(String value) {
		this.value = value;
	}

	public static Separators valueOf(Character ch) {
		Separators[] values = values();
		for(int i = 0;i < values.length;i++) {
			if(values[i].value.charAt(0) == ch) {
				return values[i];
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
