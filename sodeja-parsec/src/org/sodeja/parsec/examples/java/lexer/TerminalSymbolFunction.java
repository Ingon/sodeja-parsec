package org.sodeja.parsec.examples.java.lexer;

import static org.sodeja.functional.Maybe.just;
import static org.sodeja.functional.Maybe.nothing;
import static org.sodeja.parsec.examples.java.lexer.model.TerminalSymbol.input;
import static org.sodeja.parsec.examples.java.lexer.model.TerminalSymbol.line;

import org.sodeja.functional.Maybe;
import org.sodeja.generator.Generator;
import org.sodeja.generator.GeneratorFunction;
import org.sodeja.parsec.examples.java.lexer.model.TerminalSymbol;

public class TerminalSymbolFunction implements GeneratorFunction<TerminalSymbol> {

	private Generator<Character> input;
	
	public TerminalSymbolFunction(Generator<Character> input) {
		this.input = input;
	}
	
	@Override
	public Maybe<TerminalSymbol> execute() {
		if(input == null) {
			return nothing();
		}
		
		Character ch = input.head();
		if(ch == '\r') {
			Generator<Character> temp = input.tail();
			if(temp == null) {
				input = null;
				return just(line());
			}
			
			ch = temp.head();
			if(ch == '\n') {
				input = temp.tail();
				return just(line());
			}
			
			input = temp;
			return just(line());
		}
		
		input = input.tail();
		
		if(ch == '\n') {
			return just(line());
		}
		
		return just(input(ch));
	}
}
