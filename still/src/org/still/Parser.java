package org.still;

import java.util.List;

import org.still.src.Expression;
import org.still.src.Identifier;
import org.still.src.IntegerLiteral;
import org.still.src.StringLiteral;
import org.still.src.Token;
import org.still.src.TokenType;

public class Parser {
	public Expression parseExpression(String str) {
		List<Token> tokens = Lexer.tokenize(str);
		System.out.println(tokens);
		return expression(tokens, 0);
	}
	
	private Expression expression(List<Token> tokens, int position) {
		Expression left = binaryOperand(tokens, position);
		return null;
	}
	
	private Expression binaryOperand(List<Token> tokens, int position) {
		Token token = tokens.get(position);
		if(token.type == TokenType.UNARY_OPERATOR) {
			throw new UnsupportedOperationException();
		}
		
		return simpleExpression(tokens, position);
	}
	
	private Expression simpleExpression(List<Token> tokens, int position) {
		Token token = tokens.get(position);
		if(token.type == TokenType.IDENTIFIER) {
			return new Identifier(token.value);
		}
		
		if(token.type == TokenType.STRING) {
			return new StringLiteral(token.value);
		}
		
		if(token.type == TokenType.NUMBER) {
			return new IntegerLiteral(token.value);
		}
		
		return null;
	}
}
