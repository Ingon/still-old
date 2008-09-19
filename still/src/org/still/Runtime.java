package org.still;

import org.still.src.Expression;

public class Runtime {
	public Object eval(Expression exp) {
		return this.eval(new RuntimeContext(), exp);
	}

	public Object eval(RuntimeContext ctx, Expression exp) {
		System.out.println("::Eval: " + exp);
		return exp.eval(ctx);
	}
}
