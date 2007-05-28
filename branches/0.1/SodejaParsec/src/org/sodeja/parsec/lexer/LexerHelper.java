package org.sodeja.parsec.lexer;

public class LexerHelper {
	private LexerHelper() {
	}
	
	public static<T> boolean readEOL(AbstractLexer<T> lexer, Character ch) {
		if(ch == null) {
			return false;
		}
		
		if(ch == '\n') {
			return true;
		}
		
		if(ch == '\r') {
			Character next = lexer.readChar();
			if(next == '\n') {
				return true;
			}
			lexer.unreadChar(next);
			return true;
		}
		
		return false;
	}

	public static<T> void readTillEOL(AbstractLexer<T> lexer) {
		while(! readEOL(lexer, lexer.readChar())) {
			;
		}
	}

	public static <T> void readNumberToken(AbstractLexer<T> lexer, char initial) {
		lexer.helper.setLength(0);
		lexer.helper.append(initial);
		
		Character ch = null;
		for(ch = lexer.readChar(); Character.isDigit(ch) ; ch = lexer.readChar()) {
			lexer.helper.append(ch);
		}
		lexer.unreadChar(ch);
		
		lexer.tokens.add(lexer.createTokenFrom(lexer.helper.toString()));
	}

	public static <T> void readIdentifier(AbstractLexer<T> lexer, char initial) {
		lexer.helper.setLength(0);
		lexer.helper.append(initial);
		
		Character ch = null;
		for(ch = lexer.readChar(); Character.isJavaIdentifierPart(ch) ; ch = lexer.readChar()) {
			lexer.helper.append(ch);
		}
		lexer.unreadChar(ch);
		
		lexer.tokens.add(lexer.createTokenFrom(lexer.helper.toString()));
	}

	public static <T> void readString(AbstractLexer<T> lexer, char initial) {
		lexer.helper.setLength(0);
		lexer.helper.append(initial);

		Character ch = null;
		for(ch = lexer.readChar(); ch != '\"' ; ch = lexer.readChar()) {
			if(ch == '\\') {
				Character ch1 = lexer.readChar();
				if(ch1 == '\"') {
					lexer.helper.append(ch1);
					continue;
				}
				lexer.unreadChar(ch1);
			}
			lexer.helper.append(ch);
		}
		lexer.helper.append('\"');
		
		lexer.tokens.add(lexer.createTokenFrom(lexer.helper.toString()));
	}
}
