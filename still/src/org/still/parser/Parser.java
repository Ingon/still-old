package org.still.parser;

import java.util.ArrayList;
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
		SourceElement element = expression();
		if(! current.isSeparator(";")) {
			throw new RuntimeException("Source elements end with ;");
		}
		return element;
	}

	private Expression expression() {
		return operand();
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
		if(current.isName()) {
			Expression result = new Variable(current.value);
			nextToken();
			return result;
		}

		throw new RuntimeException("Unknwon token: " + current);
	}
}
