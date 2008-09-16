package org.still;

import java.util.List;

import org.still.src.Token;
import org.still.src.TokenType;

public class ParserContext {
	private final List<Token> tokens;
	private int position;
	
	public ParserContext(List<Token> tokens) {
		this.tokens = tokens;
		this.position = -1;
	}
	
	public void nextToken() {
		position++;
		if(hasMoreTokens() && currentToken().type == TokenType.WHITESPACE) {
			nextToken();
		}
	}
	
	public Token currentToken() {
		return tokens.get(position);
	}

	public boolean hasMoreTokens() {
		return tokens.size() > position;
	}
	
	public boolean noMoreTokens() {
		return ! hasMoreTokens();
	}
}
