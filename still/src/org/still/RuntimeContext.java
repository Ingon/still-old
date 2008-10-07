package org.still;

import java.util.HashMap;
import java.util.Map;

import org.still.parser.Symbol;

public class RuntimeContext {
	private final RuntimeContext parent;
	private final Map<Symbol, Object> bindings = new HashMap<Symbol, Object>();
	
	public RuntimeContext() {
		this.parent = null;
	}
	
	private RuntimeContext(RuntimeContext parent) {
		this.parent = parent;
	}
	
	public Object get(Symbol name) {
		Object value = bindings.get(name);
		if(value != null) {
			return value;
		}
		
		if(parent == null) {
			throw new RuntimeException("Symbol " + name + " not found");
		}
		
		return parent.get(name);
	}
	
	public void add(Symbol name, Object value) {
		if(bindings.get(name) != null) {
			throw new RuntimeException("Symbol already taken");
		}
		
		bindings.put(name, value);
	}

	public RuntimeContext child() {
		return new RuntimeContext(this);
	}
}
