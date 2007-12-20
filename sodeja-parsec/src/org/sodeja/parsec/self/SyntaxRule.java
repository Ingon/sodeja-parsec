package org.sodeja.parsec.self;

import java.util.List;

public class SyntaxRule {
	public final MetaIdentifier metaIdentifier;
	public final List<Definition> definitions;
	
	public SyntaxRule(MetaIdentifier metaIdentifier, List<Definition> definitions) {
		this.metaIdentifier = metaIdentifier;
		this.definitions = definitions;
	}
}
