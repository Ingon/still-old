package org.still.src;

public class PropertyAccess implements Expression {
	public final Expression object;
	public final Identifier property;
	
	public PropertyAccess(Expression object, Identifier property) {
		this.object = object;
		this.property = property;
	}

	@Override
	public String toString() {
		return "[P " + object + "." + property + "]";
	}
}
