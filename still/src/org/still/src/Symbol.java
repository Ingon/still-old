package org.still.src;

import java.util.HashMap;
import java.util.Map;

public class Symbol {
	private static final Map<String, Symbol> symbols = new HashMap<String, Symbol>();
	
	static synchronized Symbol get(String value) {
		Symbol symbol = symbols.get(value);
		if(symbol == null) {
			symbol = new Symbol(value);
			symbols.put(value, symbol);
		}
		return symbol;
	}
	
	private static long idGenerator = 0;
	
	public final long id;
	public final String value;
	
	private Symbol(String value) {
		this.value = value;
		this.id = ++idGenerator;
	}

	@Override
	public String toString() {
		return value + ":" + id;
	}
}
