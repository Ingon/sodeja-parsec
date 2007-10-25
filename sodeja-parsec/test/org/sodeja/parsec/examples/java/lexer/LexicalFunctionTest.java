package org.sodeja.parsec.examples.java.lexer;

import java.io.FileReader;
import java.io.StringReader;
import java.util.List;

import junit.framework.TestCase;

import org.sodeja.generator.Generator;
import org.sodeja.generator.Generators;
import org.sodeja.parsec.examples.java.lexer.model.InputElement;
import org.sodeja.parsec.examples.java.lexer.model.TerminalSymbol;

public class LexicalFunctionTest extends TestCase {
	public void testParse() throws Exception {
		System.out.println(readFullyFile("src/org/sodeja/parsec/examples/java/JavaMain.java"));
	}

	private List<InputElement> readFully(String str) throws Exception {
		return readFully(Generators.readerGenerator(new StringReader(str)));
	}

	private List<InputElement> readFullyFile(String str) throws Exception {
		return readFully(Generators.readerGenerator(new FileReader(str)));
	}
	
	private List<InputElement> readFully(Generator<Character> reader) {
		TerminalSymbolFunction terminalFunctor = new TerminalSymbolFunction(reader);
		Generator<TerminalSymbol> termGen = new Generator<TerminalSymbol>(terminalFunctor);
		
		LexicalFunction lexicalFunctor = new LexicalFunction(termGen);
		return Generators.readFully(new Generator<InputElement>(lexicalFunctor));
	}
}
