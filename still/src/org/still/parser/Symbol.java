package org.still.parser;

import java.util.HashMap;
import java.util.Map;

public class Symbol {
	private static final Map<String, Symbol> symbols = new HashMap<String, Symbol>();
	
	public static synchronized Symbol get(String value) {
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

	public int compareTo(Symbol o) {
		return value.compareTo(o.value);
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Symbol && this.id == ((Symbol) obj).id;
	}

	@Override
	public int hashCode() {
		return new Long(id).hashCode();
	}

	@Override
	public String toString() {
		return value;
	}
}
