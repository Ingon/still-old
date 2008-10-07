package org.still.lexer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Lexer implements Iterator<Token> {
	
	private final Reader input;
	protected Character ch;
	
	private final CharacterClass whiteSpace = new SimpleCharacterClass(" \r\n\t");
	private final CharacterClass digit = new RangeCharacterClass('0', '9');
	private final CharacterClass alphaSmall = new RangeCharacterClass('a', 'z');
	private final CharacterClass alphaBig = new RangeCharacterClass('A', 'Z');
	private final CharacterClass alpha = new CompositeCharacterClass(alphaSmall, alphaBig, new SimpleCharacterClass('_'));
	private final CharacterClass operator = new SimpleCharacterClass("~!@$%^&*-+/<>=\\|?.");
	private final CharacterClass separator = new SimpleCharacterClass("[](){};,");
	private final CharacterClass special = new SimpleCharacterClass(":#");
	
	private final CharacterClass nameStart = new CompositeCharacterClass(alpha, operator);
	private final CharacterClass namePart = new CompositeCharacterClass(alpha, digit, operator);
	
	public Lexer(Reader reader) {
		input = reader;
		nextChar();
	}
	
	public Lexer(InputStream is) {
		input = new InputStreamReader(is);
		nextChar();
	}
	
	public Lexer(String text) {
		input = new StringReader(text);
		nextChar();
	}
	
	public boolean hasNext() {
		return ch != null;
	}

	public Token next() {
		if(ch == null) {
			throw new NoSuchElementException();
		}
		
		if(whiteSpace.matches(ch)) {
			String ws = whiteSpace.readWhole(this);
			return Token.get(TokenType.WHITE_SPACE, ws);
		}
		
		if(digit.matches(ch)) {
			String number = digit.readWhole(this);
			if(nameStart.matches(ch)) {
				throw new RuntimeException("Numbers contain only digits");
			}
			return Token.get(TokenType.NUMBER, number);
		}
		
		if(operator.matches(ch)) {
			String op = operator.readWhole(this);
			if(! alpha.matches(ch)) {
				return Token.get(TokenType.OPERATOR, op);
			}
		}
		
		if(nameStart.matches(ch)) {
			String name = namePart.readWhole(this);
			return Token.get(TokenType.NAME, name);
		}
		
		if(separator.matches(ch)) {
			char temp = ch;
			nextChar();
			return Token.get(TokenType.SEPARATOR, "" + temp);
		}
		
		if(special.matches(ch)) {
			char temp = ch;
			nextChar();
			return Token.get(TokenType.SPECIAL, "" + temp);
		}
		
		throw new RuntimeException("Unknown character");
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}
	
	protected Character nextChar() {
		try {
			int ich = input.read();
			if(ich == -1) {
				ch = null;
			} else {
				ch = (char) ich;
			}
			return ch;
		} catch(IOException exc) {
			throw new RuntimeException(exc);
		}
	}
}
