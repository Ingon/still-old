package org.still.src;

import org.still.Symbol;

public class UnaryOperator implements Expression {
	public final Symbol operator;
	public final Expression expression;
	
	public UnaryOperator(Expression expression, Symbol operator) {
		this.expression = expression;
		this.operator = operator;
	}

	@Override
	public String toString() {
		return "[ OP(" + operator + ") " + expression + "]";
	}
}
