package org.sodeja.parsec.examples.java.lexer.model;

public abstract class TerminalSymbol {
	private static class LineTerminator extends TerminalSymbol {
		@Override
		public boolean isLineTerminator() {
			return true;
		}

		@Override
		public Character value() {
			throw new IllegalArgumentException();
		}

		@Override
		public String toString() {
			return " ";
		}

		@Override
		public boolean equals(Object obj) {
			return this == terminatorInstance;
		}
	}
	
	private static final LineTerminator terminatorInstance = new LineTerminator();
	
	private static class InputCharacter extends TerminalSymbol {
		public final Character value;
		public InputCharacter(Character value) {
			this.value = value;
		}
		
		@Override
		public boolean isLineTerminator() {
			return false;
		}

		@Override
		public Character value() {
			return value;
		}

		@Override
		public String toString() {
			return String.valueOf(value);
		}

		@Override
		public boolean equals(Object obj) {
			return obj instanceof InputCharacter && value.equals(((InputCharacter) obj).value);
		}
	}
	
	public abstract boolean isLineTerminator();
	
	public abstract Character value();
	
	public static TerminalSymbol line() {
		return terminatorInstance;
	}
	
	public static TerminalSymbol input(Character ch) {
		return new InputCharacter(ch);
	}
}
