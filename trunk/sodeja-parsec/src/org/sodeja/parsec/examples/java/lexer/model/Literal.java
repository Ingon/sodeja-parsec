package org.sodeja.parsec.examples.java.lexer.model;

public abstract class Literal<T> extends Token<T> {
	
	public static class IntegerLiteral extends Literal<Integer> {
		protected IntegerLiteral(Integer value) {
			super(value);
		}

		@Override
		public String toString() {
			return "(I " + value + ")";
		}
	}
	
	public static class LongLiteral extends Literal<Long> {
		protected LongLiteral(Long value) {
			super(value);
		}

		@Override
		public String toString() {
			return "(L " + value + ")";
		}
	}
	
	public static class FloatLiteral extends Literal<Float> {
		protected FloatLiteral(Float value) {
			super(value);
		}

		@Override
		public String toString() {
			return "(F " + value + ")";
		}
	}

	public static class DoubleLiteral extends Literal<Double> {
		protected DoubleLiteral(Double value) {
			super(value);
		}

		@Override
		public String toString() {
			return "(D " + value + ")";
		}
	}
	
	public static class StringLiteral extends Literal<String> {
		protected StringLiteral(String value) {
			super(value);
		}

		@Override
		public String toString() {
			return "(S " + value + ")";
		}
	}
	
	public static class CharacterLiteral extends Literal<Character> {
		protected CharacterLiteral(Character value) {
			super(value);
		}

		@Override
		public String toString() {
			return "(C " + value + ")";
		}
	}

	private static class BooleanLiteral extends Literal<BooleanLiterals> {
		protected BooleanLiteral(BooleanLiterals value) {
			super(value);
		}

		@Override
		public String toString() {
			return "(B " + value.name() + ")";
		}
	}

	private static BooleanLiteral TRUE = new BooleanLiteral(BooleanLiterals.TRUE);
	private static BooleanLiteral FALSE = new BooleanLiteral(BooleanLiterals.FALSE);
	
	private static class NullLiteral extends Literal<Void> {
		protected NullLiteral() {
			super(null);
		}		

		@Override
		public String toString() {
			return "(N)";
		}
	}
	
	private static NullLiteral NULL = new NullLiteral();
	
	protected Literal(T value) {
		super(value);
	}
	
	public static InputElement booleanLiteral(BooleanLiterals booleanLiterals) {
		if(booleanLiterals == BooleanLiterals.TRUE) {
			return TRUE;
		} else {
			return FALSE;
		}
	}
	
	public static InputElement nullLiteral() {
		return NULL;
	}

	public static InputElement integer(String string) {
		return new IntegerLiteral(new Integer(string));
	}
}
