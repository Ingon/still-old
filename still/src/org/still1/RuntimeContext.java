package org.still1;

import org.still1.obj.PrototypeStillObject;
import org.still1.obj.StillObject;
import org.still1.src.Symbol;

public class RuntimeContext extends PrototypeStillObject {
	
	private final RuntimeContext parent;
	
	protected RuntimeContext() {
		this(new RuntimeContext(null) {
			public StillObject get(Symbol value) {
				throw new RuntimeException("Object not found: " + value);
			}

			public StillObject set(Symbol symbol, StillObject stillObject) {
				throw new UnsupportedOperationException();
			}
		});
	}
	
	private RuntimeContext(RuntimeContext parent) {
		this.parent = parent;
	}
	
	public RuntimeContext childContext() {
		return new RuntimeContext(this);
	}

	@Override
	public StillObject get(Symbol name) {
		StillObject val = super.get(name);
		if(val == null) {
			return parent.get(name);
		}
		return val;
	}

	@Override
	public StillObject set(Symbol name, StillObject obj) {
		StillObject val = super.get(name);
		if(val != null) {
			throw new RuntimeException("Cannot change the value of " + name);
		}
		return super.set(name, obj);
	}
}
