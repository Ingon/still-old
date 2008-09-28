package org.still1;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.List;

import org.still1.obj.StillObject;
import org.still1.src.Statement;
import org.still1.src.Symbol;

public class Still {
	public static void main(String[] args) throws Exception {
		System.out.println("Welcome to Still.");
		repl(System.out, System.in);
	}

	private static void repl(OutputStream os, InputStream is) throws Exception {
		PrintWriter out = new PrintWriter(os);
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		
		initTestContext(Context.get().rootCtx);
		
		do {
			out.print("> ");
			out.flush();
			
			String line = in.readLine();
			
			out.println(eval(line));
			out.println("");
			out.flush();
		} while(true);
	}

	private static String eval(String line) {
		Context context = Context.get();
		List<Statement> statements = context.parser.parseProgram(line);
		StillObject obj = context.runtime.eval(statements);
		return String.valueOf(obj);
	}
	
	private static void initTestContext(RuntimeContext ctx) {
		ctx.set(Symbol.get("ti1"), RuntimeSupport.newInteger(new BigInteger("10")));
		ctx.set(Symbol.get("ti2"), RuntimeSupport.newInteger(new BigInteger("20")));
	}
}
