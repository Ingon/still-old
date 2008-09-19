package org.still.obj;

import java.util.Arrays;
import java.util.List;

import org.still.RuntimeContext;
import org.still.src.Expression;
import org.still.src.Symbol;

public class StillFunction extends PrototypeStillObject implements CallableStillObject {

	private final RuntimeContext ctx;
	private final List<Symbol> parameters;
	private final List<Expression> expressions;
	
	public StillFunction(RuntimeContext ctx, List<Symbol> params, final Expression expression) {
		this(ctx, params, Arrays.asList(expression));
	}
	
	public StillFunction(RuntimeContext ctx, List<Symbol> params, List<Expression> expressions) {
		this.ctx = ctx;
		this.parameters = params;
		this.expressions = expressions;
	}

	@Override
	public StillObject apply(StillObject thisRef, List<StillObject> arguments) {
		if(parameters.size() != arguments.size()) {
			throw new RuntimeException("Only exact arguments are supported");
		}
		
		RuntimeContext newCtx = ctx.childContext();
		for(int i = 0, n = parameters.size(); i < n; i++) {
			newCtx.set(parameters.get(i), arguments.get(i));
		}
		newCtx.set(Symbol.get("this"), thisRef);
		
		StillObject result = null;
		for(Expression expr : expressions) {
			result = expr.eval(newCtx);
		}
		return result;
	}
}
