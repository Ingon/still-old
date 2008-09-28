package org.still1.src;

import org.still1.RuntimeContext;
import org.still1.obj.StillObject;

public class PropertyAccess implements Expression {
	public final Expression object;
	public final Symbol property;
	
	public PropertyAccess(Expression object, Symbol property) {
		this.object = object;
		this.property = property;
	}

	@Override
	public String toString() {
		return object + "." + property;
	}

	public StillObject eval(RuntimeContext ctx) {
		StillObject target = object.eval(ctx);
		return target.get(property);
	}
}
