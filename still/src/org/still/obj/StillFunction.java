package org.still.obj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.still.RuntimeContext;
import org.still.src.Expression;
import org.still.src.Statement;
import org.still.src.Symbol;

public class StillFunction extends PrototypeStillObject implements CallableStillObject {

	private final RuntimeContext ctx;
	private final List<Symbol> parameters;
	private final List<Statement> expressions;
	
	public StillFunction(RuntimeContext ctx, List<Symbol> params, final Expression expression) {
		this.ctx = ctx;
		this.parameters = params;
		this.expressions = new ArrayList<Statement>();
		this.expressions.add(expression);
	}
	
	public StillFunction(RuntimeContext ctx, List<Symbol> params, List<Statement> expressions) {
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
		newCtx.set(Symbol.get("thisCtx"), newCtx);
		
		StillObject result = null;
		for(Statement expr : expressions) {
			result = expr.eval(newCtx);
		}
		return result;
	}
}
