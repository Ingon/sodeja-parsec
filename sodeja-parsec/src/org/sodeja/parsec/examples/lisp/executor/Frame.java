package org.sodeja.parsec.examples.lisp.executor;

import java.util.HashMap;
import java.util.Map;

public class Frame {
	protected final Frame parent;
	protected final Map<String, Object> objects;
	
	private Frame() {
		parent = null;
		objects = null;
	}
	
	public Frame(Frame parent) {
		this(parent, new HashMap<String, Object>());
	}

	public Frame(Frame parent, Map<String, Object> objects) {
		if(parent == null) {
			this.parent = new NullFrame();
		} else {
			this.parent = parent;
		}
		this.objects = objects;
	}
	
	public Object getSymbolValue(String symbol) {
		Object value = objects.get(symbol);
		if(value != null) {
			return value;
		}
		return parent.getSymbolValue(symbol);
	}
	
	public boolean containsSymbol(String symbol) {
		if(objects.containsKey(symbol)) {
			return true;
		}
		return parent.containsSymbol(symbol);
	}

	public void addSymbol(String symbol, Object value) {
		if(containsSymbol(symbol)) {
			throw new IllegalArgumentException("Not possible to redefine names in this context");
		}
		objects.put(symbol, value);
	}
	
	private static class NullFrame extends Frame {
		@Override
		public boolean containsSymbol(String symbol) {
			return false;
		}

		@Override
		public Object getSymbolValue(String symbol) {
			throw new IllegalArgumentException("Symbol '" + symbol + "' does not exists!");
		}
	}
}
