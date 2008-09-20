package org.still;

import java.util.List;

import org.still.obj.StillObject;
import org.still.src.Expression;
import org.still.src.Statement;

public class Runtime {
	public StillObject eval(Expression exp) {
		return this.eval(Context.get().rootCtx, exp);
	}

	public StillObject eval(RuntimeContext ctx, Expression exp) {
		System.out.println("::Eval: " + exp);
		return exp.eval(ctx);
	}

	public StillObject eval(List<Statement> statements) {
		return this.eval(Context.get().rootCtx, statements);
	}
	
	public StillObject eval(RuntimeContext ctx, List<Statement> statements) {
		StillObject val = null;
		for(Statement stmt : statements) {
			System.out.println("::Eval: " + stmt);
			val = stmt.eval(ctx);
		}
		return val;
	}
	
}
