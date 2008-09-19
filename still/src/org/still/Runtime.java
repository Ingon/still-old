package org.still;

import org.still.src.Expression;

public class Runtime {
	public Object eval(Expression exp) {
		System.out.println("::Eval: " + exp);
		return exp.eval(new RuntimeContext());
	}
}
