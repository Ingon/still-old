package org.still1;

import java.util.ArrayList;
import java.util.List;

import org.still1.src.Token;
import org.still1.src.TokenType;

public class Lexer {
	public static final String UNARY_OPERATORS = "!^~";
	
	public static final String BINARY_OPERATORS = "+-*/<>=&|%\\@$?";
	
	public static final String OPERATORS = BINARY_OPERATORS + UNARY_OPERATORS;
	
	public static final String SEPARATORS = "[]{}(),.:;#";
	
	public static final String LEX_SEPARATORS = " \r\n\t" + SEPARATORS;
	
	public static List<Token> tokenize(String input) {
		List<Token> tokens = new ArrayList<Token>();
		char[] chars = input.toCharArray();
		for(int i = 0; i < chars.length;i++) {
			char ch = chars[i];
			if(Character.isWhitespace(ch)) {
				tokens.add(new Token(TokenType.WHITESPACE, " "));
			} else if(oneOf(ch, SEPARATORS)) {
				tokens.add(new Token(TokenType.SEPARATOR, String.valueOf(ch)));
			} else if(Character.isDigit(ch) || (ch == '-' && Character.isDigit(chars[i + 1]))) {
				int lastIndex = i + 1;
				for(; lastIndex < chars.length; lastIndex++) {
					char tempChar = chars[lastIndex];
					if(Character.isDigit(tempChar)) {
						continue;
					}
					if(oneOf(tempChar, LEX_SEPARATORS)) {
						break;
					}
					throw new RuntimeException("Expected digit or separator!");
				}
				tokens.add(new Token(TokenType.NUMBER, input.substring(i, lastIndex)));
				i = lastIndex - 1;
			} else if(ch == '\'') {
				int lastIndex = i + 1;
				int cnt = 0;
				for(; lastIndex < chars.length; lastIndex++) {
					char tempChar = chars[lastIndex];
					if(tempChar == '\\') {
						cnt++;
						continue;
					}
					if(tempChar == '\'') {
						if(cnt == 0) {
							break;
						}
						if(cnt % 2 == 0) {
							break;
						}
					}
					cnt = 0;
				}
				tokens.add(new Token(TokenType.STRING, input.substring(i, lastIndex + 1)));
				i = lastIndex;
			} else {
				TokenType type = TokenType.SYMBOL;
				if(oneOf(ch, UNARY_OPERATORS)) {
					type = TokenType.UNARY_OPERATOR;
				} else if(oneOf(ch, BINARY_OPERATORS)) {
					type = TokenType.BINARY_OPERATOR;
				}
				
				int lastIndex = i;
				for(; lastIndex < chars.length; lastIndex++) {
					char tempChar = chars[lastIndex];
					if(oneOf(tempChar, LEX_SEPARATORS)) {
						break;
					}
					if(type == TokenType.UNARY_OPERATOR && Character.isJavaIdentifierPart(tempChar)) {
						type = TokenType.SYMBOL;
						continue;
					}
					if(type == TokenType.BINARY_OPERATOR && Character.isJavaIdentifierPart(tempChar)) {
						type = TokenType.SYMBOL;
					}
				}
				String identifier = input.substring(i, lastIndex);
				tokens.add(new Token(type, identifier));
				i = lastIndex - 1;
			}
		}
		return tokens;
	}
	
	public static boolean oneOf(char ch, String charsStr) {
		return charsStr.indexOf(ch) > -1;
	}
}
