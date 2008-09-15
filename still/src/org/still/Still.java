package org.still;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class Still {
	public static void main(String[] args) throws Exception {
		System.out.println("Welcome to Still.");
		repl(System.out, System.in);
	}

	private static void repl(OutputStream os, InputStream is) throws Exception {
		PrintWriter out = new PrintWriter(os);
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		
		do {
			out.print("> ");
			out.flush();
			
			String line = in.readLine();
			
			out.println(eval(line));
			out.flush();
		} while(true);
	}

	private static String eval(String line) {
		return line;
	}
}
