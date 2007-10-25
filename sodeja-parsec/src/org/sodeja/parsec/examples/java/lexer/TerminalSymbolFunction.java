package org.sodeja.parsec.examples.java.lexer;

import org.sodeja.functional.Maybe;
import static org.sodeja.functional.Maybe.*;
import org.sodeja.generator.Generator;
import org.sodeja.generator.GeneratorFunction;
import org.sodeja.parsec.examples.java.lexer.model.TerminalSymbol;
import static org.sodeja.parsec.examples.java.lexer.model.TerminalSymbol.*;

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
		
		Character ch = input.value();
		if(ch == '\r') {
			Generator<Character> temp = input.next();
			if(temp == null) {
				input = null;
				return just(line());
			}
			
			ch = temp.value();
			if(ch == '\n') {
				input = temp.next();
				return just(line());
			}
			
			input = temp;
			return just(line());
		}
		
		input = input.next();
		
		if(ch == '\n') {
			return just(line());
		}
		
		return just(input(ch));
	}
}
