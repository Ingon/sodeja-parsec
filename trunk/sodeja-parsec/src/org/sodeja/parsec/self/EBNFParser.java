package org.sodeja.parsec.self;

import static org.sodeja.parsec.combinator.ParsecCombinators.*;
import static org.sodeja.parsec.standart.StandartParsers.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Function1;
import org.sodeja.functional.FunctionN;
import org.sodeja.parsec.Parser;
import org.sodeja.parsec.combinator.DelegateParser;
import org.sodeja.parsec.combinator.ThenParserN;
import org.sodeja.parsec.self.model.Definition;
import org.sodeja.parsec.self.model.Factor;
import org.sodeja.parsec.self.model.Grouped;
import org.sodeja.parsec.self.model.MetaIdentifier;
import org.sodeja.parsec.self.model.Optional;
import org.sodeja.parsec.self.model.Primary;
import org.sodeja.parsec.self.model.Repeated;
import org.sodeja.parsec.self.model.SpecialSequence;
import org.sodeja.parsec.self.model.Syntax;
import org.sodeja.parsec.self.model.SyntaxRule;
import org.sodeja.parsec.self.model.Term;
import org.sodeja.parsec.self.model.TerminalString;
import org.sodeja.parsec.semantic.AbstractSemanticParser;

public class EBNFParser extends AbstractSemanticParser<String, Node> {

	private final String mainRuleName;
	private final Map<String, DelegateParser<String, Node>> rules;
	
	public EBNFParser(Syntax syntax) {
		mainRuleName = syntax.rules.get(0).metaIdentifier.value;
		rules = new HashMap<String, DelegateParser<String,Node>>();
		
		processRules(syntax);
	}

	private void processRules(Syntax syntax) {
		createDelegates(syntax);
		createParsers(syntax);
	}

	private void createDelegates(Syntax syntax) {
		for(SyntaxRule rule : syntax.rules) {
			rules.put(rule.metaIdentifier.value, new DelegateParser<String, Node>(rule.metaIdentifier.value + "_DELEGATE"));
		}
	}
	
	private void createParsers(Syntax syntax) {
		for(SyntaxRule rule : syntax.rules) {
			DelegateParser<String, Node> delegate = rules.get(rule.metaIdentifier.value);
			delegate.delegate = createParser(rule);
		}
	}

	private Parser<String, Node> createParser(SyntaxRule rule) {
		return createParser(rule.metaIdentifier.value, rule.definitions);
	}

	private Parser<String, Node> createParser(final String name, List<Definition> definitions) {
		List<Parser<String, Node>> parsers = ListUtils.map(definitions, new Function1<Parser<String, Node>, Definition>() {
			@Override
			public Parser<String, Node> execute(Definition p) {
				return createParser(name, p);
			}});
		
		if(parsers.size() == 1) {
			return parsers.get(0);
		}
		
		return oneOf(name, parsers.toArray(new Parser[parsers.size()]));
	}
	
	private Parser<String, Node> createParser(final String name, Definition definition) {
		List<Parser<String, Node>> parsers = ListUtils.map(definition.terms, new Function1<Parser<String, Node>, Term>() {
			@Override
			public Parser<String, Node> execute(Term p) {
				return createParser(p);
			}});
		
		if(parsers.size() == 1) {
			return parsers.get(0);
		}
		
		return new ThenParserN<String, Node>(name + "_DEFINITION", new FunctionN<Node, Object>() {
			@Override
			public Node execute(Object... obj) {
				return new RuleNode(name, ListUtils.asList(obj));
			}}, parsers.toArray(new Parser[parsers.size()]));
	}
	
	private Parser<String, Node> createParser(Term term) {
		if(term.exception != null) {
			throw new UnsupportedOperationException("Exception factor not supported");
		}
		
		return createParser(term.factor);
	}

	private Parser<String, Node> createParser(Factor factor) {
		if(factor.repeat != null) {
			throw new UnsupportedOperationException("Repeat factor not supported");
		}
		
		Primary primary = factor.primary;
		if(primary instanceof Grouped) {
		} else if(primary instanceof MetaIdentifier) {
			return createParser((MetaIdentifier) primary);
		} else if(primary instanceof Optional) {
			return createParser((Optional) primary);
		} else if(primary instanceof Repeated) {
			return createParser((Repeated) primary);
		} else if(primary instanceof SpecialSequence) {
			return createParser((SpecialSequence) primary);
		} else if(primary instanceof TerminalString) {
			return createParser((TerminalString) primary);
		}
		
		throw new IllegalArgumentException("Unknown primary: " + primary);
	}

	private Parser createParser(MetaIdentifier identifier) {
		return rules.get(identifier.value);
	}
	
	private Parser createParser(Optional optional) {
		return zeroOrOne("OPTIONAL", createParser("OPTIONA_DEFINITIONS", optional.definitions));
	}

	private Parser createParser(Repeated repeated) {
		return zeroOrMore("REPEATED", createParser("REPEATED_DEFINITIONS", repeated.definitions));
	}

	private Parser createParser(SpecialSequence ss) {
		if(ss.value.equals("string")) {
			return applyCons("IDENTIFIER", justString("IDENTIFIER_DELEGATE"), IdentifierNode.class);
		} else if(ss.value.equals("integer")) {
			return applyCons("INTEGER", simpleIntegerParser("INTEGER_DELEGATE"), IntegerNode.class);
		}
		
		throw new IllegalArgumentException("Unrecognized special: " + ss.value);
	}
		private Parser createParser(TerminalString ts) {
		return applyCons("TERMINAL_STRING", literal(ts.value), LiteralNode.class);
	}
	
	@Override
	protected Parser<String, Node> getParser() {
		return rules.get(mainRuleName);
	}
}
