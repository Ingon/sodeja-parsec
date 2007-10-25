package org.sodeja.parsec.examples.java.lexer.model;

public enum Keywords {
    ABSTRACT,
    ASSERT,
    BOOLEAN,
    BREAK,
    BYTE,
    CASE,
    CATCH,
    CHAR,
    CLASS,
    CONST,
    CONTINUE,
    DEFAULT,
    DO,
    DOUBLE,
    ELSE,
    ENUM,
    EXTENDS,
    FINAL,
    FINALLY,
    FLOAT,
    FOR,
    IF,
    GOTO,
    IMPLEMENTS,
    IMPORT,
    INSTANCEOF,
    INT,
    INTERFACE,
    LONG,
    NATIVE,
    NEW,
    PACKAGE,
    PRIVATE,
    PROTECTED,
    PUBLIC,
    RETURN,
    SHORT,
    STATIC,
    STRICTFP,
    SUPER,
    SWITCH,
    SYNCHRONIZED,
    THIS,
    THROW,
    THROWS,
    TRANSIENT,
    TRY,
    VOID,
    VOLATILE,
    WHILE;
    
    public static Keywords find(String str) {
    	Keywords[] values = values();
    	for(int i = 0;i < values.length;i++) {
    		if(values[i].name().toLowerCase().equals(str)) {
    			return values[i];
    		}
    	}
    	return null;
    }
}
