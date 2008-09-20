package org.still.src;

import java.util.List;

import org.still.RuntimeContext;
import org.still.obj.StillObject;

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
