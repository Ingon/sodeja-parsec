package org.sodeja.parsec.standart;

import java.util.List;

import org.sodeja.collections.ConsList;
import org.sodeja.functional.Function1;
import org.sodeja.functional.Pair;
import org.sodeja.functional.Predicate1;
import org.sodeja.parsec.AbstractParser;
import org.sodeja.parsec.ApplyParser;
import org.sodeja.parsec.Parser;
import org.sodeja.parsec.SatisfiesParser;

public class SimpleIntegerParser extends AbstractParser<String, Integer> {
	private static boolean allDigits(CharSequence chars) {
		for(int i = 0, n = chars.length();i < n;i++) {
			if(! Character.isDigit(chars.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	private final Parser<String, String> digitsParser;
	private final Parser<String, Integer> convertParser;
	
	public SimpleIntegerParser(String name) {
		super(name);
		
		digitsParser = new SatisfiesParser<String>(name + "_DIGITS", 
			new Predicate1<String>() {
				public Boolean execute(String p) {
					return allDigits(p);
				}
			}
		);
		
		convertParser = new ApplyParser<String, Integer, String>(name + "_CONVERT", 
			digitsParser, new Function1<Integer, String>() {
				public Integer execute(String p) {
					return new Integer(p);
				}
			}
		);
	}
	
	@Override
	protected List<Pair<Integer, ConsList<String>>> executeDelegate(ConsList<String> tokens) {
		return convertParser.execute(tokens);
	}
}
