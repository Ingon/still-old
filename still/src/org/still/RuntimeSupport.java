package org.still;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.still.obj.JavaStillObject;
import org.still.obj.PrototypeStillObject;
import org.still.obj.StillFunction;
import org.still.obj.StillObject;
import org.still.src.Expression;
import org.still.src.Symbol;

public class RuntimeSupport {
	private static final Expression INT_ADD_EXPRESSION = Context.get().parser.parseExpression("(this.__wrapValue).add[other.__wrapValue]");
	private static final Expression INT_MUL_EXPRESSION = Context.get().parser.parseExpression("(this.__wrapValue).multiply[other.__wrapValue]");
	
	public static StillObject newInteger(RuntimeContext ctx, BigInteger value) {
		JavaStillObject wrappedValue = new JavaStillObject(value);
		
		StillObject obj = new PrototypeStillObject();
		obj.set(Symbol.get("__wrapValue"), wrappedValue);
		
		List<Symbol> params = new ArrayList<Symbol>();
		params.add(Symbol.get("other"));
		
		obj.set(Symbol.get("+"), new StillFunction(ctx, params, INT_ADD_EXPRESSION));
		obj.set(Symbol.get("add"), new StillFunction(ctx, params, INT_ADD_EXPRESSION));
		
		obj.set(Symbol.get("*"), new StillFunction(ctx, params, INT_MUL_EXPRESSION));
		obj.set(Symbol.get("mul"), new StillFunction(ctx, params, INT_MUL_EXPRESSION));
		obj.set(Symbol.get("multiply"), new StillFunction(ctx, params, INT_MUL_EXPRESSION));
		
		return obj;
	}
	
	private static final Expression STR_CONCAT_EXPRESSION = Context.get().parser.parseExpression("(this.__wrapValue).concat[other.__wrapValue]");
	
	public static StillObject newString(RuntimeContext ctx, String value) {
		JavaStillObject wrappedValue = new JavaStillObject(value);

		StillObject obj = new PrototypeStillObject();
		obj.set(Symbol.get("__wrapValue"), wrappedValue);
		
		List<Symbol> params = new ArrayList<Symbol>();
		params.add(Symbol.get("other"));

		obj.set(Symbol.get("+"), new StillFunction(ctx, params, STR_CONCAT_EXPRESSION));
		obj.set(Symbol.get("concat"), new StillFunction(ctx, params, STR_CONCAT_EXPRESSION));
		
		return obj;
	}
}
