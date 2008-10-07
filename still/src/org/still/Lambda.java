package org.still;

import java.util.List;

import org.still.parser.SourceElement;
import org.still.parser.Symbol;

public class Lambda implements Function {

	public final RuntimeContext ctx;
	public final List<Symbol> params;
	public final List<SourceElement> body;
	
	public Lambda(RuntimeContext ctx, List<Symbol> params, List<SourceElement> body) {
		this.ctx = ctx;
		this.params = params;
		this.body = body;
	}

	public Object apply(List<Object> arguments) {
		if(params.size() != arguments.size()) {
			throw new RuntimeException("Illegal number of arguments");
		}
		
		RuntimeContext childCtx = ctx.child();
		for(int i = 0, n = params.size(); i < n; i++) {
			childCtx.add(params.get(i), arguments.get(i));
		}
		
		Object result = null;
		for(SourceElement el : body) {
			result = el.eval(childCtx);
		}
		return result;
	}
}
