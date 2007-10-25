package org.sodeja.parsec.examples.java.parser;

import static org.sodeja.parsec.examples.java.JavaLexer.tokenize;
import static org.sodeja.parsec.semantic.AbstractSemanticParser.applyParser;
import junit.framework.TestCase;

import org.sodeja.parsec.examples.java.lexer.model.Keywords;
import org.sodeja.parsec.examples.java.model.JIdentifier;
import org.sodeja.parsec.examples.java.model.JImport;
import org.sodeja.parsec.examples.java.model.JPackage;
import org.sodeja.parsec.examples.java.model.JPrimitive;
import org.sodeja.parsec.examples.java.model.JQualifiedIdentifier;

public class JavaParserTest extends TestCase {
	
	private JavaParser parser = new JavaParser();
	
	public void testIdentifier() throws Exception {
		JIdentifier result = applyParser(parser.IDENTIFIER, tokenize("com"));
		assertNotNull(result);
		assertEquals("com", result.toString());
	}
	
	public void testQualifiedIdentifier() throws Exception {
		JQualifiedIdentifier result = applyParser(parser.QUALIFIED_IDENTIFIER, tokenize("org.sodeja.parsec"));
		assertNotNull(result);
		assertEquals(3, result.idenfiers.size());
		assertEquals("org.sodeja.parsec", result.toString());
	}
	
	public void testPackage() throws Exception {
		JPackage result = applyParser(parser.PACKAGE, tokenize("package org.sodeja.parsec;"));
		assertNotNull(result);
		assertEquals("org.sodeja.parsec", result.name.toString());
	}
	
	public void testImport() throws Exception {
		JImport result = applyParser(parser.IMPORT, tokenize("import org.sodeja.parsec.examples.java.parser;"));
		checkImport(result, false, "org.sodeja.parsec.examples.java.parser", false);

		result = applyParser(parser.IMPORT, tokenize("import static org.sodeja.parsec.examples.java.parser;"));
		checkImport(result, true, "org.sodeja.parsec.examples.java.parser", false);

		result = applyParser(parser.IMPORT, tokenize("import org.sodeja.parsec.examples.java.parser.*;"));
		checkImport(result, false, "org.sodeja.parsec.examples.java.parser", true);

		result = applyParser(parser.IMPORT, tokenize("import static org.sodeja.parsec.examples.java.parser.*;"));
		checkImport(result, true, "org.sodeja.parsec.examples.java.parser", true);
	}
	
	public void testBasicType() throws Exception {
		JPrimitive result = applyParser(parser.BASIC_TYPE, tokenize("long"));
		assertEquals(Keywords.LONG, result.keyword);
	}
	
	private void checkImport(JImport result, boolean isStatic, String name, boolean isAll) {
		assertNotNull(result);
		assertEquals(isStatic, result.isStatic);
		assertEquals(name, result.name.toString());
		assertEquals(isAll, result.isAll);
	}
}
