package org.sodeja.parsec.examples.script;

import static org.sodeja.parsec.ParsecUtils.alternative1;
import static org.sodeja.parsec.ParsecUtils.applyCons;
import static org.sodeja.parsec.ParsecUtils.thenParser;
import static org.sodeja.parsec.ParsecUtils.thenParser3Cons12;
import static org.sodeja.parsec.ParsecUtils.thenParser4Cons13;
import static org.sodeja.parsec.ParsecUtils.thenParserCons;
import static org.sodeja.parsec.ParsecUtils.zeroOrMore;
import static org.sodeja.parsec.standart.StandartParsers.alphaDigitsUnderscore;
import static org.sodeja.parsec.standart.StandartParsers.literal;

import java.util.List;

import org.sodeja.functional.Function2;
import org.sodeja.parsec.Parser;
import org.sodeja.parsec.examples.script.model.Block;
import org.sodeja.parsec.examples.script.model.Command;
import org.sodeja.parsec.examples.script.model.NamedProperty;
import org.sodeja.parsec.examples.script.model.Property;
import org.sodeja.parsec.examples.script.model.PropertyValue;
import org.sodeja.parsec.examples.script.model.Script;
import org.sodeja.parsec.semantic.AbstractSemanticParser;

public class ScriptParser extends AbstractSemanticParser<String, Script>{
	public final Parser<String, PropertyValue> VALUE_PARSER =
		applyCons("VALUE_PARSER", alphaDigitsUnderscore("VALUE_PARSER_CONTENT"), PropertyValue.class);
	
	public final Parser<String, String> NAME_PARSER =
		thenParser("NAME_PARSER", alphaDigitsUnderscore("NAME_PARSER_STR"), literal("="), 
				Function2.Utils.justFirst(String.class, String.class));
	
	public final Parser<String, NamedProperty> NAME_VALUE_PARSER =
		thenParserCons("NAME_VALUE_PARSER", NAME_PARSER, VALUE_PARSER, NamedProperty.class); 
	
	public final Parser<String, Property> PROPERTY_PARSER =
		alternative1("PROPERTY_PARSER", NAME_VALUE_PARSER, VALUE_PARSER);
	
	public final Parser<String, List<Property>> PROPERTIES_PARSER =
		zeroOrMore("PROPERTIES_PARSER", PROPERTY_PARSER);
	
	public final Parser<String, Command> COMMAND_PARSER =
		thenParser3Cons12("COMMAND_PARSER", alphaDigitsUnderscore("COMMAND_PARSER_NAME"), 
				PROPERTIES_PARSER, literal(";"), Command.class); 
	
	public final Parser<String, List<Command>> COMMANDS_PARSER =
		zeroOrMore("COMMANDS_PARSER", COMMAND_PARSER);
	
	public final Parser<String, Block> BLOCK_PARSER =
		thenParser4Cons13("BLOCK_PARSER", alphaDigitsUnderscore("BLOCK_PARSER_NAME"), 
				literal("{"), COMMANDS_PARSER, literal("}"), Block.class); 
	
	public final Parser<String, List<Block>> BLOCKS_PARSER =
		zeroOrMore("BLOCKS_PARSER", BLOCK_PARSER);
	
	public final Parser<String, Script> SCRIPT_PARSER =
		applyCons("SCRIPT_PARSER", BLOCKS_PARSER, Script.class);
	
	@Override
	protected Parser<String, Script> getParser() {
		return SCRIPT_PARSER;
	}
}
