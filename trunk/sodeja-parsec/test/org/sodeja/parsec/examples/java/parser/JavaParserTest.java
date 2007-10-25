package org.sodeja.parsec.examples.java.parser;

import static org.sodeja.parsec.examples.java.JavaLexer.tokenize;
import static org.sodeja.parsec.semantic.AbstractSemanticParser.applyParser;
import junit.framework.TestCase;

import org.sodeja.parsec.examples.java.lexer.model.Keywords;
import org.sodeja.parsec.examples.java.model.JCompoundType;
import org.sodeja.parsec.examples.java.model.JIdentifier;
import org.sodeja.parsec.examples.java.model.JImport;
import org.sodeja.parsec.examples.java.model.JPackage;
import org.sodeja.parsec.examples.java.model.JPrimitive;
import org.sodeja.parsec.examples.java.model.JQualifiedIdentifier;
import org.sodeja.parsec.examples.java.model.JSimpleCompoundType;
import org.sodeja.parsec.examples.java.model.JType;
import org.sodeja.parsec.examples.java.model.JTypeArgument;
import org.sodeja.parsec.examples.java.model.JTypeArgumentBound;
import org.sodeja.parsec.examples.java.model.JTypeArgumentBoundCont;

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
	
	private void checkImport(JImport result, boolean isStatic, String name, boolean isAll) {
		assertNotNull(result);
		assertEquals(isStatic, result.isStatic);
		assertEquals(name, result.name.toString());
		assertEquals(isAll, result.isAll);
	}
	
	public void testBasicType() throws Exception {
		JPrimitive result = applyParser(parser.BASIC_TYPE, tokenize("long"));
		assertEquals(Keywords.LONG, result.keyword);
	}
	
	public void testCompoundType() throws Exception {
		// C
		JCompoundType result = applyParser(parser.COMPOUND_TYPE, tokenize("Test"));
		assertNotNull(result);
		assertFalse(result.array);
		
		assertEquals(1, result.types.size());
		JSimpleCompoundType rawType = result.types.get(0);
		assertNotNull(rawType);
		assertEquals("Test", rawType.name.value);
		
		assertEquals(0, rawType.arguments.size());

		// C[]
		result = applyParser(parser.COMPOUND_TYPE, tokenize("Test[]"));
		assertNotNull(result);
		assertTrue(result.array);
		
		assertEquals(1, result.types.size());
		rawType = result.types.get(0);
		assertNotNull(rawType);
		assertEquals("Test", rawType.name.value);
		
		assertEquals(0, rawType.arguments.size());

		// C<?>
		result = applyParser(parser.COMPOUND_TYPE, tokenize("Test<?>"));
		assertNotNull(result);
		assertFalse(result.array);
		
		assertEquals(1, result.types.size());
		rawType = result.types.get(0);
		assertNotNull(rawType);
		assertEquals("Test", rawType.name.value);
		
		assertEquals(1, rawType.arguments.size());
		JTypeArgumentBound bound = (JTypeArgumentBound) rawType.arguments.get(0);
		assertNull(bound.content);

		// C<?>[]
		result = applyParser(parser.COMPOUND_TYPE, tokenize("Test<?>[]"));
		assertNotNull(result);
		assertTrue(result.array);
		
		assertEquals(1, result.types.size());
		rawType = result.types.get(0);
		assertNotNull(rawType);
		assertEquals("Test", rawType.name.value);
		
		assertEquals(1, rawType.arguments.size());
		bound = (JTypeArgumentBound) rawType.arguments.get(0);
		assertNull(bound.content);

		// C<? extends D>
		result = applyParser(parser.COMPOUND_TYPE, tokenize("Test<? extends Test1>"));
		assertNotNull(result);
		assertFalse(result.array);
		
		assertEquals(1, result.types.size());
		rawType = result.types.get(0);
		assertNotNull(rawType);
		assertEquals("Test", rawType.name.value);
		
		assertEquals(1, rawType.arguments.size());
		bound = (JTypeArgumentBound) rawType.arguments.get(0);
		assertNotNull(bound.content);
		
		JTypeArgumentBoundCont boundCont = bound.content;
		assertFalse(boundCont.isSuper);
		assertEquals("Test1", boundCont.type.types.get(0).name.value);

		// C<? super D>
		result = applyParser(parser.COMPOUND_TYPE, tokenize("Test<? super Test1>"));
		assertNotNull(result);
		assertFalse(result.array);
		
		assertEquals(1, result.types.size());
		rawType = result.types.get(0);
		assertNotNull(rawType);
		assertEquals("Test", rawType.name.value);
		
		assertEquals(1, rawType.arguments.size());
		bound = (JTypeArgumentBound) rawType.arguments.get(0);
		assertNotNull(bound.content);
		
		boundCont = bound.content;
		assertTrue(boundCont.isSuper);
		assertEquals("Test1", boundCont.type.types.get(0).name.value);
		
		// C<D>
		result = applyParser(parser.COMPOUND_TYPE, tokenize("Test<Test1>"));
		assertNotNull(result);
		assertFalse(result.array);
		
		assertEquals(1, result.types.size());
		rawType = result.types.get(0);
		assertNotNull(rawType);
		assertEquals("Test", rawType.name.value);
		
		assertEquals(1, rawType.arguments.size());
		JCompoundType gen = (JCompoundType) rawType.arguments.get(0);
		assertEquals("Test1", gen.types.get(0).name.value);
	}	
}
