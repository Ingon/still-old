package org.still.parser;

import org.still.RuntimeContext;

public interface SourceElement {
	public Object eval(RuntimeContext runtime);
}
