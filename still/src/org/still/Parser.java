package org.still;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.still.src.Block;
import org.still.src.BodyFragment;
import org.still.src.BooleanLiteral;
import org.still.src.Case;
import org.still.src.CaseWhen;
import org.still.src.Constituent;
import org.still.src.Declaration;
import org.still.src.Definition;
import org.still.src.Expression;
import org.still.src.Function;
import org.still.src.FunctionCall;
import org.still.src.IntegerLiteral;
import org.still.src.Let;
import org.still.src.ListFragment;
import org.still.src.Macro;
import org.still.src.MethodCall;
import org.still.src.PropertyAccess;
import org.still.src.SourceRecord;
import org.still.src.Statement;
import org.still.src.StringLiteral;
import org.still.src.Symbol;
import org.still.src.Token;
import org.still.src.TokenType;
import org.still.src.While;

public class Parser {
	public static final Map<Symbol, Macro> DEF_BODY_MACROS = new HashMap<Symbol, Macro>();
	public static final Map<Symbol, Macro> DEF_LIST_MACROS = new HashMap<Symbol, Macro>();
	
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
	
	public SourceRecord parserSource(String src) {
		List<Token> tokens = Lexer.tokenize(src);
		System.out.println("::Lex : " + tokens);
		ParserContext ctx = new ParserContext(tokens);
		return new SourceRecord(constituents(ctx));
	}
	
	private List<Constituent> constituents(ParserContext ctx) {
		List<Constituent> result = new ArrayList<Constituent>();
		Constituent current = constituent(ctx);
		if(current == null) {
			throw new RuntimeException("Parse failed, expected at least one constituent");
		}
		result.add(current);
		
		while(ctx.hasMoreTokens() && ctx.currentToken().isSeparator(";")) {
			ctx.nextToken();
			
			current = constituent(ctx);
			if(current == null) {
				continue;
			}
			result.add(current);
		}
		
		if(ctx.hasMoreTokens()) {
			throw new RuntimeException("Parse failed, expected constituent");
		}
		
		return result;
	}
	
	private Constituent constituent(ParserContext ctx) {
		Constituent current = definition(ctx);
		if(current != null) {
			return current;
		}
		current = declaration(ctx);
		if(current != null) {
			return current;
		}
		return expression(ctx);
	}

	private boolean isMacroName(ParserContext ctx) {
		Token token = ctx.currentToken();
		if(! token.isSymbol()) {
			throw new RuntimeException("Parse failed, expected symbol");
		}
		Symbol sym = token.asSymbol();
		return DEF_BODY_MACROS.containsKey(sym) || DEF_LIST_MACROS.containsKey(sym);
	}
	
	private Definition definition(ParserContext ctx) {
		if(! ctx.currentToken().isSymbol("def")) {
			return null;
		}
		ctx.nextToken();
		
		if(ctx.currentToken().isSymbol("macro")) {
			ctx.nextToken();
			return macroDefinition(ctx);
		}
		
		List<Symbol> modifiers = new ArrayList<Symbol>();
		while(! isMacroName(ctx)) {
			modifiers.add(ctx.currentToken().asSymbol());
			ctx.nextToken();
		}
		
		Symbol macroName = ctx.currentToken().asSymbol();
		ctx.nextToken();
		
		if(DEF_BODY_MACROS.containsKey(macroName)) {
			BodyFragment body = bodyFragment(ctx);
			Macro macro = DEF_BODY_MACROS.get(macroName);
			macro.apply(body);
		} else {
			ListFragment list = listFragment(ctx);
			Macro macro = DEF_LIST_MACROS.get(macroName);
			macro.apply(list);
		}
		
		if(! ctx.currentToken().isSymbol("end")) {
			throw new RuntimeException("Expected 'end' of macro call");
		}
		ctx.nextToken();
		
		throw new UnsupportedOperationException();
	}
	
	private Definition macroDefinition(ParserContext ctx) {
		throw new UnsupportedOperationException();
	}

	private BodyFragment bodyFragment(ParserContext ctx) {
		throw new UnsupportedOperationException();
	}

	private ListFragment listFragment(ParserContext ctx) {
		throw new UnsupportedOperationException();
	}

	private Declaration declaration(ParserContext ctx) {
		Token token = ctx.currentToken();
		if(token.isSymbol("let")) {
			ctx.nextToken();
			token = ctx.currentToken();
			
			if(token.isSymbol("handler")) {
				throw new UnsupportedOperationException();
			}
			
			Symbol sym = symbol(ctx, true);
			token = ctx.currentToken();
			if(! (token.isBinary() && token.is("="))) {
				throw new RuntimeException("Parse failed, expected =.");
			}
			Expression expr = expression(ctx);
			return new Let(sym, expr);
		} else if(token.isSymbol("local")) {
			ctx.nextToken();
			
			
		}
		
		return null;
	}
	
//	private Declaration function(ParserContext ctx) {
//		Token token = ctx.currentToken();
//		if(token.isSymbol("method")) {
//			ctx.nextToken();
//			token = ctx.currentToken();
//		}
//		
//		throw new UnsupportedOperationException();
//	}
	
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
		if(token.isSeparator(".")) {
			ctx.nextToken();
			return parseObjectAccess(ctx, leaf);
		} else if(token.isSeparator("[")) {
			// Fun call
			ctx.nextToken();
			List<Expression> expressions = expressions(ctx);
			token = ctx.currentToken();
			
			if(! token.isSeparator("]")) {
				throw new RuntimeException("Expected ]");
			}
			
			return new FunctionCall(leaf, expressions);
		}
		return leaf;
	}

	private Expression parseObjectAccess(ParserContext ctx, Expression leaf) {
		Token token;
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
		Expression fun = function(ctx);
		if(fun != null) {
			return fun;
		}
		
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
	
	private List<Symbol> symbols(ParserContext ctx) {
		List<Symbol> result = new ArrayList<Symbol>();
		Symbol currentSymbol = symbol(ctx, false);
		if(currentSymbol == null) {
			return result;
		}
		result.add(currentSymbol);
		
		while(ctx.currentToken().isSeparator(",")) {
			ctx.nextToken();
			currentSymbol = symbol(ctx, true);
			result.add(currentSymbol);
		}
		
		return result;
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
	
	private Function function(ParserContext ctx) {
		Token token = ctx.currentToken();
		if(! token.isSymbol("function")) {
			return null;
		}
		
		ctx.nextToken();
		token = ctx.currentToken();
		if(! token.isSeparator("[")) {
			throw new RuntimeException("Expected parameters list");
		}
		ctx.nextToken();
		
		List<Symbol> parameters = symbols(ctx);
		token = ctx.currentToken();
		
		if(! token.isSeparator("]")) {
			throw new RuntimeException("Expected parameters list end ]");
		}
		ctx.nextToken();
		
		Expression body = expression(ctx);
		return new Function(parameters, body);
	}
}
