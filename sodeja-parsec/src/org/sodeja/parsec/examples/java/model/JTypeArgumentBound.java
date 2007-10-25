package org.sodeja.parsec.examples.java.model;

import org.sodeja.parsec.examples.java.lexer.model.Token.Operator;

public class JTypeArgumentBound implements JTypeArgument {
	
	public final JTypeArgumentBoundCont content;
	
	public JTypeArgumentBound(Operator op, JTypeArgumentBoundCont content) {
		this.content = content;
	}
}
