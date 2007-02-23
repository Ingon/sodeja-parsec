package org.sodeja.parsec.examples.script.model;

import java.util.List;

public class Script {
	public final List<Block> blocks;

	public Script(final List<Block> blocks) {
		this.blocks = blocks;
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		for(Block block : blocks) {
			res.append(block.toString());
		}
		return res.toString();
	}
}
