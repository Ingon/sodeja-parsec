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
				"(def mean-square (\\ (x y) (average (square x) (square y)))) " +
				"(def abs (\\ (x) (if (< x 0) (- x) x))) ";
//		String exp = "3 (+ 3 4 8) (+ 3 (* 5 6) 8 2)";
//		String exp = "(+ (* 3 5) (* 47 (- 20 6)) 12)";
//		String exp = "(def a (* 5 5)) " +
//				"(* a a) " +
//				"(def b (+ a (* 5 a))) " +
//				"b " +
//				"(+ a (/ b 5))";
//		String exp = lib +
//				"(square 10) " +
//				"(square 1001) " +
//				"(square (+ 5 7)) " +
//				"(+ (square 3) (square 4)) " +
//				"(square (square (square 1001))) " +
//				"square " +
//				"+";
//		String exp = lib + 
//				"(def abs (\\ (x) (cond " +
//					"((< x 0) (- x)) " +
//					"((= x 0) 0) " +
//					"((> x 0) x)))) " +
//				"(def abs (\\ (x) (if (< x 0) (- x) x))) " +
//				"(abs (- 3))";
		String exp = lib +
			"(def improve (\\ (guess x) (average guess (/ x guess)))) " + 
			"(def good-enough? (\\ (guess x) (< (abs (- (square guess))) 0.001))) " +
			"(def try (\\ (guess x) (if (good-enough? guess x) guess (try (improve guess x) x)))) " + 
			"(def sqrt (\\ (x) (try 1 x))) " +
			"(sqrt 2)";
		LispLexer lexer = new LispLexer(new StringReader(exp));
		List<String> tokens = lexer.tokenize();
		System.out.println(tokens);
		
		LispParser parser = new LispParser();
		Script script = parser.parse(tokens);
//		System.out.println(script);
		
		LispExecutor executor = new LispExecutor();
		executor.execute(script);
	}
}
