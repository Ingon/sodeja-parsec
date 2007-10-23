package org.sodeja.parsec.examples.bp;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sodeja.parsec.examples.bp.model.BeanPath;

public class BeanPathMain {
	public static void main(String[] args) {
//		BPParser test = new BPParser();
//		
//		BeanPath path = test.parse(ListUtils.asList("a"));
//		System.out.println(path);
//		
//		path = test.parse(ListUtils.asList("a", "[", "1", "]"));
//		System.out.println(path);
//		
//		path = test.parse(ListUtils.asList("a", "{", "dsa", "}"));
//		System.out.println(path);
//		
//		path = test.parse(ListUtils.asList("a", "[", "1", "]", "{", "dsa", "}"));
//		System.out.println(path);
//
//		path = test.parse(ListUtils.asList("a", "{", "dsa", "}", "[", "1", "]"));
//		System.out.println(path);
//		
//		path = test.parse(ListUtils.asList("a", ".", "b"));
//		System.out.println(path);
//
//		path = test.parse(ListUtils.asList("a", ".", "b", "[", "1", "]"));
//		System.out.println(path);
//
//		path = test.parse(ListUtils.asList("a", ".", "b", "{", "dsa", "}"));
//		System.out.println(path);
//
//		path = test.parse(ListUtils.asList("a", ".", "b", "[", "1", "]", "{", "dsa", "}"));
//		System.out.println(path);
//
//		path = test.parse(ListUtils.asList("a", ".", "b", "{", "dsa", "}", "[", "1", "]"));
//		System.out.println(path);
//
//		path = test.parse(ListUtils.asList("a", ".", "b", "(", "dsa", ")"));
//		System.out.println(path);
//
//		path = test.parse(ListUtils.asList("a", ".", "b", "(", "dsa", ")", "[", "1", "]"));
//		System.out.println(path);
//
//		path = test.parse(ListUtils.asList("a", ".", "b", "(", "dsa", ")", "{", "absd", "}"));
//		System.out.println(path);
		
//		String path = "a.b";
		String path = "a.a(d, e).b[1].c{\"iuhu\"}";
//		String path = "\"juuuuuuji\".length()";
//		String path = "\"juuuuuuji\".substring(1)";
		
		BPLexer lexer = new BPLexer(new StringReader(path));
		List<String> tokens = lexer.tokenize();
		
		System.out.println("Tokens: " + tokens);
		
		BPParser parser = new BPParser();
		BeanPath bp = parser.parse(tokens);
		
		long start = System.currentTimeMillis();
		for(int i = 0;i < 1000;i++) {
			parser.parse(tokens);
		}
		long end = System.currentTimeMillis();
		System.out.println("Time: " + (end - start));
		
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
