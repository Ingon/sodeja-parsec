package org.sodeja.parsec.self.model;

import java.util.List;

public class Optional implements Primary {
	public final List<Definition> definitions;

	public Optional(List<Definition> definitions) {
		this.definitions = definitions;
	}
}
