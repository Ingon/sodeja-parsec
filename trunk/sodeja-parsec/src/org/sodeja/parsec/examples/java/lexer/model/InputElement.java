package org.sodeja.parsec.examples.java.lexer.model;

public abstract class InputElement {
	private static class WhiteSpace extends InputElement {
		@Override
		public String toString() {
			return " ";
		}
	}
	
	private static final WhiteSpace spaceInstance = new WhiteSpace();
	
	private static class Comment extends InputElement {
		public final String text;
		public Comment(String text) {
			this.text = text;
		}
		@Override
		public String toString() {
			return "[C " + text + "]";
		}
	}
	
	protected InputElement() {
	}
	
	public static InputElement space() {
		return spaceInstance;
	}
	
	public static InputElement comment(String text) {
		return new Comment(text);
	}
}
