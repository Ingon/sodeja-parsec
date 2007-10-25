package org.sodeja.parsec.examples.java.lexer;

import static org.sodeja.parsec.examples.java.lexer.model.TerminalSymbol.input;
import static org.sodeja.parsec.examples.java.lexer.model.TerminalSymbol.line;

import java.io.FileReader;
import java.io.StringReader;
import java.util.List;

import junit.framework.TestCase;

import org.sodeja.collections.ListUtils;
import org.sodeja.generator.Gen;
import org.sodeja.generator.Generators;
import org.sodeja.parsec.examples.java.lexer.model.TerminalSymbol;

public class TerminalSymbolFunctionTest extends TestCase {
	public void testTokenize() throws Exception {
		assertEquals(ListUtils.asList(input('a')), readFully("a"));
		assertEquals(ListUtils.asList(line()), readFully("\n"));
		assertEquals(ListUtils.asList(line()), readFully("\r"));
		assertEquals(ListUtils.asList(line()), readFully("\r\n"));
		assertEquals(ListUtils.asList(input('a'), line(), input('b')), readFully("a\r\nb"));
	}

	public void testTokenize1() throws Exception {
		System.out.println(readFullyFile("src/org/sodeja/parsec/examples/java/JavaMain.java"));
	}
		
	private List<TerminalSymbol> readFully(String str) throws Exception {
		return readFully(Generators.readerGenerator(new StringReader(str)));
	}

	private List<TerminalSymbol> readFullyFile(String str) throws Exception {
		return readFully(Generators.readerGenerator(new FileReader(str)));
	}

	private List<TerminalSymbol> readFully(Gen<Character> reader) throws Exception {
		TerminalSymbolFunction terminalFunctor = new TerminalSymbolFunction(reader);
		return Generators.readFully(new Gen<TerminalSymbol>(terminalFunctor));
	}
}
