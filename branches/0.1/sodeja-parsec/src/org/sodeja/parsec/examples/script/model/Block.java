package org.sodeja.parsec.examples.script.model;

import java.util.List;

public class Block {
	public final String name;
	public final List<Command> commands;
	
	public Block(final String name, final List<Command> commands) {
		this.name = name;
		this.commands = commands;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		sb.append(" {");
		
		for(Command cmd : commands) {
			sb.append("\r\n   ");
			sb.append(cmd);
		}
		
		sb.append("\r\n}");
		
		return sb.toString();
	}
}
