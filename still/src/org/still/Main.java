package org.still;

import org.still.lexer.Lexer;
import org.still.parser.Parser;

public class Main {
	public static void main(String[] args) {
		Lexer lexer = new Lexer("+[a, b];");
		Parser parser = new Parser(lexer);
		while(parser.hasNext()) {
			System.out.println(parser.next());
		}
	}
}
