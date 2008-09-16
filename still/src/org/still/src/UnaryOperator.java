package org.still.src;

public class UnaryOperator implements Expression {
	public final String operator;
	public final Expression expression;
	
	public UnaryOperator(Expression expression, String operator) {
		this.expression = expression;
		this.operator = operator;
	}

	@Override
	public String toString() {
		return "[ OP(" + operator + ") " + expression + "]";
	}
}
