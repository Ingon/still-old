package org.still;

import java.util.List;

import org.still.lexer.Lexer;
import org.still.parser.Parser;
import org.still.parser.SourceElement;
import org.still.parser.Symbol;

public class Main {
	public static void main(String[] args) {
		RuntimeContext ctx = new RuntimeContext();
		ctx.add(Symbol.get("+"), new Function() {
			@Override
			public Object apply(List<Object> arguments) {
				int result = 0;
				for(Object intObj : arguments) {
					result += (Integer) intObj;
				}
				return result;
			}});
		ctx.add(Symbol.get("-"), new Function() {
			@Override
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
		
		Lexer lexer = new Lexer("3;+[3, 4];-[3];-[6, 2];5 + 5;let a = 3;a + a;");
		Parser parser = new Parser(lexer);
		
		while(parser.hasNext()) {
			SourceElement element = parser.next();
			System.out.println(element + ";");
			System.out.println(element.eval(ctx));
		}
	}
}
