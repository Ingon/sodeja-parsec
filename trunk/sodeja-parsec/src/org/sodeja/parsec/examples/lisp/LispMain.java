package org.sodeja.parsec.examples.lisp;

import java.io.StringReader;
import java.util.List;

import org.sodeja.parsec.examples.lisp.executor.LispExecutor;
import org.sodeja.parsec.examples.lisp.model.Script;

public class LispMain {
	public static void main(String[] args) {
		String lib = 
				"(def square (\\ (x) (* x x))) " +
				"(def average (\\ (x y) (/ x y))) " +
				"(def mean-square (\\ (x y) (average (square x) (square y)))) ";
//		String expression = "(() ())";
//		String expression = "(+ 1 2)";
//		String expression = "(+ 3 32) (- 3) (- 4 6 3)";
//		String expression = "(+ 3 32 (- 3))";
//		String expression = "(def x 3) (def y 6) (+ x y)";
//		String expression = "(def twice (\\ (x) (+ x x))) \r\n (twice 3)";
//		String exp = "3 (+ 3 4 8) (+ 3 (* 5 6) 8 2)";
//		String exp = "(+ (* 3 5) (* 47 (- 20 6)) 12)";
//		String exp = "(def a (* 5 5)) " +
//				"(* a a) " +
//				"(def b (+ a (* 5 a))) " +
//				"b " +
//				"(+ a (/ b 5))";
		String exp = lib +
//				"(square 10) " +
//				"(square 1001) " +
//				"(square (+ 5 7)) " +
//				"(+ (square 3) (square 4)) " +
//				"(square (square (square 1001))) " +
				"square " +
				"+";
		LispLexer lexer = new LispLexer(new StringReader(exp));
		List<String> tokens = lexer.tokenize();
//		System.out.println(tokens);
		
		LispParser parser = new LispParser();
		Script script = parser.parse(tokens);
//		System.out.println(script);
		
		LispExecutor executor = new LispExecutor();
		executor.execute(script);
	}
}
