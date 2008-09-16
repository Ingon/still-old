package org.still.src;

public class BinaryOperator implements Expression {
	public final Expression left;
	public final String operator;
	public final Expression right;
	
	public BinaryOperator(Expression left, String operator, Expression right) {
		this.left = left;
		this.operator = operator;
		this.right = right;
	}

	@Override
	public String toString() {
		return "[ " + left + " OP(" + operator + ") " + right + " ]";
	}
}
