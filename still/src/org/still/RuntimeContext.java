package org.still;

import java.util.HashMap;
import java.util.Map;

import org.still.parser.Symbol;

public class RuntimeContext {
	private Map<Symbol, Object> bindings = new HashMap<Symbol, Object>();
	
	public Object get(Symbol name) {
		return bindings.get(name);
	}
	
	public void add(Symbol name, Object value) {
		if(bindings.get(name) != null) {
			throw new RuntimeException("Symbol already taken");
		}
		
		bindings.put(name, value);
	}
}
