package org.still.src;

import java.util.ArrayList;
import java.util.List;

import org.still.RuntimeContext;
import org.still.obj.CallableStillObject;
import org.still.obj.StillObject;

public class FunctionCall implements Expression {
	public final Expression function;
	public final List<Expression> expressions;

	public FunctionCall(Expression function, List<Expression> expressions) {
		this.function = function;
		this.expressions = expressions;
	}

	@Override
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
