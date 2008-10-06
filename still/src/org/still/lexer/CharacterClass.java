package org.still.lexer;

abstract class CharacterClass {
	
	public abstract boolean matches(Character ch);
	
	public String readWhole(Lexer lexer) {
		StringBuffer sb = new StringBuffer();
		sb.append(lexer.ch);
		while(lexer.hasNext()) {
			lexer.nextChar();
			if(matches(lexer.ch)) {
				sb.append(lexer.ch);
			} else {
				break;
			}
		}
		
		return sb.toString();
	}
}
