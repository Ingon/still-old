package org.still.parser;

import java.util.List;

import org.still.Lambda;
import org.still.RuntimeContext;

public class LambdaExpression implements Expression {

	public final List<Symbol> params;
	public final List<SourceElement> body;
	
	public LambdaExpression(List<Symbol> params, List<SourceElement> body) {
		this.params = params;
		this.body = body;
	}

	public Object eval(RuntimeContext ctx) {
		return new Lambda(ctx, params, body);
	}
}
