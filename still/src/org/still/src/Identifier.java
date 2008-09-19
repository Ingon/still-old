package org.still.src;

import org.still.RuntimeContext;
import org.still.obj.StillObject;

public class Identifier implements Expression {
	public final Symbol value;
	
	public Identifier(Symbol value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "I(" + value + ")";
	}

	public StillObject eval(RuntimeContext ctx) {
		return ctx.get(value);
	}
}
