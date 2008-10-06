package org.still.parser;

import org.still.RuntimeContext;

public class LetDeclaration implements Declaration {

	public final Symbol name;
	public final Expression value;
	
	public LetDeclaration(String varName, Expression value) {
		this.name = Symbol.get(varName);
		this.value = value;
	}

	@Override
	public Object eval(RuntimeContext ctx) {
		ctx.add(name, value.eval(ctx));
		return name;
	}

	@Override
	public String toString() {
		return "let " + name.value + " = " + value;
	}
}
