package org.still.src;

import java.math.BigInteger;

import org.still.RuntimeContext;
import org.still.RuntimeSupport;
import org.still.obj.StillObject;

public class IntegerLiteral implements Literal {
	public final BigInteger value;
	
	public IntegerLiteral(String value) {
		this.value = new BigInteger(value);
	}

	public StillObject eval(RuntimeContext ctx) {
		return RuntimeSupport.newInteger(ctx, value);
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
