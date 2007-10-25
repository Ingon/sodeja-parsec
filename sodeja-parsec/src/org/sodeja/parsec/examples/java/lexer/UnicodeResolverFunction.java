package org.sodeja.parsec.examples.java.lexer;

import static org.sodeja.functional.Maybe.just;
import static org.sodeja.functional.Maybe.nothing;

import java.util.Deque;
import java.util.LinkedList;

import org.sodeja.functional.Maybe;
import org.sodeja.generator.Generator;
import org.sodeja.generator.GeneratorFunction;
import org.sodeja.lang.CharacterUtils;

public class UnicodeResolverFunction implements GeneratorFunction<Character> {
	
	private Generator<Character> input;
	private Deque<Character> buffer;
	
	public UnicodeResolverFunction(Generator<Character> input) {
		this.input = input;
		this.buffer = new LinkedList<Character>();
	}

	@Override
	public Maybe<Character> execute() {
		if(! buffer.isEmpty()) {
			return just(buffer.pollFirst());
		}
		
		if(input == null) {
			return nothing();
		}
		
		Character backslash = input.head();
		if(backslash != '\\') {
			input = input.tail();
			return just(backslash);
		}
		
		Generator<Character> ugen = input.tail();
		Character u = ugen.head();
		if(u != 'u') {
			buffer.offerLast(u);
			input = ugen.tail();
			return just(backslash);
		}
		
		while(u == 'u') {
			ugen = ugen.tail();
			u = ugen.head();
		}
		
		Character[] digits = readDigits(ugen, u);
		
		return just(CharacterUtils.convertToUnicode(digits));
	}
	
	private Character[] readDigits(final Generator<Character> ugen, final Character u) {
		Generator<Character> digitsGen = ugen;
		Character digit = u;
		Character[] digits = new Character[4];
		for(int i = 0;i < digits.length;i++) {
			if(! CharacterUtils.isHexadecimal(digit)) {
				throw new RuntimeException("Expects a hex character at this position");
			}
			
			digits[i] = digit;
			digitsGen = digitsGen.tail();
			if(digitsGen == null) {
				digit = null;
			} else {
				digit = digitsGen.head();
			}
		}
		
		input = digitsGen;
		
		return digits;
	}
}
