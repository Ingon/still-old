package org.still1.src;

import java.util.ArrayList;
import java.util.List;

import org.still1.RuntimeContext;
import org.still1.obj.CallableStillObject;
import org.still1.obj.StillObject;

public class FunctionCall implements Expression {
	public final Expression function;
	public final List<Expression> expressions;

	public FunctionCall(Expression function, List<Expression> expressions) {
		this.function = function;
		this.expressions = expressions;
	}

	public StillObject eval(RuntimeContext ctx) {
		StillObject rfunction = function.eval(ctx);
		if(! (rfunction instanceof CallableStillObject)) {
			throw new RuntimeException("Not a function: " + rfunction);
		}
		CallableStillObject target = (CallableStillObject) rfunction;
		List<StillObject> params = new ArrayList<StillObject>();
		for(Expression exp : expressions) {
			params.add(exp.eval(ctx));
		}
		// TODO not ok for this ?!?!?
		StillObject result = target.apply(null, params);
		return result;
	}
}
