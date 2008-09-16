package org.still.src;

import org.still.Symbol;


public class BinaryOperator implements Expression {
	public final Expression left;
	public final Symbol operator;
	public final Expression right;
	
	public BinaryOperator(Expression left, Symbol operator, Expression right) {
		this.left = left;
		this.operator = operator;
		this.right = right;
	}

	@Override
	public String toString() {
		return "[ " + left + " OP(" + operator + ") " + right + " ]";
	}
}
