package org.sodeja.parsec.examples.java.model;

import java.util.ArrayList;
import java.util.List;

public class JSimpleCompoundType {
	public final JIdentifier name;
	public final List<JTypeArgument> arguments;
	
	public JSimpleCompoundType(JIdentifier identifier, List<JTypeArgument> arguments) {
		this.name = identifier;
		this.arguments = arguments != null ? arguments : new ArrayList<JTypeArgument>(0);
	}
}
