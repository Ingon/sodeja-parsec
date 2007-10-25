package org.sodeja.parsec.examples.java;

import org.sodeja.generator.Generator;
import org.sodeja.generator.Generators;
import org.sodeja.parsec.examples.java.lexer.model.Token;

public class JavaMain {
	public static void main(String[] args) throws Exception {
		for(int i = 0;i < args.length;i++) {
			long start = System.currentTimeMillis();
			Generator<Token<?>> tokens = JavaLexer.tokenizeFile(args[i]);
//			System.out.println(Generators.readFully(tokens));
			Generators.readFully(tokens);
			long end = System.currentTimeMillis();
			System.out.println("Time: " + (end - start));
		}
	}	
}
