package org.still.src;

import org.still.RuntimeContext;
import org.still.obj.StillObject;

public class Let implements Statement {

	public final Symbol name;
	public final Expression value;
	
	public Let(Symbol name, Expression value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public StillObject eval(RuntimeContext ctx) {
		StillObject realValue = value.eval(ctx);
		ctx.set(name, realValue);
		return realValue;
	}
}
