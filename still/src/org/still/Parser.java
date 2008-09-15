package org.still;

import java.util.List;

import org.still.src.Expression;
import org.still.src.Token;

public class Parser {
	public Expression parseExpression(String str) {
		List<Token> tokens = Lexer.tokenize(str);
		System.out.println(tokens);
		return expression(tokens);
	}
	
	private Expression expression(List<Token> tokens) {
		return null;
	}
}
