package org.still.src;

import org.still.RuntimeContext;
import org.still.obj.JavaStillObject;
import org.still.obj.StillObject;

public class StringLiteral implements Literal {

	public final String value;
	
	public StringLiteral(String value) {
		this.value = value;
	}

	public StillObject eval(RuntimeContext ctx) {
		return new JavaStillObject(value);
	}
}
