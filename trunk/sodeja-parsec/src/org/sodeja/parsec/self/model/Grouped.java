package org.sodeja.parsec.self.model;

import java.util.List;

public class Grouped implements Primary {
	public final List<Definition> definitions;

	public Grouped(List<Definition> definitions) {
		this.definitions = definitions;
	}
}
