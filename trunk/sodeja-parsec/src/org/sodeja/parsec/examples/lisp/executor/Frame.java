package org.sodeja.parsec.examples.lisp.executor;

import java.util.HashMap;
import java.util.Map;

public class Frame {
	protected final Frame parent;
	protected final Map<String, Object> objects;
	
	public Frame(Frame parent) {
		this(parent, new HashMap<String, Object>());
	}

	public Frame(Frame parent, Map<String, Object> objects) {
		this.parent = parent;
		this.objects = objects;
	}
	
	public Object getSymbolValue(String symbol) {
		Object value = objects.get(symbol);
		if(value == null) {
			throw new IllegalArgumentException("Symbol '" + symbol + "' does not exists!");
		}
		return value;
	}
	
	public boolean containsSymbol(String symbol) {
		return objects.containsKey(symbol);
	}

	public void addSymbol(String symbol, Object value) {
		if(containsSymbol(symbol)) {
			throw new IllegalArgumentException("Not possible to redefine names in this context");
		}
		objects.put(symbol, value);
	}
}
