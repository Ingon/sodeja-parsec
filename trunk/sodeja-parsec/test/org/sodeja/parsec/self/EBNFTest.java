package org.sodeja.parsec.self;

import java.io.StringReader;
import java.util.List;

import org.sodeja.parsec.self.model.Syntax;

import junit.framework.TestCase;

public class EBNFTest extends TestCase {
	public void testSimple() {
//		String txt = "syntax = syntax_rule, {syntax_rule};";
//		String txt = "syntax_rule = meta_identifier, '=', definitions_list, ';';";
		String txt = "all_characters = ? all visible characters ?;";
		EBNFGrammarLexer lexer = new EBNFGrammarLexer(new StringReader(txt));
		List<String> tokens = lexer.tokenize();
		
		System.out.println("Tokens: " + tokens);
		
		EBNFGrammarParser parser = new EBNFGrammarParser();
		Syntax syntax = parser.parse(tokens);
		
		System.out.println("Syntax: " + syntax);
	}
	
	public void testJson() {
		String txt = "object = '{', [members], '}';" +
				"members = {member};" +
				"member = pair, ',', [members];" +
				"pair = string, ':', value;" +
				"array = '[', [elements], ']';" +
				"elements = {element};" +
				"element = value, ',', [elements];" +
				"value = string | number | object | array | 'true' | 'false' | 'null';" +
				"string = ?string?;" +
				"number = ?integer?;";
		
		EBNFGrammarLexer lexer = new EBNFGrammarLexer(new StringReader(txt));
		List<String> tokens = lexer.tokenize();
		
		System.out.println("Tokens: " + tokens);
		
		EBNFGrammarParser parser = new EBNFGrammarParser();
		Syntax syntax = parser.parse(tokens);
		
		System.out.println("Syntax: " + syntax);
	}
	
	public static void main(String[] args) {
		new EBNFTest().testJson();
	}
}
