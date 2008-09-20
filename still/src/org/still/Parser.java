package org.still;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.still.src.Block;
import org.still.src.BooleanLiteral;
import org.still.src.Case;
import org.still.src.CaseWhen;
import org.still.src.Expression;
import org.still.src.IntegerLiteral;
import org.still.src.Let;
import org.still.src.MethodCall;
import org.still.src.PropertyAccess;
import org.still.src.Statement;
import org.still.src.StringLiteral;
import org.still.src.Symbol;
import org.still.src.Token;
import org.still.src.TokenType;
import org.still.src.While;

public class Parser {
	public Expression parseExpression(String str) {
		List<Token> tokens = Lexer.tokenize(str);
		System.out.println("::Lex : " + tokens);
		ParserContext ctx = new ParserContext(tokens);
		ctx.nextToken();
		return expression(ctx);
	}

	public List<Statement> parseProgram(String str) {
		List<Token> tokens = Lexer.tokenize(str);
		System.out.println("::Lex : " + tokens);
		ParserContext ctx = new ParserContext(tokens);
		ctx.nextToken();
		return statements(ctx);
	}
	
	private List<Statement> statements(ParserContext ctx) {
		List<Statement> result = new ArrayList<Statement>();
		Statement current = statement(ctx);
		if(current == null) {
			return result;
		}
		
		result.add(current);
		while(ctx.hasMoreTokens() && ctx.currentToken().isSeparator(";")) {
			ctx.nextToken();
			current = statement(ctx);
			if(current == null) {
				throw new RuntimeException("Statement expected!");
			}
			result.add(current);
		}
		
		return result;
	}
	
	private Statement statement(ParserContext ctx) {
		if(ctx.currentToken().isSymbol("case")) {
			ctx.nextToken();
			
			List<CaseWhen> alternatives = new ArrayList<CaseWhen>();
			while(ctx.currentToken().isSymbol("when")) {
				ctx.nextToken();
				
				Expression condition = brackets(ctx, true);
				Expression consequent = expression(ctx);
				
				alternatives.add(new CaseWhen(condition, consequent));
			}
			
			if(ctx.currentToken().isSymbol("otherwise")) {
				ctx.nextToken();
				
				Expression consequent = expression(ctx);
				return new Case(alternatives, consequent);
			}
			
			return new Case(alternatives, null);
		}
		
		if(ctx.currentToken().isSymbol("while")) {
			ctx.nextToken();
			
			Expression condition = brackets(ctx, true);
			Expression body = expression(ctx);
			
			return new While(condition, body);
		}
		
		if(ctx.currentToken().isSymbol("let")) {
			ctx.nextToken();
			
			Symbol name = symbol(ctx, true);
			
			if(! (ctx.currentToken().isBinary() && ctx.currentToken().is("="))) {
				throw new RuntimeException("Expected =");
			}
			ctx.nextToken();
			
			Expression value = expression(ctx);
			
			return new Let(name, value);
		}
		
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
		Expression block = block(ctx, false);
		if(block != null) {
			return block;
		}
		
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
		
		Expression brackets = brackets(ctx, false);
		if(brackets != null) {
			return brackets;
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
		
		if(token.isSeparator("#")) {
			ctx.nextToken();
			
			token = ctx.currentToken();
			if(token.isSymbol("t")) {
				ctx.nextToken();
				return new BooleanLiteral(true);
			} else if(token.isSymbol("f")) {
				ctx.nextToken();
				return new BooleanLiteral(false);
			} else if(token.isSeparator("[")) {
				throw new UnsupportedOperationException();
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
	
	private Expression brackets(ParserContext ctx, boolean strict) {
		Token token = ctx.currentToken();
		if(! token.isSeparator("(")) {
			if(strict) {
				throw new RuntimeException("Expected (expression)");
			} else {
				return null;
			}
		}
		
		ctx.nextToken();
		Expression internal = expression(ctx);
		token = ctx.currentToken();
		if(! token.isSeparator(")")) {
			throw new RuntimeException("Parse failed exception: expected )");
		}
		ctx.nextToken();
		return internal;
	}
	
	private Block block(ParserContext ctx, boolean strict) {
		Token token = ctx.currentToken();
		if(! token.isSeparator("{")) {
			if(strict) {
				throw new RuntimeException("Expected block");
			} else {
				return null;
			}
		}
		ctx.nextToken();
		
		List<Statement> statements = statements(ctx);
		token = ctx.currentToken();
		if(! token.isSeparator("}")) {
			throw new RuntimeException("Parse failed exception: expected }");
		}
		return new Block(statements);
	}
}
