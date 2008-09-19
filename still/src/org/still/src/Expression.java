package org.still.src;

import org.still.RuntimeContext;
import org.still.obj.StillObject;

public interface Expression {
	public StillObject eval(RuntimeContext ctx);
}
