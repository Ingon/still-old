package org.still.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.still.lexer.Lexer;
import org.still.lexer.Token;

public class Parser implements Iterator<SourceElement> {
	private final Lexer lexer;
	private Token current;
	
	public Parser(Lexer lexer) {
		this.lexer = lexer;
		nextToken();
	}

	public boolean hasNext() {
		return lexer.hasNext();
	}

	public SourceElement next() {
		return sourceElement();
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

	private void nextToken() {
		do {
			this.current = lexer.next();
		} while(this.current != null && this.current.isWhiteSpace());
	}
	
	private SourceElement sourceElement() {
		SourceElement element = null;
		
		if(element == null) {
			element = declaration();
		}
		
		if(element == null) {
			element = expression();
		}
		
		if(! current.isSeparator(";")) {
			throw new RuntimeException("Source elements end with ;");
		}
		if(lexer.hasNext()) {
			nextToken();
		}
		return element;
	}

	private Declaration declaration() {
		if(current.isName("let")) {
			nextToken();
			
			if(! current.isName()) {
				throw new RuntimeException("Expected variable");
			}
			
			String varName = current.value;
			nextToken();
			
			if(! current.isOperator("=")) {
				throw new RuntimeException("Expected '=' sign");
			}
			nextToken();
			
			Expression value = expression();
			return new LetDeclaration(varName, value);
		}
		
		return null;
	}
	
	private Expression expression() {
		Expression left = operand();
		if(! lexer.hasNext()) {
			return left;
		}
		
		if(! current.isOperator()) {
			return left;
		}
		
		Expression operator = new Variable(current.value);
		nextToken();
		
		Expression right = expression();
		return new FunctionApplication(operator, Arrays.asList(left, right));
	}

	private Expression operand() {
		Expression leaf = leaf();
		if(current == null) {
			return leaf;
		}
		
		if(! current.isSeparator("[")) {
			return leaf;
		}
		
		nextToken();
		List<Expression> arguments = arguments();
		
		if(current == null || ! current.isSeparator("]")) {
			throw new RuntimeException("Expected end of arguments list");
		}
		if(lexer.hasNext()) {
			nextToken();
		}
		
		return new FunctionApplication(leaf, arguments);
	}

	private List<Expression> arguments() {
		List<Expression> arguments = new ArrayList<Expression>();
		Expression expr = expression();
		if(expr == null) {
			return arguments;
		}
		arguments.add(expr);
		
		while(current.isSeparator(",")) {
			nextToken();
			expr = expression();
			if(expr == null) {
				throw new RuntimeException("Expected expression");
			}
			arguments.add(expr);
		}
		
		return arguments;
	}
	
	private Expression leaf() {
		if(current.isName("function")) {
			nextToken();
			return functionDefinition();
		}
		
		if(current.isName()) {
			Expression result = new Variable(current.value);
			nextToken();
			return result;
		}
		
		if(current.isNumber()) {
			Expression result = new IntegerLiteral(current.value);
			nextToken();
			return result;
		}

		throw new RuntimeException("Unknwon token: " + current);
	}

	private Expression functionDefinition() {
		if(! current.isSeparator("[")) {
			throw new RuntimeException("Expected parameters list");
		}
		nextToken();
		List<Symbol> params = parameters();
		if(! current.isSeparator("]")) {
			throw new RuntimeException("Expected parameters list end");
		}
		nextToken();
		
		List<SourceElement> body = new ArrayList<SourceElement>();
		while(current != null && ! current.isName("end")) {
			body.add(sourceElement());
		}
		nextToken();
		
		if(current == null) {
			throw new RuntimeException("Unfinished function definition");
		}
		
		return new LambdaExpression(params, body);
	}

	private List<Symbol> parameters() {
		List<Symbol> parameters = new ArrayList<Symbol>();
		if(! current.isName()) {
			return parameters;
		}
		parameters.add(Symbol.get(current.value));
		nextToken();
		
		while(current.isSeparator(",")) {
			nextToken();
			if(! current.isName()) {
				throw new RuntimeException("Expected variable name");
			}
			parameters.add(Symbol.get(current.value));
			nextToken();
		}
		
		return parameters;
	}
}
