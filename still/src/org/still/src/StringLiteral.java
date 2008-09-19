package org.still.src;

import org.still.RuntimeContext;
import org.still.RuntimeSupport;
import org.still.obj.StillObject;

public class StringLiteral implements Literal {

	public final String value;
	
	public StringLiteral(String value) {
		this.value = value.substring(1, value.length() - 1);
	}

	public StillObject eval(RuntimeContext ctx) {
		return RuntimeSupport.newString(ctx, value);
	}

	@Override
	public String toString() {
		return "'" + value + "'";
	}
}
