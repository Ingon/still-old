package org.still.src;

import org.still.Symbol;

public class Identifier implements Expression {
	public final Symbol value;
	
	public Identifier(Symbol value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "I(" + value + ")";
	}
}
