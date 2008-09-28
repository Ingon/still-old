package org.still1.src;

import org.still1.RuntimeContext;
import org.still1.obj.StillObject;

public interface Statement {
	public StillObject eval(RuntimeContext ctx);
}
