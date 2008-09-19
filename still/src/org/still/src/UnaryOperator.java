package org.still.src;

import org.still.RuntimeContext;
import org.still.obj.StillObject;

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

	public StillObject eval(RuntimeContext ctx) {
		throw new UnsupportedOperationException();
	}
}
