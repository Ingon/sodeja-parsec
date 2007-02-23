package org.sodeja.parsec.examples.bp;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sodeja.parsec.examples.bp.expression.BeanPath;

public class BeanPathMain {
	public static void main(String[] args) {
		String path = "a.a(d, e).b[1].c{iuhu}";
		
		BPLexer lexer = new BPLexer(new StringReader(path));
		List<String> tokens = lexer.tokenize();
		
		System.out.println("Tokens: " + tokens);
		
		BPParser parser = new BPParser();
		BeanPath bp = parser.parse(tokens);
		
		System.out.println("Expressions: " + bp);
		
		Map<String, Object> ctx = new HashMap<String, Object>();
		ctx.put("a", new One());
		ctx.put("d", "DDDDDDDDDDDDDD");
		ctx.put("e", "EEEEEEEEEEEEEE");
		
		System.out.println("Result: " + bp.read(ctx));
	}
	
	private static class One {
		private Object a = new Two();
		
		private Object a(String a1, String a2) {
			System.out.println("T1:" + a1);
			System.out.println("T2:" + a2);
			return a;
		}
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
