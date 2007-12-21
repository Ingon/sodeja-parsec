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
		String txt = "object = '{', members, '}' | '{', '}';" +
				"members = pair, ',', members | pair;" +
				"pair = string, ':', value;" +
				"array = '[', elements, ']' | '[', ']';" +
				"elements = value, ',', elements | value;" +
				"value = 'true' | 'false' | 'null' | string | number | array | object;" +
				"string = ?string?;" +
				"number = ?integer?;";
//		String txt = "object = '{', [members], '}';" +
//				"members = {member};" +
//				"member = pair, [',', members];" +
//				"pair = string, ':', value;" +
//				"array = '[', [elements], ']';" +
//				"elements = {element};" +
//				"element = value, [',', elements];" +
//				"value = string | number | object | array | 'true' | 'false' | 'null';" +
//				"string = ?string?;" +
//				"number = ?integer?;";
		
		EBNFGrammarLexer glexer = new EBNFGrammarLexer(new StringReader(txt));
		List<String> tokens = glexer.tokenize();
		
		System.out.println("Tokens: " + tokens);
		
		EBNFGrammarParser gparser = new EBNFGrammarParser();
		Syntax syntax = gparser.parse(tokens);
		
		System.out.println("Syntax: " + syntax);
		
		String jsonTest = "{" +
			"'class': 'com.icw.phr.fedex.FedexCall', " +
			"'serviceName': 'service1', " +
			"'methodName': 'method1', " + 
			"'params': ['someScope1', {}, {'val' : true}]}";
		
		JSONLexer lexer = new JSONLexer(new StringReader(jsonTest));
		List<String> jsonTokens = lexer.tokenize();
		
		System.out.println("JTokens: " + jsonTokens);
		
		EBNFParser parser = new EBNFParser(syntax);
		Node node = parser.parse(jsonTokens);

		System.out.println("Node: " + node);
	}
	
//	private Object applyRules(Node node) {
//		if(node instanceof RuleNode) {
//			applyRuleNode((RuleNode) node);
//		} else if(node instanceof )
//	}
	
	public static void main(String[] args) {
		new EBNFTest().testJson();
	}
}
