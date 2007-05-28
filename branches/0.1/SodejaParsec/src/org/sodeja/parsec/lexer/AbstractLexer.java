package org.sodeja.parsec.lexer;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLexer<T> {
	protected PushbackReader reader;
	protected List<T> tokens;
	protected StringBuilder helper;

	public AbstractLexer(Reader originalReader) {
		reader = new PushbackReader(originalReader, 1); // TODO look ahead dependant
		tokens = new ArrayList<T>();
		helper = new StringBuilder();
	}

	public List<T> tokenize() {
		tokens.clear();
		
		for(Character ch = readChar(); ch != null; ch = readChar()) {
			tokenizeDelegate(ch);
		}
		
		return tokens;
	}
	
	protected abstract void tokenizeDelegate(char ch);
	
	protected abstract T createTokenFrom(String str);
	
	protected Character readChar() {
		try {
			int value = reader.read();
			if(value == -1) {
				return null;
			}
			return (char) value;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected void unreadChar(char ch) {
		try {
			reader.unread(ch);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
