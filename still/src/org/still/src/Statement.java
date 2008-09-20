package org.still.src;

import org.still.RuntimeContext;
import org.still.obj.StillObject;

public interface Statement {
	public StillObject eval(RuntimeContext ctx);
}
