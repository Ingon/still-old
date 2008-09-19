package org.still.obj;

import java.lang.reflect.Method;
import java.util.List;

import org.still.src.Symbol;

public class JavaCallableStillObject implements CallableStillObject {
	private final Object delegate;
	private final List<Method> methods;
	
	public JavaCallableStillObject(Object delegate, List<Method> methods) {
		this.delegate = delegate;
		this.methods = methods;
	}

	public StillObject apply(List<StillObject> params) {
		Object[] rparams = mapToJava(params);
		Method method = findRealMethod(rparams);
		try {
			Object result = method.invoke(delegate, rparams);
			return JavaStillObject.wrapIfNecessary(result);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public StillObject get(Symbol name) {
		throw new UnsupportedOperationException();
	}

	public StillObject set(Symbol name, StillObject obj) {
		throw new UnsupportedOperationException();
	}
	
	private static Object[] mapToJava(List<StillObject> params) {
		Object[] result = new Object[params.size()];
		for(int i = 0;i < result.length;i++) {
			StillObject obj = params.get(i);
			if(obj instanceof JavaStillObject) {
				result[i] = ((JavaStillObject) obj).getDelegate();
			} else {
				result[i] = obj;
			}
		}
		return result;
	}

	private Method findRealMethod(Object[] rparams) {
		if(methods.size() == 1) {
			return methods.get(0);
		}
		throw new UnsupportedOperationException();
	}
}
