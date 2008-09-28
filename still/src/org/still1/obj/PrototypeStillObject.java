package org.still1.obj;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import org.still1.src.Symbol;

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

	@Override
	public String toString() {
		StillObject obj = internalMap.get(Symbol.get("to-string"));
		if(! (obj instanceof CallableStillObject)) {
			return super.toString();
		}
		return "PSO: " + String.valueOf(((CallableStillObject) obj).apply(this, Collections.<StillObject>emptyList()));
	}
}
