package org.still;

import org.still.obj.PrototypeStillObject;
import org.still.obj.StillObject;
import org.still.src.Symbol;

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
}
