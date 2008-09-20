package org.still.src;

import org.still.RuntimeContext;
import org.still.RuntimeSupport;
import org.still.obj.StillObject;

public class BooleanLiteral implements Expression {

	public final boolean value;
	
	public BooleanLiteral(boolean value) {
		this.value = value;
	}

	public StillObject eval(RuntimeContext ctx) {
		return RuntimeSupport.newBoolean(value);
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
