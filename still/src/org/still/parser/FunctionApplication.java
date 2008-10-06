package org.still.parser;

import java.util.ArrayList;
import java.util.List;

import org.still.Function;
import org.still.RuntimeContext;

public class FunctionApplication implements Expression {
	private final Expression target;
	private final List<Expression> arguments;
	
	public FunctionApplication(Expression target, List<Expression> arguments) {
		this.target = target;
		this.arguments = arguments;
	}

	public Object eval(RuntimeContext ctx) {
		Object targetObj = target.eval(ctx);
		if(! (targetObj instanceof Function)) {
			throw new RuntimeException("Expected function");
		}
		
		List<Object> argumentsObj = new ArrayList<Object>();
		for(Expression expr : arguments) {
			argumentsObj.add(expr.eval(ctx));
		}
		
		return ((Function) targetObj).apply(argumentsObj);
	}

	@Override
	public String toString() {
		return target.toString() + arguments;
	}
}
