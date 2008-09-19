package org.still.obj;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.still.src.Symbol;

public class JavaStillObject implements StillObject {
	private final Object delegate;
	
	public JavaStillObject(Object delegate) {
		this.delegate = delegate;
	}
	
	protected Object getDelegate() {
		return delegate;
	}

	public StillObject get(Symbol name) {
		Field fld = findField(delegate, name.value);
		if(fld != null) {
			try {
				Object obj = fld.get(delegate);
				return wrapIfNecessary(obj);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
		List<Method> methods = findMethod(delegate, name.value);
		if(methods.isEmpty()) {
			throw new RuntimeException("Property not found: " + name);
		}
		return new JavaCallableStillObject(methods);
	}

	protected static StillObject wrapIfNecessary(Object obj) {
		if(obj instanceof StillObject) {
			return (StillObject) obj;
		}
		return new JavaStillObject(obj);
	}

	public StillObject set(Symbol name, StillObject obj) {
		Field fld = findField(delegate, name.value);
		if(fld != null) {
			try {
				Object oldObject = fld.get(delegate);
				Object newObject = obj;
				if(obj instanceof JavaStillObject) {
					newObject = ((JavaStillObject) obj).delegate;
				}
				fld.set(delegate, newObject);
				return wrapIfNecessary(oldObject);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
		throw new RuntimeException("Settable property not found: " + name);
	}
	
	private Field findField(Object obj, String name) {
		Class<?> clazz = delegate.getClass();
		Field[] fields = clazz.getFields();
		for(Field fld : fields) {
			if(fld.getName().equals(name)) {
				return fld;
			}
		}
		return null;
	}
	
	private List<Method> findMethod(Object obj, String name) {
		Class<?> clazz = delegate.getClass();
		List<Method> result = new ArrayList<Method>();
		Method[] methods = clazz.getMethods();
		for(Method meth : methods) {
			if(meth.getName().equals(name))	{
				result.add(meth);
			}
		}
		return result;
	}

	@Override
	public String toString() {
		return "JSO: " + delegate;
	}
}
