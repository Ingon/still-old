package org.still.src;

import java.math.BigInteger;

import org.still.RuntimeContext;
import org.still.obj.JavaStillObject;
import org.still.obj.StillObject;

public class IntegerLiteral implements Literal {
	public final Integer value;
	
	public IntegerLiteral(String value) {
		this.value = Integer.parseInt(value);
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	public StillObject eval(RuntimeContext ctx) {
		return new JavaStillObject(new BigInteger(String.valueOf(value)));
	}
}
