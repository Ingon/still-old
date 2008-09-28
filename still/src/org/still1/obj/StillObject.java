package org.still1.obj;

import org.still1.src.Symbol;

public interface StillObject {
	public StillObject get(Symbol name);
	public StillObject set(Symbol name, StillObject obj);
}
