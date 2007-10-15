package org.sodeja.parsec.examples.lisp;

import java.io.Reader;

import org.sodeja.collections.ListUtils;
import org.sodeja.parsec.lexer.AbstractLexer;
import org.sodeja.parsec.lexer.LexerHelper;

public class LispLexer extends AbstractLexer<String> {
	public LispLexer(Reader originalReader) {
		super(originalReader);
	}

	@Override
	protected String createTokenFrom(String str) {
		return str;
	}

	@Override
	protected void tokenizeDelegate(char ch) {
		if(Character.isWhitespace(ch)) {
			if(! " ".equals(ListUtils.last(tokens))) {
				tokens.add(" ");
			}
			return;
		}

		if(Character.isDigit(ch)) {
			LexerHelper.readNumberToken(this, ch);
			return;
		} else if(Character.isLetter(ch)) {
			LexerHelper.readIdentifierS(this, ch);
			return;
		}
		
		tokens.add(String.valueOf(ch));
	}
}
