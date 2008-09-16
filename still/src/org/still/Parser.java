package org.still;

import java.util.List;

import org.still.src.BinaryOperator;
import org.still.src.Expression;
import org.still.src.Identifier;
import org.still.src.IntegerLiteral;
import org.still.src.StringLiteral;
import org.still.src.Token;
import org.still.src.TokenType;
import org.still.src.UnaryOperator;

public class Parser {
	public Expression parseExpression(String str) {
		List<Token> tokens = Lexer.tokenize(str);
		System.out.println(tokens);
		ParserContext ctx = new ParserContext(tokens);
		ctx.nextToken();
		return expression(ctx);
	}
	
	private Expression expression(ParserContext ctx) {
		Expression left = binaryOperand(ctx);
		if(ctx.noMoreTokens()) {
			return left;
		}
		
		Token current = ctx.currentToken();
		if(current.type == TokenType.BINARY_OPERATOR) {
			ctx.nextToken();
			Expression right = expression(ctx);
			return new BinaryOperator(left, Symbol.get(current.value), right);
		}
		return left;
	}
	
	private Expression binaryOperand(ParserContext ctx) {
		if(ctx.noMoreTokens()) {
			throw new RuntimeException("No binary operand found");
		}
		
		Token token = ctx.currentToken();
		if(token.type == TokenType.UNARY_OPERATOR) {
			ctx.nextToken();
			return new UnaryOperator(simpleExpression(ctx), Symbol.get(token.value));
		}
		
		return simpleExpression(ctx);
	}
	
	private Expression simpleExpression(ParserContext ctx) {
		Token token = ctx.currentToken();
		if(token.type == TokenType.IDENTIFIER) {
			ctx.nextToken();
			return new Identifier(Symbol.get(token.value));
		}
		
		if(token.type == TokenType.STRING) {
			ctx.nextToken();
			return new StringLiteral(token.value);
		}
		
		if(token.type == TokenType.NUMBER) {
			ctx.nextToken();
			return new IntegerLiteral(token.value);
		}
		
		if(token.type == TokenType.SEPARATOR && token.value.equals("(")) {
			ctx.nextToken();
			Expression internal = expression(ctx);
			if(token.type != TokenType.SEPARATOR || !token.value.equals(")")) {
				throw new RuntimeException("Parse failed exception: expected )");
			}
			ctx.nextToken();
			return internal;
		}
		
		throw new RuntimeException("Parse failed exception: expected simple expression");
	}
}
