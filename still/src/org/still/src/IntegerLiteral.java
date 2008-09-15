package org.still.src;

public class IntegerLiteral implements Literal {
	public final Integer value;
	
	public IntegerLiteral(String value) {
		this.value = Integer.parseInt(value);
	}
}
