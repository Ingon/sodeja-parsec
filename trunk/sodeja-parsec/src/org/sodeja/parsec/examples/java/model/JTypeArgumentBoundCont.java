package org.sodeja.parsec.examples.java.model;

import org.sodeja.parsec.examples.java.lexer.model.Keywords;
import org.sodeja.parsec.examples.java.lexer.model.Token.Keyword;

public class JTypeArgumentBoundCont {
	public final boolean isSuper;
	public final JCompoundType type;
	
	public JTypeArgumentBoundCont(Keyword word, JCompoundType type) {
		this.isSuper = word.value == Keywords.SUPER;
		this.type = type;
	}
}
