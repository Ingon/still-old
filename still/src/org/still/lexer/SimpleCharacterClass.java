package org.still.lexer;

class SimpleCharacterClass extends CharacterClass {

	private final char[] chars;
	
	public SimpleCharacterClass(char ch) {
		this.chars = new char[] {ch};
	}
	
	public SimpleCharacterClass(String chars) {
		this.chars = chars.toCharArray();
	}
	
	@Override
	public boolean matches(Character ch) {
		for (char ich : chars) {
			if (ich == ch) {
				return true;
			}
		}

		return false;
	}
}
