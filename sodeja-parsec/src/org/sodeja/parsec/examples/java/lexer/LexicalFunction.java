package org.sodeja.parsec.examples.java.lexer;

import static org.sodeja.functional.Maybe.just;
import static org.sodeja.parsec.examples.java.lexer.model.InputElement.comment;
import static org.sodeja.parsec.examples.java.lexer.model.InputElement.space;
import static org.sodeja.parsec.examples.java.lexer.model.Literal.booleanLiteral;
import static org.sodeja.parsec.examples.java.lexer.model.Literal.nullLiteral;
import static org.sodeja.parsec.examples.java.lexer.model.Token.identifier;
import static org.sodeja.parsec.examples.java.lexer.model.Token.keyword;
import static org.sodeja.parsec.examples.java.lexer.model.Token.operator;
import static org.sodeja.parsec.examples.java.lexer.model.Token.separator;

import java.util.List;

import org.sodeja.functional.Maybe;
import org.sodeja.generator.Generator;
import org.sodeja.generator.GeneratorFunction;
import org.sodeja.parsec.examples.java.lexer.model.BooleanLiterals;
import org.sodeja.parsec.examples.java.lexer.model.InputElement;
import org.sodeja.parsec.examples.java.lexer.model.Keywords;
import org.sodeja.parsec.examples.java.lexer.model.Literal;
import org.sodeja.parsec.examples.java.lexer.model.Operators;
import org.sodeja.parsec.examples.java.lexer.model.Separators;
import org.sodeja.parsec.examples.java.lexer.model.TerminalSymbol;

public class LexicalFunction implements GeneratorFunction<InputElement> {
	
	private Generator<TerminalSymbol> input;
	
	public LexicalFunction(Generator<TerminalSymbol> input) {
		this.input = input;
	}
	
	@Override
	public Maybe<InputElement> execute() {
		if(input == null) {
			return Maybe.nothing();
		}
		
		TerminalSymbol value = input.head();
		if(isWhitespace(value)) {
			readTillNonWhiteSpace();
			return just(space());
		}
		
		if(isCommentStart(value)) {
			Generator<TerminalSymbol> next = input.tail();
			if(isSingleComment(next.head())) {
				String text = readSingleComment();
				return just(comment(text));
			}
			
			if(isMultyComment(next.head())) {
				String text = readMultyComment();
				return just(comment(text));
			}
		}
		
		Character ch = value.value();
		if(Character.isJavaIdentifierStart(ch)) {
			return readIdentifier();
		}
		
		if(Character.isDigit(ch)) {
			return readNumber();
		}
		
		Separators sep = Separators.valueOf(ch);
		if(sep != null) {
			input = input.tail();
			return just(separator(sep));
		}
		
		return readOperator();
	}

	private Maybe<InputElement> readIdentifier() {
		String val = readIdentifierText();
		
		Keywords keyword = Keywords.find(val);
		if(keyword != null) {
			return just(keyword(keyword));
		}
		
		BooleanLiterals bool = BooleanLiterals.find(val);
		if(bool != null) {
			return just(booleanLiteral(bool));
		}
		
		// TODO not very good. Clearly some other form is needed
		if("null".equals(val)) {
			return just(nullLiteral());
		}
		
		return just(identifier(val));
	}

	private Maybe<InputElement> readNumber() {
		StringBuilder sb = new StringBuilder();
		
		while(input != null && (! input.head().isLineTerminator()) && (Character.isDigit(input.head().value()))) {
			sb.append(input.head().value());
			input = input.tail();
		}
		
		return Maybe.just(Literal.integer(sb.toString()));
	}
	
	// TODO so ugly
	private Maybe<InputElement> readOperator() {
		Generator<TerminalSymbol> singleGen = input;
		
		String single = String.valueOf(singleGen.head().value());
		List<Operators> singleOps = Operators.filter(single);
		if(singleOps.size() == 0) {
			throw new IllegalArgumentException();
		}
		
		Generator<TerminalSymbol> tupleGen = input.tail();
		if(tupleGen.head().isLineTerminator()) {
			input = tupleGen;
			return just(operator(Operators.valueOfString(single)));
		}
		String tuple = single + String.valueOf(tupleGen.head().value());
		List<Operators> tupleOps = Operators.filter(tuple);
		if(tupleOps.size() == 0) {
			input = tupleGen;
			return just(operator(Operators.valueOfString(single)));
		}
		
		Generator<TerminalSymbol> tripleGen = tupleGen.tail();
		if(tripleGen.head().isLineTerminator()) {
			input = tripleGen;
			return just(operator(Operators.valueOfString(tuple)));
		}
		String triple = tuple + String.valueOf(tripleGen.head().value());
		List<Operators> tripleOps = Operators.filter(triple);
		if(tripleOps.size() == 0) {
			input = tripleGen;
			return just(operator(Operators.valueOfString(tuple)));
		}
		
		Generator<TerminalSymbol> quadGen = tupleGen.tail();
		if(quadGen.head().isLineTerminator()) {
			input = quadGen;
			return just(operator(Operators.valueOfString(triple)));
		}
		String quad = tuple + String.valueOf(quadGen.head().value());
		List<Operators> quadOps = Operators.filter(quad);
		if(quadOps.size() == 0) {
			input = quadGen;
			return just(operator(Operators.valueOfString(triple)));
		}
		
		if(quadOps.size() > 1) {
			throw new IllegalArgumentException();
		}
		
		return just(operator(Operators.valueOfString(quad)));
	}

	private String readIdentifierText() {
		StringBuilder sb = new StringBuilder();
		
		while(input != null && (! input.head().isLineTerminator()) && (Character.isJavaIdentifierPart(input.head().value()))) {
			sb.append(input.head().value());
			input = input.tail();
		}
		
		return sb.toString();
	}

	private void readTillNonWhiteSpace() {
		input = input.tail();
		while(input != null && isWhitespace(input.head())) {
			input = input.tail();
		}
	}

	private String readSingleComment() {
		StringBuilder sb = new StringBuilder();
		
		while(! input.head().isLineTerminator()) {
			sb.append(input.head().value());
			input = input.tail();
		}
		
		return sb.toString();
	}

	private String readMultyComment() {
		throw new UnsupportedOperationException();
	}

	
	private boolean isWhitespace(TerminalSymbol ts) {
		return ts.isLineTerminator() || Character.isWhitespace(ts.value());
	}
	
	private boolean isCommentStart(TerminalSymbol ts) {
		return (! ts.isLineTerminator()) && ts.value() == '/';
	}

	private boolean isSingleComment(TerminalSymbol ts) {
		return isCommentStart(ts);
	}
	
	private boolean isMultyComment(TerminalSymbol ts) {
		return (! ts.isLineTerminator()) && ts.value() == '*';
	}
}
