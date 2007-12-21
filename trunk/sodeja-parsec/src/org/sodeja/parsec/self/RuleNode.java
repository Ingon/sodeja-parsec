package org.sodeja.parsec.self;

import java.util.List;

public class RuleNode implements Node {
	public final String name;
	public final List<Object> nodes;
	
	public RuleNode(String name, List<Object> nodes) {
		this.name = name;
		this.nodes = nodes;
	}
}
