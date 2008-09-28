package org.still1;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.still1.obj.CallableStillObject;
import org.still1.obj.JavaStillObject;
import org.still1.obj.PrototypeStillObject;
import org.still1.obj.StillFunction;
import org.still1.obj.StillObject;
import org.still1.src.Expression;
import org.still1.src.Statement;
import org.still1.src.Symbol;

public class RuntimeSupport {
	public static RuntimeContext initDefault() {
		final RuntimeContext ctx = new RuntimeContext();
		ctx.set(Symbol.get("__runtime-suport"), new StillObject() {
			@Override
			public StillObject get(final Symbol name) {
				return new CallableStillObject() {
					@Override
					public StillObject apply(StillObject thisRef, List<StillObject> params) {
						StillObject value = params.get(0);
						if(name.value.equals("post-wrap-integer")) {
							return postWrapInteger((JavaStillObject) value);
						}
						if(name.value.equals("post-wrap-string")) {
							return postWrapString((JavaStillObject) value);
						}
						if(name.value.equals("post-wrap-boolean")) {
							if(! (value instanceof JavaStillObject)) {
								return value;
							}
							return postWrapBoolean((JavaStillObject) value);
						}
						throw new RuntimeException("Unknown method call.");
					}

					@Override
					public StillObject get(Symbol name) {
						throw new UnsupportedOperationException();
					}

					@Override
					public StillObject set(Symbol name, StillObject obj) {
						throw new UnsupportedOperationException();
					}};
			}

			@Override
			public StillObject set(Symbol name, StillObject obj) {
				throw new UnsupportedOperationException();
			}});
		return ctx;
	}
	
	public static StillObject newInteger(BigInteger value) {
		JavaStillObject wrappedValue = new JavaStillObject(value);
		return postWrapInteger(wrappedValue);
	}

	private static final Expression INT_ADD_EXPRESSION = Context.get().parser.parseExpression("(this.__wrap-value).add[other.__wrap-value]");
	private static final Expression INT_SUB_EXPRESSION = Context.get().parser.parseExpression("(this.__wrap-value).subtract[other.__wrap-value]");
	private static final Expression INT_MUL_EXPRESSION = Context.get().parser.parseExpression("(this.__wrap-value).multiply[other.__wrap-value]");
	private static final Expression INT_DIV_EXPRESSION = Context.get().parser.parseExpression("(this.__wrap-value).divide[other.__wrap-value]");
	private static final Expression INT_EQ_EXPRESSION = Context.get().parser.parseExpression("(this.__wrap-value).equals[other.__wrap-value]");
	private static final Expression INT_NEQ_EXPRESSION = Context.get().parser.parseExpression("! (this.__wrap-value).equals[other.__wrap-value]");
	private static final Expression INT_COMPARE_EXPRESSION = Context.get().parser.parseExpression("(this.__wrap-value).compareTo[other.__wrap-value]");
	private static final Expression INT_LESS_EXPRESSION = Context.get().parser.parseExpression("(this <=> other) = -1");
	private static final Expression INT_BIG_EXPRESSION = Context.get().parser.parseExpression("(this <=> other) = 1");
	private static final Expression INT_TO_STRING = Context.get().parser.parseExpression("this.__wrap-value");
	
	private static StillObject postWrapInteger(JavaStillObject wrappedValue) {
		StillObject obj = new PrototypeStillObject();
		obj.set(Symbol.get("__wrap-value"), wrappedValue);
		
		List<Symbol> params = new ArrayList<Symbol>();
		params.add(Symbol.get("other"));
		
		RuntimeContext rootCtx = Context.get().rootCtx;
		
		obj.set(Symbol.get("+"), new StillFunction(rootCtx, params, INT_ADD_EXPRESSION));
		obj.set(Symbol.get("add"), new StillFunction(rootCtx, params, INT_ADD_EXPRESSION));

		obj.set(Symbol.get("-"), new StillFunction(rootCtx, params, INT_SUB_EXPRESSION));
		obj.set(Symbol.get("sub"), new StillFunction(rootCtx, params, INT_SUB_EXPRESSION));
		obj.set(Symbol.get("substract"), new StillFunction(rootCtx, params, INT_SUB_EXPRESSION));
		
		obj.set(Symbol.get("*"), new StillFunction(rootCtx, params, INT_MUL_EXPRESSION));
		obj.set(Symbol.get("mul"), new StillFunction(rootCtx, params, INT_MUL_EXPRESSION));
		obj.set(Symbol.get("multiply"), new StillFunction(rootCtx, params, INT_MUL_EXPRESSION));
		
		obj.set(Symbol.get("/"), new StillFunction(rootCtx, params, INT_DIV_EXPRESSION));
		obj.set(Symbol.get("div"), new StillFunction(rootCtx, params, INT_DIV_EXPRESSION));
		obj.set(Symbol.get("divide"), new StillFunction(rootCtx, params, INT_DIV_EXPRESSION));
		
		obj.set(Symbol.get("="), new StillFunction(rootCtx, params, INT_EQ_EXPRESSION));
		obj.set(Symbol.get("<>"), new StillFunction(rootCtx, params, INT_NEQ_EXPRESSION));
		obj.set(Symbol.get("<=>"), new StillFunction(rootCtx, params, INT_COMPARE_EXPRESSION));
		obj.set(Symbol.get("<"), new StillFunction(rootCtx, params, INT_LESS_EXPRESSION));
		obj.set(Symbol.get(">"), new StillFunction(rootCtx, params, INT_BIG_EXPRESSION));
		
		obj.set(Symbol.get("to-string"), new StillFunction(rootCtx, Collections.<Symbol>emptyList(), INT_TO_STRING));
		
		return obj;
	}
	
