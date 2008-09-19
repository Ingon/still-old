package org.still.src;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.still.Context;
import org.still.RuntimeContext;
import org.still.obj.JavaStillObject;
import org.still.obj.PrototypeStillObject;
import org.still.obj.StillFunction;
import org.still.obj.StillObject;

public class IntegerLiteral implements Literal {
	public final Integer value;
	
	public IntegerLiteral(String value) {
		this.value = Integer.parseInt(value);
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	public StillObject eval(RuntimeContext ctx) {
//		return new JavaStillObject(new BigInteger(String.valueOf(value)));
		return create(ctx);
	}
	
	private static final Expression ADD_EXPRESSION = Context.get().parser.parseExpression("(this.__wrapValue).add[other.__wrapValue]");
	
	private StillObject create(RuntimeContext ctx) {
		JavaStillObject wrappedValue = new JavaStillObject(new BigInteger(String.valueOf(value)));
		
		StillObject obj = new PrototypeStillObject();
		obj.set(Symbol.get("__wrapValue"), wrappedValue);
		
		List<Symbol> params = new ArrayList<Symbol>();
		params.add(Symbol.get("other"));
		
		obj.set(Symbol.get("+"), new StillFunction(ctx, params, ADD_EXPRESSION));
		obj.set(Symbol.get("add"), new StillFunction(ctx, params, ADD_EXPRESSION));
		
		return obj;
	}
}
