package org.sodeja.parsec.examples.script;

import static org.sodeja.parsec.ParsecUtils.alternative1;
import static org.sodeja.parsec.ParsecUtils.apply;
import static org.sodeja.parsec.ParsecUtils.thenParser;
import static org.sodeja.parsec.ParsecUtils.thenParser3;
import static org.sodeja.parsec.ParsecUtils.thenParser4;
import static org.sodeja.parsec.ParsecUtils.zeroOrMore;
import static org.sodeja.parsec.standart.StandartParsers.alphaDigitsUnderscore;
import static org.sodeja.parsec.standart.StandartParsers.literal;

import java.util.List;

import org.sodeja.functional.Function1;
import org.sodeja.functional.Function2;
import org.sodeja.functional.Function3;
import org.sodeja.functional.Function4;
import org.sodeja.functional.Pair;
import org.sodeja.parsec.Parser;
import org.sodeja.parsec.examples.script.model.Block;
import org.sodeja.parsec.examples.script.model.Command;
import org.sodeja.parsec.examples.script.model.NamedProperty;
import org.sodeja.parsec.examples.script.model.Property;
import org.sodeja.parsec.examples.script.model.PropertyValue;
import org.sodeja.parsec.examples.script.model.Script;

public class ScriptParser {
	public final Parser<String, PropertyValue> VALUE_PARSER =
		apply("VALUE_PARSER", alphaDigitsUnderscore("VALUE_PARSER_CONTENT"), 
			new Function1<PropertyValue, String>() {
				public PropertyValue execute(String p) {
					return new PropertyValue(p);
				}
			}
		);
	
	public final Parser<String, String> NAME_PARSER =
		thenParser("NAME_PARSER", alphaDigitsUnderscore("NAME_PARSER_STR"), literal("="), 
				Function2.Utils.justFirst(String.class, String.class));
	
	public final Parser<String, NamedProperty> NAME_VALUE_PARSER =
		thenParser("NAME_VALUE_PARSER", NAME_PARSER, VALUE_PARSER, 
			new Function2<NamedProperty, String, PropertyValue>() {
				public NamedProperty execute(String p1, PropertyValue p2) {
					return new NamedProperty(p1, p2);
				}
			}
		);
	
	public final Parser<String, Property> PROPERTY_PARSER =
		alternative1("PROPERTY_PARSER", NAME_VALUE_PARSER, VALUE_PARSER);
	
	public final Parser<String, List<Property>> PROPERTIES_PARSER =
		zeroOrMore("PROPERTIES_PARSER", PROPERTY_PARSER);
	
	public final Parser<String, Command> COMMAND_PARSER =
		thenParser3("COMMAND_PARSER", alphaDigitsUnderscore("COMMAND_PARSER_NAME"), PROPERTIES_PARSER, literal(";"), 
			new Function3<Command, String, List<Property>, String>() {
				public Command execute(String p1, List<Property> p2, String p3) {
					return new Command(p1, p2);
				}
			}
		);
	
	public final Parser<String, List<Command>> COMMANDS_PARSER =
		zeroOrMore("COMMANDS_PARSER", COMMAND_PARSER);
	
	public final Parser<String, Block> BLOCK_PARSER =
		thenParser4("BLOCK_PARSER", alphaDigitsUnderscore("BLOCK_PARSER_NAME"), literal("{"), COMMANDS_PARSER, literal("}"), 
			new Function4<Block, String, String, List<Command>, String>() {
				public Block execute(String p1, String p2, List<Command> p3, String p4) {
					return new Block(p1, p3);
				}
			}
		);
	
	public final Parser<String, List<Block>> BLOCKS_PARSER =
		zeroOrMore("BLOCKS_PARSER", BLOCK_PARSER);
	
	public final Parser<String, Script> SCRIPT_PARSER =
		apply("SCRIPT_PARSER", BLOCKS_PARSER, 
			new Function1<Script, List<Block>>() {
				public Script execute(List<Block> p) {
					return new Script(p);
				}
			}
		);
	
	public Script parse(List<String> tokens) {
		List<Pair<Script, List<String>>> parseResults = SCRIPT_PARSER.execute(tokens);
		for(Pair<Script, List<String>> result : parseResults) {
			if(result.second.isEmpty()) {
				return result.first;
			}
		}
		
		throw new RuntimeException("Syntax error!");
	}
}
