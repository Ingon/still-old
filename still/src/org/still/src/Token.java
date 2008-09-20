package org.still.src;


public class Token {
	public final TokenType type;
	public final String value;
	
	public Token(TokenType type, String value) {
		this.type = type;
		this.value = value;
	}

	@Override
	public String toString() {
		return "(" + type + ":" + value + ")";
	}
	
	public boolean isSymbol() {
		return type == TokenType.SYMBOL;
	}

	public boolean isSymbol(String value) {
		return isSymbol() && this.value.equals(value);
	}
	
	public boolean isSeparator() {
		return type == TokenType.SEPARATOR;
	}

	public boolean isSeparator(String value) {
		return isSeparator() && this.value.equals(value);
	}

	public boolean isUnary() {
		return type == TokenType.UNARY_OPERATOR;
	}

	public boolean isBinary() {
		return type == TokenType.BINARY_OPERATOR;
	}
	
	public Symbol asSymbol() {
		return Symbol.get(value);
	}
	
	public boolean is(String val) {
		return value.equals(val);
	}
}
