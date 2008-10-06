package org.still.lexer;

class CompositeCharacterClass extends CharacterClass {

	private final CharacterClass[] classes;
	
	public CompositeCharacterClass(CharacterClass... classes) {
		this.classes = classes;
	}

	@Override
	public boolean matches(Character ch) {
		for(CharacterClass clazz : classes) {
			if(clazz.matches(ch)) {
				return true;
			}
		}
		return false;
	}
}
