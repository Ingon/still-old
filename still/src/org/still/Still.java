package org.still;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;

import org.still.src.Expression;
import org.still.src.Symbol;

public class Still {
	public static void main(String[] args) throws Exception {
		System.out.println("Welcome to Still.");
		repl(System.out, System.in);
	}

	private static void repl(OutputStream os, InputStream is) throws Exception {
		PrintWriter out = new PrintWriter(os);
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		
		RuntimeContext ctx = new RuntimeContext();
		initTestContext(ctx);
		
		do {
			out.print("> ");
			out.flush();
			
			String line = in.readLine();
			
			out.println(eval(ctx, line));
			out.println("");
			out.flush();
		} while(true);
	}

	private static String eval(RuntimeContext ctx, String line) {
		Context context = Context.get();
		Expression expr = context.parser.parseExpression(line);
		Object obj = context.runtime.eval(ctx, expr);
		return String.valueOf(obj);
	}
	
	private static void initTestContext(RuntimeContext ctx) {
		ctx.set(Symbol.get("ti1"), RuntimeSupport.newInteger(ctx, new BigInteger("10")));
		ctx.set(Symbol.get("ti2"), RuntimeSupport.newInteger(ctx, new BigInteger("20")));
	}
}
