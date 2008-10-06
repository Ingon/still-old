package org.still.lexer;

class RangeCharacterClass extends CharacterClass {
	private final char from;
	private final char to;
	
	public RangeCharacterClass(char from, char to) {
		this.from = from;
		this.to = to;
	}

	@Override
	public boolean matches(Character ch) {
		return ch >= from && ch <= to;
	}
}
