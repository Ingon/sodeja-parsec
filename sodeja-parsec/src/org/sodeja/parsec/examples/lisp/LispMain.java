package org.sodeja.parsec.examples.lisp;

import java.io.StringReader;
import java.util.List;

import org.sodeja.parsec.examples.lisp.executor.LispExecutor;
import org.sodeja.parsec.examples.lisp.model.Script;

public class LispMain {
	public static void main(String[] args) {
//		String expression = "(+ 3 32) (- 3) (- 4 6 3)";
		String expression = "(def x 3) (def y 6) (+ x y)";
		LispLexer lexer = new LispLexer(new StringReader(expression));
		List<String> tokens = lexer.tokenize();
//		System.out.println(tokens);
		
		LispParser parser = new LispParser();
		Script script = parser.parse(tokens);
//		System.out.println(script);
		
		LispExecutor executor = new LispExecutor();
		executor.execute(script);
	}
}
