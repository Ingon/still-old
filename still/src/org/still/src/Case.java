package org.still.src;

import java.util.List;

import org.still.RuntimeContext;
import org.still.RuntimeSupport;
import org.still.obj.StillObject;

public class Case implements Statement {
	public final List<CaseWhen> alternatives;
	public final Expression otherwise;

	public Case(List<CaseWhen> alternatives, Expression otherwise) {
		this.alternatives = alternatives;
		this.otherwise = otherwise;
	}

	@Override
	public StillObject eval(RuntimeContext ctx) {
		for(CaseWhen when : alternatives) {
			StillObject condResult = when.condition.eval(ctx);
			if(RuntimeSupport.isTrue(condResult)) {
				return when.consequent.eval(ctx);
			}
		}
		
		if(otherwise == null) {
			throw new RuntimeException("Case did not match any case");
		}
		
		return otherwise.eval(ctx);
	}
}
