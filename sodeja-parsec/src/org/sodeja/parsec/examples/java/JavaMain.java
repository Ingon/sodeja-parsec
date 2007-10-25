package org.sodeja.parsec.examples.java;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.sodeja.functional.Function1;
import org.sodeja.functional.Predicate1;
import org.sodeja.generator.Generator;
import org.sodeja.generator.Generators;
import org.sodeja.parsec.examples.java.lexer.LexicalFunction;
import org.sodeja.parsec.examples.java.lexer.TerminalSymbolFunction;
import org.sodeja.parsec.examples.java.lexer.UnicodeResolverFunction;
import org.sodeja.parsec.examples.java.lexer.model.InputElement;
import org.sodeja.parsec.examples.java.lexer.model.TerminalSymbol;
import org.sodeja.parsec.examples.java.lexer.model.Token;

public class JavaMain {
	public static void main(String[] args) throws Exception {
		for(int i = 0;i < args.length;i++) {
			Generator<Token<?>> tokens = tokenize(args[i]);
//			System.out.println(Generators.readFully(tokens));
		}
	}
	
	private static Generator<Token<?>> tokenize(String file) throws IOException {
		return tokenize(new FileReader(file));
	}
	
	private static Generator<Token<?>> tokenize(Reader reader) throws IOException {
		Generator<InputElement> onlyTokens = Generators.filter(lexify(reader), new Predicate1<InputElement>() {
			@Override
			public Boolean execute(InputElement p) {
				return p instanceof Token<?>;
			}});
		
		return Generators.map(onlyTokens, new Function1<Token<?>, InputElement>() {
			@Override
			public Token<?> execute(InputElement p) {
				return (Token<?>) p;
			}});
	}
	
	private static Generator<InputElement> lexify(Reader reader) throws IOException {
		LexicalFunction functor = new LexicalFunction(symbolate(reader));
		return new Generator<InputElement>(functor);
	}
	
	private static Generator<TerminalSymbol> symbolate(Reader reader) throws IOException {
		TerminalSymbolFunction functor = new TerminalSymbolFunction(unicodize(reader));
		return new Generator<TerminalSymbol>(functor);
	}
	
	private static Generator<Character> unicodize(Reader reader) throws IOException {
		UnicodeResolverFunction functor = new UnicodeResolverFunction(Generators.readerGenerator(reader));
		return new Generator<Character>(functor);
	}
}
