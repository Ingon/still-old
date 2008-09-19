package org.still.src;

import java.util.ArrayList;
import java.util.List;

import org.still.RuntimeContext;
import org.still.obj.CallableStillObject;
import org.still.obj.StillObject;

public class MethodCall implements Expression {
	public final PropertyAccess property;
	public final List<Expression> expressions;
	
	public MethodCall(PropertyAccess property, List<Expression> expressions) {
		this.property = property;
		this.expressions = expressions;
	}

	@Override
	public String toString() {
		return "[M " + property + expressions + "]";
	}

	public StillObject eval(RuntimeContext ctx) {
		StillObject target = property.eval(ctx);
		if(! (target instanceof CallableStillObject)) {
			throw new RuntimeException("Not a method!");
		}
		CallableStillObject rtarget = (CallableStillObject) target;
		
		List<StillObject> params = new ArrayList<StillObject>();
		for(Expression exp : expressions) {
			params.add(exp.eval(ctx));
		}
		
		return rtarget.apply(params);
	}
}
