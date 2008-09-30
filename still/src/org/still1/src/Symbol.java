package org.still1.src;

import java.util.HashMap;
import java.util.Map;

import org.still1.RuntimeContext;
import org.still1.obj.StillObject;

public class Symbol implements Comparable<Symbol>, Expression {
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

	public StillObject eval(RuntimeContext ctx) {
		return ctx.get(this);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Symbol other = (Symbol) obj;
		if (id != other.id)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return value;
	}
}
