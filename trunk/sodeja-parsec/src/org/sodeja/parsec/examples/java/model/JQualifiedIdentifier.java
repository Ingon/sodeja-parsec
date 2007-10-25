package org.sodeja.parsec.examples.java.model;

import java.util.List;

import org.sodeja.lang.StringUtils;

public class JQualifiedIdentifier {
	public final List<JIdentifier> idenfiers;
	
	public JQualifiedIdentifier(List<JIdentifier> idenfiers) {
		this.idenfiers = idenfiers;
	}

	@Override
	public String toString() {
		return StringUtils.appendWithSeparatorToString(idenfiers, ".");
	}
}
