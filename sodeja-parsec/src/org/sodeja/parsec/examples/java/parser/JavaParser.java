package org.sodeja.parsec.examples.java.parser;

import static org.sodeja.parsec.combinator.ParsecCombinators.alternative;
import static org.sodeja.parsec.combinator.ParsecCombinators.alternative1;
import static org.sodeja.parsec.combinator.ParsecCombinators.applyCons;
import static org.sodeja.parsec.combinator.ParsecCombinators.oneOf;
import static org.sodeja.parsec.combinator.ParsecCombinators.oneOrMoreSep;
import static org.sodeja.parsec.combinator.ParsecCombinators.thenParser3Cons2;
import static org.sodeja.parsec.combinator.ParsecCombinators.thenParser3Just2;
import static org.sodeja.parsec.combinator.ParsecCombinators.thenParser4Cons234;
import static org.sodeja.parsec.combinator.ParsecCombinators.thenParserCons;
import static org.sodeja.parsec.combinator.ParsecCombinators.thenParserJust1;
import static org.sodeja.parsec.combinator.ParsecCombinators.thenParserJust2;
import static org.sodeja.parsec.combinator.ParsecCombinators.zeroOrOne;
import static org.sodeja.parsec.combinator.ParsecCombinators.zeroOrOneBoolean;
import static org.sodeja.parsec.examples.java.parser.JavaParserUtils.keyword;
import static org.sodeja.parsec.examples.java.parser.JavaParserUtils.operator;
import static org.sodeja.parsec.examples.java.parser.JavaParserUtils.separator;

import java.util.List;

import org.sodeja.parsec.Parser;
import org.sodeja.parsec.combinator.DelegateParser;
import org.sodeja.parsec.combinator.ParsecCombinators;
import org.sodeja.parsec.examples.java.lexer.model.Keywords;
import org.sodeja.parsec.examples.java.lexer.model.Operators;
import org.sodeja.parsec.examples.java.lexer.model.Separators;
import org.sodeja.parsec.examples.java.lexer.model.Token;
import org.sodeja.parsec.examples.java.lexer.model.Token.Identifier;
import org.sodeja.parsec.examples.java.model.JCompoundType;
import org.sodeja.parsec.examples.java.model.JIdentifier;
import org.sodeja.parsec.examples.java.model.JImport;
import org.sodeja.parsec.examples.java.model.JPackage;
import org.sodeja.parsec.examples.java.model.JPrimitive;
import org.sodeja.parsec.examples.java.model.JQualifiedIdentifier;
import org.sodeja.parsec.examples.java.model.JSimpleCompoundType;
import org.sodeja.parsec.examples.java.model.JType;
import org.sodeja.parsec.examples.java.model.JTypeArgument;
import org.sodeja.parsec.examples.java.model.JTypeArgumentBound;
import org.sodeja.parsec.examples.java.model.JTypeArgumentBoundCont;
import org.sodeja.parsec.examples.java.model.JTypeParameter;
import org.sodeja.parsec.semantic.AbstractSemanticParser;

public class JavaParser extends AbstractSemanticParser<Token<?>, JImport> {
	
	public Parser<Token<?>, JIdentifier> IDENTIFIER = applyCons(
			"IDENTIFIER", new ClassParser("IDENTIFIER_DEL", Identifier.class), JIdentifier.class);
	
	private Parser<Token<?>, List<JIdentifier>> IDENTIFIERS = oneOrMoreSep(
			"IDENTIFIERS", IDENTIFIER, separator(Separators.DOT));
	
	public Parser<Token<?>, JQualifiedIdentifier> QUALIFIED_IDENTIFIER = applyCons(
			"QUALIFIED_IDENTIFIER", IDENTIFIERS, JQualifiedIdentifier.class);
	
	public Parser<Token<?>, JPackage> PACKAGE = thenParser3Cons2("PACKAGE", keyword(Keywords.PACKAGE), 
			QUALIFIED_IDENTIFIER, separator(Separators.SEMI_COLON), JPackage.class);
	
	private Parser<Token<?>, Boolean> STATIC_OPTIONAL = zeroOrOneBoolean("STATIC_OPTIONAL", keyword(Keywords.STATIC));
	
	private Parser<Token<?>, Boolean> IMPORT_ALL_OPTIONAL = zeroOrOneBoolean("IMPORT_TYPE_OPTIONAL", 
			thenParserJust1("IMPORT_ALL", separator(Separators.DOT), operator(Operators.S)));
			
	public Parser<Token<?>, JImport> IMPORT = thenParser4Cons234("IMPORT", keyword(Keywords.IMPORT), 
			STATIC_OPTIONAL, QUALIFIED_IDENTIFIER, IMPORT_ALL_OPTIONAL, JImport.class);
	
