package org.still;

import java.util.List;

import org.still.parser.Expression;

public interface Function {
	public Object apply(List<Expression> arguments);
}
