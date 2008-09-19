package org.still.obj;

import java.util.List;

public interface CallableStillObject extends StillObject {
	public StillObject apply(List<StillObject> params);
}
