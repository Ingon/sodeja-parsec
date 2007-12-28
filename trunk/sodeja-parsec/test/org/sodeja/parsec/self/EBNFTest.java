package org.sodeja.parsec.self;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.sodeja.collections.ConsList;
import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Function1;
import org.sodeja.functional.Predicate1;
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
		
//		String jsonTest = "{" +
//			"'class': 'com.icw.phr.fedex.FedexCall', " +
//			"'serviceName': 'service1', " +
//			"'methodName': 'method1'}"; 
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
		
		Object obj = applyRules(node);
		System.out.println("OBJ: " + obj);
	}
	
	private Object applyRules(Node node) {
		if(node instanceof RuleNode) {
			return applyRuleNode((RuleNode) node);
		} else if(node instanceof IntegerNode) {
			return ((IntegerNode) node).value;
		} else if(node instanceof LiteralNode) {
			return ((LiteralNode) node).name;
		} else if(node instanceof IdentifierNode) {
			return ((IdentifierNode) node).name;
		}
		
		throw new IllegalArgumentException("Unsupported node: " + node);
	}
	
	private Object applyRuleNode(final RuleNode rule) {
		List<Object> internals = ListUtils.map(rule.nodes, new Function1<Object, Object>() {
			@Override
			public Object execute(Object p) {
				if(p instanceof Node) {
					return applyRules((Node) p);
				}
				
				throw new IllegalArgumentException("Unsupported object: " + p);
			}});
		
		if(rule.name.equals("object")) {
			if(internals.size() == 2) {
				return new JSONObject(new ArrayList<JSONField>());
			}
			Object internal = internals.get(1);
			if(internal instanceof JSONField) {
				return new JSONObject(ListUtils.asList((JSONField) internal));
			}
			return new JSONObject((List<JSONField>) internal);
		} else if(rule.name.equals("pair")) {
			return new JSONField((String) internals.get(0), internals.get(2));
		} else if(rule.name.equals("members")) {
			if(internals.get(2) instanceof ConsList) {
				return new ConsList<JSONField>((JSONField) internals.get(0), ((ConsList<JSONField>) internals.get(2)));
			}
			return new ConsList<JSONField>((JSONField) internals.get(0), new ConsList<JSONField>((JSONField) internals.get(2)));
		} else if(rule.name.equals("elements")) {
			if(internals.size() == 2) {
				return new ArrayList();
			}
			
			if(internals.get(2) instanceof ConsList) {
				return new ConsList(internals.get(0), (ConsList) internals.get(2));
			}
			return new ConsList(internals.get(0), new ConsList(internals.get(2)));
		} else if(rule.name.equals("array")) {
			if(internals.size() == 2) {
				return new Object[0];
			}
			return internals.get(1);
		}
		
		throw new IllegalArgumentException("Unsupported rule: " + rule.name);
	}

	public static void main(String[] args) {
		new EBNFTest().testJson();
	}
}
