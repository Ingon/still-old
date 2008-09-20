package org.still.src;

import org.still.RuntimeContext;
import org.still.RuntimeSupport;
import org.still.obj.StillObject;

public class While implements Statement {

	public final Expression condition;
	public final Expression body;
	
	public While(Expression condition, Expression body) {
		this.condition = condition;
		this.body = body;
	}

	@Override
	public StillObject eval(RuntimeContext ctx) {
		StillObject result = null;
		while(RuntimeSupport.isTrue(condition.eval(ctx))) {
			result = body.eval(ctx);
		}
		return result;
	}
}
