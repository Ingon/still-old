package org.still1.src;

import org.still1.RuntimeContext;
import org.still1.RuntimeSupport;
import org.still1.obj.StillObject;

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
