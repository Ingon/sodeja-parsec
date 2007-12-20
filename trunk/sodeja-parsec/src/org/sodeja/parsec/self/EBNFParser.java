package org.sodeja.parsec.self;

import static org.sodeja.parsec.combinator.ParsecCombinators.*;
import static org.sodeja.parsec.standart.StandartParsers.*;

import java.util.List;

import org.sodeja.parsec.Parser;
import org.sodeja.parsec.combinator.DelegateParser;
import org.sodeja.parsec.semantic.AbstractSemanticParser;

public class EBNFParser extends AbstractSemanticParser<String, Syntax> {

	private Parser<String, Integer> INTEGER = simpleIntegerParser("INTEGER");
	
	private DelegateParser<String, List<Definition>> DEFINITIONS_LIST = new DelegateParser<String, List<Definition>>("DEFINITIONS_LIST");
	
	private Parser<String, Optional> OPTIONAL = thenParser3Cons2("OPTIONAL", literal("["), DEFINITIONS_LIST, literal("]"), Optional.class);
	
	private Parser<String, Repeated> REPEATED = thenParser3Cons2("REPEATED", literal("{"), DEFINITIONS_LIST, literal("}"), Repeated.class);

	private Parser<String, Grouped> GROUPED = thenParser3Cons2("REPEATED", literal("("), DEFINITIONS_LIST, literal(")"), Grouped.class);
	
	private Parser<String, MetaIdentifier> META_IDENTIFIER = applyCons("META_IDENTIFIER", alphaDigitsUnderscore("META_VALUE"), MetaIdentifier.class);
	
	private Parser<String, TerminalString> TERMINAL_STRING = applyCons("TERMINAL_STRING", justString("TERMINAL_STRING_INT"), TerminalString.class);
	
	private Parser<String, SpecialSequence> SPECIAL_SEQUENCE = applyCons("SPECIAL_SEQUENCE", justStartEnd("SPECIAL_SEQUENCE_INT", "?"), SpecialSequence.class);
	
	private Parser<String, Primary> PRIMARY = oneOf1("PRIMARY", OPTIONAL, REPEATED, GROUPED, META_IDENTIFIER, TERMINAL_STRING, SPECIAL_SEQUENCE);
	
	private Parser<String, Integer> FACTOR_INT = thenParserJust1("FACTOR_INT", INTEGER, literal("*"));
	
	private Parser<String, Integer> FACTOR_INT_OPTIONAL = zeroOrOne("FACTOR_INT_OPTIONAL", FACTOR_INT);
	
	private Parser<String, Integer> FACTOR_REPEAT = applyJust("FACTOR_REPEAT", FACTOR_INT_OPTIONAL);
	
	private Parser<String, Factor> FACTOR = thenParserCons("FACTOR", FACTOR_REPEAT, PRIMARY, Factor.class);
	
	private Parser<String, Factor> EXCEPTION_PART = FACTOR;
	
	private Parser<String, Factor> EXCEPTION = thenParserJust2("EXCEPTION_INT", literal("-"), EXCEPTION_PART);

	private Parser<String, Factor> EXCEPTION_OPTIONAL = zeroOrOne("EXCEPTION_OPTIONAL", EXCEPTION);
	
	private Parser<String, Term> TERM = thenParserCons("TERM", FACTOR, EXCEPTION_OPTIONAL, Term.class);
	
	private Parser<String, List<Term>> TERMS = oneOrMoreSep("TERMS", TERM, literal(","));
	
	private Parser<String, Definition> DEFINITION = applyCons("DEFINITION", TERMS, Definition.class);
	
	private Parser<String, List<Definition>> DEFINITIONS = oneOrMoreSep("DEFINITIONS", DEFINITION, literal("|"));
	
	private Parser<String, SyntaxRule> SYNTAX_RULE = thenParser3Cons13("SYNTAX_RULE", META_IDENTIFIER, literal("="), DEFINITIONS, SyntaxRule.class);
	
	private Parser<String, List<SyntaxRule>> SYNTAX_RULES = oneOrMore("SYNTAX_RULES", SYNTAX_RULE);
	
	private Parser<String, Syntax> SYNTAX = applyCons("SYNTAX", SYNTAX_RULES, Syntax.class);
	
	public EBNFParser() {
		DEFINITIONS_LIST.delegate = DEFINITIONS;
	}

	@Override
	protected Parser<String, Syntax> getParser() {
		return SYNTAX;
	}
}
