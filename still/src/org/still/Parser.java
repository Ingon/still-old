package org.still;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.still.src.BooleanLiteral;
import org.still.src.Expression;
import org.still.src.IntegerLiteral;
import org.still.src.MethodCall;
import org.still.src.PropertyAccess;
import org.still.src.StringLiteral;
import org.still.src.Symbol;
import org.still.src.Token;
import org.still.src.TokenType;

public class Parser {
	public Expression parseExpression(String str) {
		List<Token> tokens = Lexer.tokenize(str);
		System.out.println("::Lex : " + tokens);
		ParserContext ctx = new ParserContext(tokens);
		ctx.nextToken();
		return expression(ctx);
	}
	
	private List<Expression> expressions(ParserContext ctx) {
		List<Expression> result = new ArrayList<Expression>();
		Expression current = expression(ctx);
		if(current == null) {
			return result;
		}
		
		result.add(current);
		while(ctx.currentToken().isSeparator(",")) {
			ctx.nextToken();
			current = expression(ctx);
			if(current == null) {
				throw new RuntimeException("Expected expression!");
			}
			result.add(current);
		}
		
		return result;
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
			return new MethodCall(left, token.asSymbol(), Arrays.asList(right));
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
			return new MethodCall(operand(ctx), token.asSymbol(), Collections.<Expression>emptyList());
		}
		
		return operand(ctx);
	}
	
	private Expression operand(ParserContext ctx) {
		Expression leaf = leaf(ctx);
		if(ctx.noMoreTokens()) {
			return leaf;
		}
		
		Token token = ctx.currentToken();
		if(! token.isSeparator(".")) {
			return leaf;
		}
		
		ctx.nextToken();
		Symbol propName = symbol(ctx, true);
		
		if(! ctx.hasMoreTokens()) {
			return new PropertyAccess(leaf, propName);
		}
		
		token = ctx.currentToken();
		// Method call
		if(! token.isSeparator("[")) {
			return new PropertyAccess(leaf, propName);
		}
		
		ctx.nextToken();
		List<Expression> expressions = expressions(ctx);
		token = ctx.currentToken();
		
		if(! token.isSeparator("]")) {
			throw new RuntimeException("Expected ]");
		}
		
		return new MethodCall(leaf, propName, expressions);
	}
	
	private Expression leaf(ParserContext ctx) {
		Expression ident = symbol(ctx, false);
		if(ident != null) {
			return ident;
		}
		
		Token token = ctx.currentToken();
		if(token.isSeparator("(")) {
			ctx.nextToken();
			Expression internal = expression(ctx);
			token = ctx.currentToken();
			if(! token.isSeparator(")")) {
				throw new RuntimeException("Parse failed exception: expected )");
			}
			ctx.nextToken();
			return internal;
		}
		
		if(token.type == TokenType.STRING) {
			ctx.nextToken();
			return new StringLiteral(token.value);
		}
		
		if(token.type == TokenType.NUMBER) {
			ctx.nextToken();
			return new IntegerLiteral(token.value);
		}
		
		if(token.isSeparator("#")) {
			ctx.nextToken();
			
			token = ctx.currentToken();
			if(token.isSymbol("t")) {
				return new BooleanLiteral(true);
			} else if(token.isSymbol("f")) {
				return new BooleanLiteral(false);
			} else if(token.isSeparator("[")) {
				// List
			}
		}
		
		throw new RuntimeException("Parse failed exception: expected simple expression");
	}
	
	private Symbol symbol(ParserContext ctx, boolean strict) {
		Token token = ctx.currentToken();
		if(token.isSymbol()) {
			ctx.nextToken();
			return token.asSymbol();
		}
		
		if(strict) {
			throw new RuntimeException("Parse failed exception: expected identifier");
		} else {
			return null;
		}
	}
}
