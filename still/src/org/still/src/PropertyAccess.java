package org.still.src;

import org.still.RuntimeContext;
import org.still.obj.StillObject;

public class PropertyAccess implements Expression {
	public final Expression object;
	public final Identifier property;
	
	public PropertyAccess(Expression object, Identifier property) {
		this.object = object;
		this.property = property;
	}

	@Override
	public String toString() {
		return "[P " + object + "." + property + "]";
	}

	public StillObject eval(RuntimeContext ctx) {
		StillObject target = object.eval(ctx);
		return target.get(property.value);
	}
}
