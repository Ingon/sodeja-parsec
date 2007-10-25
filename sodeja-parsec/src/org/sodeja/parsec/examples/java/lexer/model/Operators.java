package org.sodeja.parsec.examples.java.lexer.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.sodeja.lang.StringUtils;

/**
 * E -> =
 * G -> >
 * L -> <
 * N -> !
 * A -> &
 * O -> |
 * T -> ~
 * Q -> ?
 * C -> :
 * P -> +
 * M -> -
 * S -> *
 * D -> /
 * H -> ^
 * V -> %
 */
public enum Operators {
	E   ("="),
	EE  ("=="),
	P   ("+"),
	PE  ("+="),
	G   (">"),
	GE  (">="),
	M   ("-"),
	ME  ("-="),
	L   ("<"),
	LE  ("<="),
	S   ("*"),
	SE  ("*="),
	N   ("!"),
	NE  ("!="),
	D   ("/"),
	DE  ("/="),
	A   ("&"),
	AA  ("&&"),
	AE  ("&="),
	O   ("|"),
	OO  ("||"),
	OE  ("|="),
	T   ("~"),
	Q   ("?"),
	C   (":"),
	PP  ("++"),
	MM  ("--"),
	H   ("^"),
	HE  ("^="),
	V   ("%"),
	VE  ("%="),
	LL  ("<<"),
	LLE ("<<="),
	GG  (">>"),
	GGE (">>="),
	GGG (">>>"),
	GGGE(">>>=");
	
	private final String value;
	
	private Operators(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

	public static List<Operators> filter(String str) {
		if(StringUtils.isTrimmedEmpty(str) || str.length() > 4) {
			return Collections.emptyList();
		}
		
		List<Operators> result = new ArrayList<Operators>();
		Operators[] values = values();
		for(int i = 0;i < values.length;i++) {
			if(values[i].value.startsWith(str)) {
				result.add(values[i]);
			}
		}
		return result;
	}
}
