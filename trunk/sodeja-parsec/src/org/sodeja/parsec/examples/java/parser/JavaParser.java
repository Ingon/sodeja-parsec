package org.sodeja.parsec.examples.java.parser;

import static org.sodeja.parsec.combinator.ParsecCombinators.applyCons;
import static org.sodeja.parsec.combinator.ParsecCombinators.oneOf;
import static org.sodeja.parsec.combinator.ParsecCombinators.oneOrMoreSep;
import static org.sodeja.parsec.combinator.ParsecCombinators.thenParser3Cons2;
import static org.sodeja.parsec.combinator.ParsecCombinators.thenParser4Cons234;
import static org.sodeja.parsec.combinator.ParsecCombinators.thenParserJust1;
import static org.sodeja.parsec.combinator.ParsecCombinators.zeroOrOneBoolean;
import static org.sodeja.parsec.examples.java.parser.JavaParserUtils.keyword;
import static org.sodeja.parsec.examples.java.parser.JavaParserUtils.operator;
import static org.sodeja.parsec.examples.java.parser.JavaParserUtils.separator;

import java.util.List;

import org.sodeja.parsec.Parser;
import org.sodeja.parsec.combinator.ParsecCombinators;
import org.sodeja.parsec.examples.java.lexer.model.Keywords;
import org.sodeja.parsec.examples.java.lexer.model.Operators;
import org.sodeja.parsec.examples.java.lexer.model.Separators;
import org.sodeja.parsec.examples.java.lexer.model.Token;
import org.sodeja.parsec.examples.java.lexer.model.Token.Identifier;
import org.sodeja.parsec.examples.java.model.JIdentifier;
import org.sodeja.parsec.examples.java.model.JImport;
import org.sodeja.parsec.examples.java.model.JPackage;
import org.sodeja.parsec.examples.java.model.JPrimitive;
import org.sodeja.parsec.examples.java.model.JQualifiedIdentifier;
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
	
	@Override
	protected Parser<Token<?>, JImport> getParser() {
		return IMPORT;
	}
}
