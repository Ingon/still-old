package org.still1.src;

import java.util.List;

import org.still1.RuntimeContext;
import org.still1.obj.StillFunction;
import org.still1.obj.StillObject;

public class Function implements Expression {

	public final List<Symbol> parameters;
	public final Expression body;
	
	public Function(List<Symbol> parameters, Expression body) {
		this.parameters = parameters;
		this.body = body;
	}

	public StillObject eval(RuntimeContext ctx) {
		if(body instanceof Block) {
			return new StillFunction(ctx, parameters, ((Block) body).statements);
		}
		return new StillFunction(ctx, parameters, body);
	}
	
}
