package org.still.parser;

public class CaseAlternative {
	public final Expression predicate;
	public final Expression consequent;
	
	public CaseAlternative(Expression predicate, Expression consequent) {
		this.predicate = predicate;
		this.consequent = consequent;
	}
}
