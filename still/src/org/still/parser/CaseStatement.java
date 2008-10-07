package org.still.parser;

import java.util.List;

import org.still.RuntimeContext;

public class CaseStatement implements Expression {

	private final List<CaseAlternative> alternatives;
	
	public CaseStatement(List<CaseAlternative> alternatives) {
		this.alternatives = alternatives;
	}

	public Object eval(RuntimeContext ctx) {
		for(CaseAlternative alter : alternatives) {
			Object result = alter.predicate.eval(ctx);
			if(! (result instanceof Boolean)) {
				throw new RuntimeException("Expected to evaluate to boolean");
			}
			if(((Boolean) result).booleanValue()) {
				return alter.consequent.eval(ctx);
			}
		}
		
		throw new RuntimeException("No case matched");
	}
}
