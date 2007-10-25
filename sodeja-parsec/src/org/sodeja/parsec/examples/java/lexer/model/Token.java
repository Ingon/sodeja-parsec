package org.sodeja.parsec.examples.java.lexer.model;

public abstract class Token<T> extends InputElement {

	public static class Identifier extends Token<String> {
		protected Identifier(String value) {
			super(value);
		}

		@Override
		public String toString() {
			return "I" + value;
		}
	}
	
	public static class Keyword extends Token<Keywords> {
		protected Keyword(Keywords value) {
			super(value);
		}

		@Override
		public String toString() {
			return "K" + value.name().toLowerCase();
		}
	}

	public static class Separator extends Token<Separators> {
		protected Separator(Separators value) {
			super(value);
		}

		@Override
		public String toString() {
			return "S" + value;
		}
	}

	public static class Operator extends Token<Operators> {
		protected Operator(Operators value) {
			super(value);
		}

		@Override
		public String toString() {
			return "O" + value;
		}
	}
	
	public final T value;
	
	protected Token(T value) {
		this.value = value;
	}
	
	public static InputElement identifier(String name) {
		return new Identifier(name);
	}

	public static InputElement keyword(Keywords keyword) {
		return new Keyword(keyword);
	}

	public static InputElement separator(Separators separator) {
		return new Separator(separator);
	}

	public static InputElement operator(Operators operator) {
		return new Operator(operator);
	}
}
