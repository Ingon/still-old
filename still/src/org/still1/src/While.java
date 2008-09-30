package org.still1.src;

import org.still1.RuntimeContext;
import org.still1.RuntimeSupport;
import org.still1.obj.StillObject;

public class While implements Statement {

	public final Expression condition;
	public final Expression body;
	
	public While(Expression condition, Expression body) {
		this.condition = condition;
		this.body = body;
	}

	public StillObject eval(RuntimeContext ctx) {
		StillObject result = null;
		while(RuntimeSupport.isTrue(condition.eval(ctx))) {
			result = body.eval(ctx);
		}
		return result;
	}
}
