package org.sodeja.parsec.examples.bp;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sodeja.parsec.examples.bp.expression.Expression;

public class BeanPath {
	public static void main(String[] args) {
		String path = "a.b[1].c{iuhu}";
		
		BPLexer lexer = new BPLexer(new StringReader(path));
		List<String> tokens = lexer.tokenize();
		
		System.out.println("Tokens: " + tokens);
		
		BPParser parser = new BPParser();
		List<Expression> expressions = parser.parse(tokens);
		
		System.out.println("Expressions: " + expressions);
		
		System.out.println("Read: " + read(new One(), expressions));
	}
	
	public static Object read(Object root, List<Expression> expressions) {
		Object temp = root;
		for(Expression expression : expressions) {
			temp = expression.read(temp);
		}
		return temp;
	}
	
	private static class One {
		private Object a = new Two();
	}
	
	private static class Two {
		private List b = new ArrayList();
		private Two() {
			b.add(new Three());
			b.add(new Three());
		}
	}
	
	private static class Three {
		private Map c = new HashMap();
		private Three() {
			c.put("iuhu", "BIGDATA");
		}
	}
}
