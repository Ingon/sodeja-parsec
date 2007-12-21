package org.sodeja.parsec.self.model;

import java.util.List;

public class Repeated implements Primary {
	public final List<Definition> definitions;

	public Repeated(List<Definition> definitions) {
		this.definitions = definitions;
	}
}
