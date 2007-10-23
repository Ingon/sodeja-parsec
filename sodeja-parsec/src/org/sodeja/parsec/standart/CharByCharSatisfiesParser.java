package org.sodeja.parsec.standart;

import java.util.List;

import org.sodeja.collections.ConsList;
import org.sodeja.functional.Pair;
import org.sodeja.functional.Predicate1;
import org.sodeja.parsec.combinator.AbstractParser;
import org.sodeja.parsec.combinator.SatisfiesParser;

public abstract class CharByCharSatisfiesParser extends AbstractParser<String, String> {
	private SatisfiesParser<String> internal;
	
	public CharByCharSatisfiesParser(String name) {
		super(name);
		
		internal = new SatisfiesParser<String>(name + "_SATISFIES", new Predicate1<String>() {
			public Boolean execute(String p) {
				for (int i = 0, n = p.length(); i < n; i++) {
					if(! checkChar(p.charAt(i))) {
						return false;
					}
				}
				return true;
			}
		});
	}
	
	@Override
	protected List<Pair<String, ConsList<String>>> executeDelegate(ConsList<String> tokens) {
		return internal.execute(tokens);
	}

	protected abstract boolean checkChar(char ch);
}
