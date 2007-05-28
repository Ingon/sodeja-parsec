package org.sodeja.parsec.examples.script;

import java.io.Reader;

import org.sodeja.parsec.lexer.AbstractLexer;
import org.sodeja.parsec.lexer.LexerHelper;

public class ScriptLexer extends AbstractLexer<String> {

	public ScriptLexer(Reader originalReader) {
		super(originalReader);
	}

	@Override
	protected String createTokenFrom(String str) {
		return str;
	}

	@Override
	protected void tokenizeDelegate(char ch) {
		if(Character.isWhitespace(ch)) {
			return;
		}
		
		if(ch == '#') {
			LexerHelper.readTillEOL(this);
			return;
		}
		
		if(Character.isDigit(ch)) {
			LexerHelper.readNumberToken(this, ch);
			return;
		}
		
		if(Character.isJavaIdentifierStart(ch)) {
			LexerHelper.readIdentifier(this, ch);
			return;
		}
		
		tokens.add(String.valueOf(ch));
	}
}
