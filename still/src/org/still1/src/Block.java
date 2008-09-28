package org.still1.src;

import java.util.List;

import org.still1.RuntimeContext;
import org.still1.obj.StillObject;

public class Block implements Expression {

	public final List<Statement> statements;
	
	public Block(List<Statement> statements) {
		this.statements = statements;
	}

	@Override
	public StillObject eval(RuntimeContext ctx) {
		RuntimeContext newCtx = ctx.childContext();
		StillObject result = null;
		for(Statement stmt : statements) {
			result = stmt.eval(newCtx);
		}
		return result;
	}
}
