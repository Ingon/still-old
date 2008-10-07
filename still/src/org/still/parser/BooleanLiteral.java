package org.still.parser;

import org.still.RuntimeContext;

public class BooleanLiteral implements Expression {

	public final Boolean value;
	
	public BooleanLiteral(Boolean value) {
		this.value = value;
	}
	
	public Object eval(RuntimeContext ctx) {
		return value;
	}
}
