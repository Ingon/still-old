package org.still.lexer;

public class Token {
	public static Token get(TokenType type, String value) {
		// TODO cache these
		return new Token(type, value);
	}
	
	public final TokenType type;
	public final String value;
	
	private Token(TokenType type, String value) {
		this.type = type;
		this.value = value;
	}

	@Override
	public String toString() {
		return type + "@" + value;
	}

	public boolean isSeparator(String string) {
		return isSeparator() && value.equals(string);
	}

	private boolean isSeparator() {
		return type == TokenType.SEPARATOR;
	}

	public boolean isName() {
		return type == TokenType.NAME || type == TokenType.OPERATOR;
	}

	public boolean isSpecial(String string) {
		return isSpecial() && value.equals(string);
	}

	private boolean isSpecial() {
		return type == TokenType.SPECIAL;
	}

	public boolean isWhiteSpace() {
		return type == TokenType.WHITE_SPACE;
	}
}
