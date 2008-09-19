package org.still.src;

import java.util.List;

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
}
