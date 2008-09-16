package org.still.src;

public class Identifier implements Expression {
	public final String value;
	
	public Identifier(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "I(" + value + ")";
	}
}