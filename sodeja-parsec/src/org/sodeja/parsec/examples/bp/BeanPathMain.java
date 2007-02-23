package org.sodeja.parsec.examples.bp;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sodeja.parsec.examples.bp.expression.BeanPath;

public class BeanPathMain {
	public static void main(String[] args) {
		String path = "a(d, e).b[1].c{iuhu}";
		
		BPLexer lexer = new BPLexer(new StringReader(path));
		List<String> tokens = lexer.tokenize();
		
		System.out.println("Tokens: " + tokens);
		
		BPParser parser = new BPParser();
		BeanPath bp = parser.parse(tokens);
		
		System.out.println("Expressions: " + bp);
		
//		System.out.println("Read: " + read(new One(), expressions));
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
