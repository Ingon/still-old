package org.still.parser;

import org.still.RuntimeContext;

public class IntegerLiteral implements Expression {

	public final Integer value;
	
	public IntegerLiteral(String valueStr) {
		this.value = new Integer(valueStr);
	}
	
	public Object eval(RuntimeContext ctx) {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