	public static StillObject newString(String value) {
		JavaStillObject wrappedValue = new JavaStillObject(value);
		return postWrapString(wrappedValue);
	}

	private static final Expression STR_CONCAT_EXPRESSION = Context.get().parser.parseExpression("(this.__wrap-value).concat[other.__wrap-value]");
	private static final Expression STR_TO_STRING = Context.get().parser.parseExpression("this.__wrap-value");

	private static StillObject postWrapString(JavaStillObject wrappedValue) {
		StillObject obj = new PrototypeStillObject();
		obj.set(Symbol.get("__wrap-value"), wrappedValue);
		
		List<Symbol> params = new ArrayList<Symbol>();
		params.add(Symbol.get("other"));

		RuntimeContext rootCtx = Context.get().rootCtx;
		
		obj.set(Symbol.get("+"), new StillFunction(rootCtx, params, STR_CONCAT_EXPRESSION));
		obj.set(Symbol.get("concat"), new StillFunction(rootCtx, params, STR_CONCAT_EXPRESSION));

		obj.set(Symbol.get("to-string"), new StillFunction(rootCtx, Collections.<Symbol>emptyList(), STR_TO_STRING));
		
		return obj;
	}
	
	private static final Expression BOOL_COMPARE = Context.get().parser.parseExpression("(this.__wrap-value).compareTo[other.__wrap-value]");
	private static final Expression BOOL_EQ = Context.get().parser.parseExpression("(this <=> other) = 0");
	private static final Expression BOOL_NEQ = Context.get().parser.parseExpression("(this <=> other) <> 0");
	private static final List<Statement> BOOL_NOT = Context.get().parser.parseProgram("case when (this) #f otherwise #t");
	private static final List<Statement> BOOL_TO_STRING = Context.get().parser.parseProgram("case when (this) '#t' otherwise '#f'");
	
	private static StillObject BOOL_TRUE = postWrapBoolean(new JavaStillObject(true));
	private static StillObject BOOL_FALSE = postWrapBoolean(new JavaStillObject(false));
	
	public static boolean isTrue(StillObject obj) {
		return obj == BOOL_TRUE;
	}

	public static boolean isFalse(StillObject obj) {
		return obj == BOOL_FALSE;
	}
	
	public static StillObject newBoolean(boolean value) {
		if(value) {
			return BOOL_TRUE;
		} else {
			return BOOL_FALSE;
		}
	}
	
	private static StillObject postWrapBoolean(JavaStillObject wrappedValue) {
		StillObject obj = new PrototypeStillObject();
		obj.set(Symbol.get("__wrap-value"), wrappedValue);
		
		List<Symbol> params = new ArrayList<Symbol>();
		params.add(Symbol.get("other"));
		
		RuntimeContext rootCtx = Context.get().rootCtx;
		
		obj.set(Symbol.get("="), new StillFunction(rootCtx, params, BOOL_EQ));
		obj.set(Symbol.get("<>"), new StillFunction(rootCtx, params, BOOL_NEQ));
		obj.set(Symbol.get("<=>"), new StillFunction(rootCtx, params, BOOL_COMPARE));
		obj.set(Symbol.get("!"), new StillFunction(rootCtx, Collections.<Symbol>emptyList(), BOOL_NOT));
		
		obj.set(Symbol.get("to-string"), new StillFunction(rootCtx, Collections.<Symbol>emptyList(), BOOL_TO_STRING));
		
		return obj;
	}
	
	public static StillObject wrap(Object javaValue) {
		if(javaValue instanceof StillObject) {
			return (StillObject) javaValue;
		}
		if(javaValue instanceof Integer) {
			return newInteger(BigInteger.valueOf((Integer) javaValue));
		}
		if(javaValue instanceof BigInteger) {
			return newInteger((BigInteger) javaValue);
		}
		if(javaValue instanceof Boolean) {
			return newBoolean((Boolean) javaValue);
		}
		if(javaValue instanceof String) {
			return newString((String) javaValue);
		}
		return new JavaStillObject(javaValue);
	}
}
