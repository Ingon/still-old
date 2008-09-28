package org.still1.src;

import org.still1.RuntimeContext;
import org.still1.RuntimeSupport;
import org.still1.obj.StillObject;

public class StringLiteral implements Literal {

	public final String value;
	
	public StringLiteral(String value) {
		this.value = value.substring(1, value.length() - 1);
	}

	public StillObject eval(RuntimeContext ctx) {
		return RuntimeSupport.newString(value);
	}

	@Override
	public String toString() {
		return "'" + value + "'";
	}
}
