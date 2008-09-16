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
		
		Token token = ctx.currentToken();
		if(token.isBinary()) {
			ctx.nextToken();
			Expression right = expression(ctx);
			return new BinaryOperator(left, token.asSymbol(), right);
		}
		return left;
	}
	
	private Expression binaryOperand(ParserContext ctx) {
		if(ctx.noMoreTokens()) {
			throw new RuntimeException("No binary operand found");
		}
		
		Token token = ctx.currentToken();
		if(token.isUnary()) {
			ctx.nextToken();
			return new UnaryOperator(operand(ctx), token.asSymbol());
		}
		
		return operand(ctx);
	}
	
	private Expression operand(ParserContext ctx) {
		// Method call
		// Prop access
		// List access
		Expression leaf = leaf(ctx);
		Token token = ctx.currentToken();
		if(token.isSeparator()) {
			if(token.value.equals(".")) {
				ctx.nextToken();
				Identifier propName = identifier(ctx, true);
				
				token = ctx.currentToken();
				// Method call
				if(token.isSeparator() && token.is("[")) {
					ctx.nextToken();
					token = ctx.currentToken();
					
					if(token.isSeparator() && token.is("]")) {
//						return 
					}
				}
			}
		}
		
		return leaf;
	}
	
	private Expression leaf(ParserContext ctx) {
		Expression ident = identifier(ctx, false);
		if(ident != null) {
			return ident;
		}
		
		Token token = ctx.currentToken();
		if(token.type == TokenType.STRING) {
			ctx.nextToken();
			return new StringLiteral(token.value);
		}
		
		if(token.type == TokenType.NUMBER) {
			ctx.nextToken();
			return new IntegerLiteral(token.value);
		}
		
		if(token.isSeparator() && token.value.equals("(")) {
			ctx.nextToken();
			Expression internal = expression(ctx);
			token = ctx.currentToken();
			if(! token.isSeparator() || !token.value.equals(")")) {
				throw new RuntimeException("Parse failed exception: expected )");
			}
			ctx.nextToken();
			return internal;
		}
		
		throw new RuntimeException("Parse failed exception: expected simple expression");
	}
	
	private Identifier identifier(ParserContext ctx, boolean strict) {
		Token token = ctx.currentToken();
		if(token.isIdentifier()) {
			ctx.nextToken();
			return new Identifier(token.asSymbol());
		}
		
		if(strict) {
			throw new RuntimeException("Parse failed exception: expected identifier");
		} else {
			return null;
		}
	}
}
