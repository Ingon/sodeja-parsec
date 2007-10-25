package org.sodeja.parsec.examples.java.lexer;

import java.io.FileReader;
import java.io.StringReader;
import java.util.List;

import junit.framework.TestCase;

import org.sodeja.generator.Gen;
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
	
	private List<InputElement> readFully(Gen<Character> reader) {
		TerminalSymbolFunction terminalFunctor = new TerminalSymbolFunction(reader);
		Gen<TerminalSymbol> termGen = new Gen<TerminalSymbol>(terminalFunctor);
		
		LexicalFunction lexicalFunctor = new LexicalFunction(termGen);
		return Generators.readFully(new Gen<InputElement>(lexicalFunctor));
	}
}
