package org.still.src;

import java.util.ArrayList;
import java.util.List;

import org.still.RuntimeContext;
import org.still.obj.CallableStillObject;
import org.still.obj.StillObject;

public class MethodCall implements Expression {
	private final boolean log = false;
	public final Expression object;
	public final Symbol property;
	public final List<Expression> expressions;
	
	public MethodCall(Expression object, Symbol property, List<Expression> expressions) {
		this.object = object;
		this.property = property;
		this.expressions = expressions;
	}

	@Override
	public String toString() {
		return object + "." + property + "" + expressions;
	}

	public StillObject eval(RuntimeContext ctx) {
		if(log) {
			System.out.println("::Call: " + this);
		}
		StillObject targetObject = object.eval(ctx);
		StillObject target = targetObject.get(property);
		if(target == null) {
			throw new RuntimeException("Method not found (" + property + ")");
		}
		if(! (target instanceof CallableStillObject)) {
			throw new RuntimeException("Not a method! (" + property + ")");
		}
		CallableStillObject rtarget = (CallableStillObject) target;
		
		List<StillObject> params = new ArrayList<StillObject>();
		for(Expression exp : expressions) {
			params.add(exp.eval(ctx));
		}
		
		if(log) {
			System.out.println("::This: (" + this + ") " + targetObject);
			System.out.println("::With: (" + this + ") " + params);
		}
		StillObject result = rtarget.apply(targetObject, params);
		if(log) {
			System.out.println("::Retu: (" + this + ") " + result);
		}
		return result;
	}
}
