package org.still1.src;

import java.math.BigInteger;

import org.still1.RuntimeContext;
import org.still1.RuntimeSupport;
import org.still1.obj.StillObject;

public class IntegerLiteral implements Literal {
	public final BigInteger value;
	
	public IntegerLiteral(String value) {
		this.value = new BigInteger(value);
	}

	public StillObject eval(RuntimeContext ctx) {
		return RuntimeSupport.newInteger(value);
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
