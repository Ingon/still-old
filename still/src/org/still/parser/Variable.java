package org.still.parser;

import org.still.RuntimeContext;

public class Variable implements Expression {
	private final Symbol name;
	
	public Variable(String name) {
		this.name = Symbol.get(name);
	}

	public Object eval(RuntimeContext runtime) {
		return runtime.get(name);
	}

	@Override
	public String toString() {
		return name.value;
	}
}
