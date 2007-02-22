package org.sodeja.parsec.examples.bp;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class BPLexer {
	
	private PushbackReader reader;
	private List<String> tokens;
	private StringBuilder helper;
	
	public BPLexer(Reader originalReader) {
		reader = new PushbackReader(originalReader, 1);
		tokens = new ArrayList<String>();
		helper = new StringBuilder();
	}

	public List<String> tokenize() {
		for(Character ch = nextChar(); ch != null; ch = nextChar()) {
			if(Character.isWhitespace(ch)) {
				continue;
			}
			
			if(Character.isDigit(ch)) {
				readNumberToken(ch);
				continue;
			}
			
			if(Character.isJavaIdentifierStart(ch)) {
				readIdentifier(ch);
				continue;
			}
			
			tokens.add(String.valueOf(ch));
		}
		
		return tokens;
	}

	private void readNumberToken(char initial) {
		helper.setLength(0);
		helper.append(initial);
		
		Character ch = null;
		for(ch = nextChar(); Character.isDigit(ch) ; ch = nextChar()) {
			helper.append(ch);
		}
		returnChar(ch);
		
		tokens.add(helper.toString());
	}
	
	private void readIdentifier(char initial) {
		helper.setLength(0);
		helper.append(initial);
		
		Character ch = null;
		for(ch = nextChar(); Character.isJavaIdentifierPart(ch) ; ch = nextChar()) {
			helper.append(ch);
		}
		returnChar(ch);
		
		tokens.add(helper.toString());
	}
	
	private Character nextChar() {
		try {
			int value = reader.read();
//			System.out.println(":" + value);
			if(value == -1) {
				return null;
			}
			return (char) value;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void returnChar(char ch) {
		try {
			reader.unread(ch);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
