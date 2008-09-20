package org.still;

import org.still.obj.StillObject;
import org.still.src.Expression;

public class Runtime {
	public StillObject eval(Expression exp) {
		return this.eval(Context.get().rootCtx, exp);
	}

	public StillObject eval(RuntimeContext ctx, Expression exp) {
		System.out.println("::Eval: " + exp);
		return exp.eval(ctx);
	}
}
