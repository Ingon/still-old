package org.still;

import java.util.ArrayList;
import java.util.List;

import org.still.src.Token;
import org.still.src.TokenType;

public class Lexer {
	public static final String OPERATORS = "+-*/^&|?<>=\\";
	
	public static final String SEPARATORS = "[]{}()";
	
	public static final String LEX_SEPARATORS = " \r\n\t" + SEPARATORS;
	
	public static List<Token> tokenize(String input) {
		List<Token> tokens = new ArrayList<Token>();
		char[] chars = input.toCharArray();
		for(int i = 0; i < chars.length;i++) {
			char ch = chars[i];
			if(Character.isWhitespace(ch)) {
				tokens.add(new Token(TokenType.WHITESPACE, " "));
			}
			if(oneOf(ch, OPERATORS)) {
				int end = readUntil(chars, i, LEX_SEPARATORS);
				
				
				
				i = end - 1;
			}
		}
		return tokens;
	}
	
	public static boolean oneOf(char ch, String charsStr) {
		return charsStr.indexOf(ch) > -1;
	}
	
	public static int readUntil(char[] chars, int i, String charsStr) {
		for(i++; i < chars.length;i++) {
			char ch = chars[i];
			if(oneOf(ch, charsStr)) {
				return i;
			}
		}
		return i;
	}
}
