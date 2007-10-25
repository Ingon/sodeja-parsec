package org.sodeja.parsec.examples.java.model;

import java.util.ArrayList;
import java.util.List;

public class JCompoundType {
	public final List<JSimpleCompoundType> types;
	public final boolean array;
	
	public JCompoundType(List<JSimpleCompoundType> types, Boolean array) {
		this.types = types != null ? types : new ArrayList<JSimpleCompoundType>(0);
		this.array = array;
	}
}
