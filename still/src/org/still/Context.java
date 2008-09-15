package org.still;

public class Context {
	private static ThreadLocal<Context> localContext = new ThreadLocal<Context>() {
		@Override
		protected synchronized Context initialValue() {
			return new Context();
		}
	};
	
	public static Context get() {
		return localContext.get();
	}
	
	public final Parser parser;
	public final Runtime runtime;
	
	private Context() {
		parser = new Parser();
		runtime = new Runtime();
	}
}
