package org.still.obj;

import org.still.src.Symbol;

public interface StillObject {
	public StillObject get(Symbol name);
	public StillObject set(Symbol name, StillObject obj);
}
