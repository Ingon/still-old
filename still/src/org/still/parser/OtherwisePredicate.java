package org.still.parser;

import org.still.RuntimeContext;

public class OtherwisePredicate implements Expression {
	public Object eval(RuntimeContext ctx) {
		return Boolean.TRUE;
	}
}
