package org.sodeja.parsec.examples.java;

import java.io.Reader;

import org.sodeja.parsec.examples.java.lmodel.JToken;
import org.sodeja.parsec.lexer.AbstractLexer;

public class JavaLexer extends AbstractLexer<JToken> {
	public JavaLexer(Reader originalReader) {
		super(originalReader);
	}

	@Override
	protected void tokenizeDelegate(char ch) {
	}

	@Override
	protected JToken createTokenFrom(String str) {
		// TODO Auto-generated method stub
		return null;
	}
}
