package org.sodeja.parsec.self;

import java.io.StringReader;
import java.util.List;

import junit.framework.TestCase;

public class EBNFTest extends TestCase {
	public void testSimple() {
//		String txt = "syntax = syntax_rule, {syntax_rule};";
//		String txt = "syntax_rule = meta_identifier, '=', definitions_list, ';';";
		String txt = "all_characters = ? all visible characters ?;";
		EBNFLexer lexer = new EBNFLexer(new StringReader(txt));
		List<String> tokens = lexer.tokenize();
		
		System.out.println("Tokens: " + tokens);
		
		EBNFParser parser = new EBNFParser();
		Syntax syntax = parser.parse(tokens);
		
		System.out.println("Syntax: " + syntax);
	}
	
	public static void main(String[] args) {
		new EBNFTest().testSimple();
	}
}
