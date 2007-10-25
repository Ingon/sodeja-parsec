package org.sodeja.parsec.examples.java.lexer;

import java.io.StringReader;
import java.util.List;

import org.sodeja.collections.ListUtils;
import org.sodeja.generator.Generator;
import org.sodeja.generator.Generators;
import org.sodeja.parsec.examples.java.lexer.model.TerminalSymbol;
import static org.sodeja.parsec.examples.java.lexer.model.TerminalSymbol.*;

import junit.framework.TestCase;

public class TerminalSymbolFunctionTest extends TestCase {
	public void testTokenize() throws Exception {
		assertEquals(ListUtils.asList(input('a')), readFully("a"));
		assertEquals(ListUtils.asList(line()), readFully("\n"));
		assertEquals(ListUtils.asList(line()), readFully("\r"));
		assertEquals(ListUtils.asList(line()), readFully("\r\n"));
		assertEquals(ListUtils.asList(input('a'), line(), input('b')), readFully("a\r\nb"));
	}

	private List<TerminalSymbol> readFully(String str) throws Exception {
		Generator<Character> reader = Generators.readerGenerator(new StringReader(str));
		TerminalSymbolFunction terminalFunctor = new TerminalSymbolFunction(reader);
		return Generators.readFully(new Generator<TerminalSymbol>(terminalFunctor));
	}
}