	private Parser<Token<?>, Token<?>> BASIC_TYPE_LIT = oneOf("BASIC_TYPE_LIT", keyword(Keywords.BYTE),
			keyword(Keywords.SHORT), keyword(Keywords.CHAR), keyword(Keywords.INT), keyword(Keywords.LONG),
			keyword(Keywords.FLOAT), keyword(Keywords.DOUBLE), keyword(Keywords.BOOLEAN));
	
	public Parser<Token<?>, JPrimitive> BASIC_TYPE = applyCons("BASIC_TYPE", BASIC_TYPE_LIT, JPrimitive.class);
	
	public DelegateParser<Token<?>, JCompoundType> COMPOUND_TYPE = new DelegateParser<Token<?>, JCompoundType>("COMPOUND_TYPE");
	
	private Parser<Token<?>, List<JCompoundType>> COMPOUND_TYPE_LIST = oneOrMoreSep("COMPOUND_TYPE_LIST", COMPOUND_TYPE, separator(Separators.COMMA));
	
	private Parser<Token<?>, JTypeArgumentBoundCont> COMPOUND_TYPE_ARGUMENT_BOUND_CONT = 
		thenParserCons("COMPOUND_TYPE_ARGUMENT_BOUND_CONT", 
				alternative("COMPOUND_TYPE_ARGUMENT_BOUND_CONT_ALT", keyword(Keywords.EXTENDS), keyword(Keywords.SUPER)), 
				COMPOUND_TYPE, JTypeArgumentBoundCont.class);
	
	private Parser<Token<?>, JTypeArgumentBound> COMPOUND_TYPE_ARGUMENT_BOUND = 
		thenParserCons("COMPOUND_TYPE_ARGUMENT_BOUND", operator(Operators.Q), 
			zeroOrOne("COMPOUND_TYPE_ARGUMENT_BOUND_ZO", COMPOUND_TYPE_ARGUMENT_BOUND_CONT), 
			JTypeArgumentBound.class);
	
	private Parser<Token<?>, JTypeArgument> COMPOUND_TYPE_ARGUMENT = 
		alternative1("COMPOUND_TYPE_ARGUMENT", COMPOUND_TYPE, COMPOUND_TYPE_ARGUMENT_BOUND);
	
	private Parser<Token<?>, List<JTypeArgument>> COMPOUND_TYPE_ARGUMENTS_LIST =
		oneOrMoreSep("COMPOUND_TYPE_ARGUMENTS_LIST", COMPOUND_TYPE_ARGUMENT, separator(Separators.COMMA));
	
	private Parser<Token<?>, List<JTypeArgument>> COMPOUND_TYPE_ARGUMENTS =
		thenParser3Just2("COMPOUND_TYPE_ARGUMENTS", operator(Operators.L), 
				COMPOUND_TYPE_ARGUMENTS_LIST, operator(Operators.G));
	
	private Parser<Token<?>, JSimpleCompoundType> SIMPLE_COMPOUND_TYPE = 
		thenParserCons("SIMPLE_COMPOUND_TYPE", IDENTIFIER, 
				zeroOrOne("COMPOUND_TYPE_ARGUMENTS_OPT", COMPOUND_TYPE_ARGUMENTS), 
				JSimpleCompoundType.class);

	private Parser<Token<?>, List<JSimpleCompoundType>> SIMPLE_COMPOUND_TYPES = 
		oneOrMoreSep("COMPOUND_TYPE_DEL", SIMPLE_COMPOUND_TYPE, separator(Separators.DOT));
	
	private Parser<Token<?>, Boolean> OPTIONAL_ARRAY = zeroOrOneBoolean("OPTIONAL_ARRAY", 
			thenParserJust1("OPTIONAL_ARRAY_MARK", separator(Separators.OPEN_RECT), separator(Separators.CLOSE_RECT)));
	
	private Parser<Token<?>, List<JCompoundType>> BOUND = oneOrMoreSep("BOUND", COMPOUND_TYPE, operator(Operators.A));
	
	private Parser<Token<?>, JTypeParameter> TYPE_PARAMETER = 
		thenParserCons("TYPE_PARAMETER", IDENTIFIER, 
				zeroOrOne("BOUND_OPT", thenParserJust2("BOUND_OPT_THEN", keyword(Keywords.EXTENDS), BOUND)), 
				JTypeParameter.class);
	
	private Parser<Token<?>, List<JTypeParameter>> TYPE_PARAMETERS =
		thenParser3Just2("TYPE_PARAMETERS", operator(Operators.L), 
				oneOrMoreSep("TYPE_PARAMETERS_LIST", TYPE_PARAMETER, separator(Separators.COMMA)), 
				operator(Operators.G));
	
	public JavaParser() {
		COMPOUND_TYPE.delegate = thenParserCons("COMPOUND_TYPE_DELEGATE", 
				SIMPLE_COMPOUND_TYPES, OPTIONAL_ARRAY, JCompoundType.class);
	}
	
	@Override
	protected Parser<Token<?>, JImport> getParser() {
		return IMPORT;
	}
}
