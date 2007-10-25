package org.sodeja.parsec.examples.java.lexer;

import java.io.StringReader;
import java.util.List;

import junit.framework.TestCase;

import org.sodeja.collections.ListUtils;
import org.sodeja.generator.Gen;
import org.sodeja.generator.Generators;

public class UnicodeResolverFunctionTest extends TestCase {
	public void testResolver() throws Exception {
		assertEquals(ListUtils.asList('a', 'b', 'c'), readFully("abc"));
		assertEquals(ListUtils.asList('Z'), readFully("\\u005a"));
		assertEquals(ListUtils.asList('a', 'Z'), readFully("a\\u005a"));
		assertEquals(ListUtils.asList('a', 'Z', 'a'), readFully("a\\u005aa"));
		assertEquals(ListUtils.asList('\\', '\\', 'u', '0', '0', '5', 'a'), readFully("\\\\u005a"));
		try {
			readFully("\\u005y");
			assertTrue(false);
		} catch(RuntimeException exc) {
			assertTrue(true);
		}
	}
	
	private List<Character> readFully(String str) throws Exception {
		Gen<Character> reader = Generators.readerGenerator(new StringReader(str));
		UnicodeResolverFunction functor = new UnicodeResolverFunction(reader);
		return Generators.readFully(new Gen<Character>(functor));
	}
}
