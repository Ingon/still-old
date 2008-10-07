package org.still;

import java.io.FileReader;
import java.util.List;

import org.still.lexer.Lexer;
import org.still.parser.Parser;
import org.still.parser.SourceElement;
import org.still.parser.Symbol;

public class Main {
	public static void main(String[] args) throws Exception {
		RuntimeContext ctx = new RuntimeContext();
		ctx.add(Symbol.get("+"), new Function() {
			public Object apply(List<Object> arguments) {
				int result = 0;
				for(Object intObj : arguments) {
					result += (Integer) intObj;
				}
				return result;
			}});
		ctx.add(Symbol.get("-"), new Function() {
			public Object apply(List<Object> arguments) {
				int result = (Integer) arguments.get(0);
				if(arguments.size() == 1) {
					return -result;
				}
				
				for(int i = 1, n = arguments.size(); i < n; i++) {
					result -= (Integer) arguments.get(i);
				}
				return result;
			}});
		
		Lexer lexer = new Lexer(new FileReader("tests/simple.still"));
		Parser parser = new Parser(lexer);
		
		while(parser.hasNext()) {
			SourceElement element = parser.next();
			System.out.println(element + ";");
			System.out.println(element.eval(ctx));
		}
	}
}
