package org.still1.src;

import org.still1.RuntimeContext;
import org.still1.obj.StillObject;

public class Let implements Statement, Declaration {

	public final Symbol name;
	public final Expression value;
	
	public Let(Symbol name, Expression value) {
		this.name = name;
		this.value = value;
	}

	public StillObject eval(RuntimeContext ctx) {
		StillObject realValue = value.eval(ctx);
		ctx.set(name, realValue);
		return realValue;
	}
}
