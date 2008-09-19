package org.still.obj;

import java.util.Map;
import java.util.TreeMap;

import org.still.src.Symbol;

public class PrototypeStillObject implements StillObject {

	private final Map<Symbol, StillObject> internalMap;
	
	public PrototypeStillObject() {
		internalMap = new TreeMap<Symbol, StillObject>();
	}

	@Override
	public StillObject get(Symbol name) {
		return internalMap.get(name);
	}

	@Override
	public StillObject set(Symbol name, StillObject obj) {
		return internalMap.put(name, obj);
	}

}
