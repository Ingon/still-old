package org.still.obj;

import java.lang.reflect.Method;
import java.util.List;

import org.still.RuntimeSupport;
import org.still.src.Symbol;

public class JavaCallableStillObject implements CallableStillObject {
	private final List<Method> methods;
	
	public JavaCallableStillObject(List<Method> methods) {
		this.methods = methods;
	}

	public StillObject apply(StillObject thisRef, List<StillObject> params) {
		if(! (thisRef instanceof JavaStillObject)) {
			throw new RuntimeException("Unexpected reference!");
		}
		Object delegate = ((JavaStillObject) thisRef).getDelegate();
		
		Object[] rparams = mapToJava(params);
		Method method = findRealMethod(rparams);
		try {
			Object result = method.invoke(delegate, rparams);
			return RuntimeSupport.wrap(result);
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
		if(rparams.length > 1) {
			throw new UnsupportedOperationException();
		}
		
		Method bestFit = null;
		for(Method meth : methods) {
			if(meth.getParameterTypes()[0].isInstance(rparams[0])) {
				if(bestFit == null) {
					bestFit = meth;
					continue;
				}
				
				if(bestFit.getParameterTypes()[0].isAssignableFrom(meth.getParameterTypes()[0])) {
					bestFit = meth;
				}
			}
		}
		
		if(bestFit == null) {
			throw new RuntimeException("No fit found");
		}
		return bestFit;
	}
}
