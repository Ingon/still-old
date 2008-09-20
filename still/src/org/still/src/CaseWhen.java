package org.still.src;

public class CaseWhen {
	public final Expression condition;
	public final Expression consequent;
	
	public CaseWhen(Expression condition, Expression consequent) {
		this.condition = condition;
		this.consequent = consequent;
	}
}
