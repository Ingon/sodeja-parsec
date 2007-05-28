package org.sodeja.parsec.examples.script;

import java.io.StringReader;
import java.util.List;

import org.sodeja.parsec.examples.script.model.Script;

public class ScriptMain {
	public static void main(String[] args) {
		String target = "  # What to do in the morning\r\n"
				+ "morning {\r\n"
				+ "wake_up;\r\n"
				+ "eat bacon;\r\n"
				+ "brush_teeth;\r\n"
				+ "wash hair=true minutes=10;\r\n"
				+ "}\r\n";
		
		ScriptLexer lexer = new ScriptLexer(new StringReader(target));
		List<String> tokens = lexer.tokenize();
		
		System.out.println("Result: " + tokens);
		
		ScriptParser parser = new ScriptParser();
		Script script = parser.parse(tokens);
		
		System.out.println("Script: " + script);
	}
}
