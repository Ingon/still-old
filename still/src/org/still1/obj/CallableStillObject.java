package org.still1.obj;

import java.util.List;

public interface CallableStillObject extends StillObject {
	public StillObject apply(StillObject thisRef, List<StillObject> params);
}
