package simpol.lexer;

import static simpol.tokens.TokenType.ADD_KWORD;
import static simpol.tokens.TokenType.AND_KWORD;
import static simpol.tokens.TokenType.ASK_KWORD;
import static simpol.tokens.TokenType.BLN_CONST;
import static simpol.tokens.TokenType.BLN_KWORD;
import static simpol.tokens.TokenType.CLOSE_BLOCK;
import static simpol.tokens.TokenType.CODE_KWORD;
import static simpol.tokens.TokenType.DIV_KWORD;
import static simpol.tokens.TokenType.EQL_KWORD;
import static simpol.tokens.TokenType.GRE_KWORD;
import static simpol.tokens.TokenType.GRT_KWORD;
import static simpol.tokens.TokenType.ID;
import static simpol.tokens.TokenType.INT_CONST;
import static simpol.tokens.TokenType.INT_KWORD;
import static simpol.tokens.TokenType.IN_KWORD;
import static simpol.tokens.TokenType.LEE_KWORD;
import static simpol.tokens.TokenType.LET_KWORD;
import static simpol.tokens.TokenType.MOD_KWORD;
import static simpol.tokens.TokenType.MUL_KWORD;
import static simpol.tokens.TokenType.NON_KWORD;
import static simpol.tokens.TokenType.OHR_KWORD;
import static simpol.tokens.TokenType.OPEN_BLOCK;
import static simpol.tokens.TokenType.PRT_KWORD;
import static simpol.tokens.TokenType.PUT_KWORD;
import static simpol.tokens.TokenType.STG_CONST;
import static simpol.tokens.TokenType.STG_KWORD;
import static simpol.tokens.TokenType.SUB_KWORD;
import static simpol.tokens.TokenType.VARIABLE_KWORD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import simpol.tokens.Token;
import simpol.tokens.TokenType;

public class Lexer {

	private static String ONE_CHAR_TOKENS = "{}";
	private static TokenType[] ONE_CHAR_TOKENS_TYPE = { OPEN_BLOCK, CLOSE_BLOCK };
	private static List<String> wordsTokens = Arrays.asList("INT", "BLN", "STG", "variable", "code", "PUT", "IN", "ASK",
			"ADD", "SUB", "MUL", "DIV", "MOD", "GRT", "GRE", "LET", "LEE", "EQL", "AND", "OHR", "NON", "PRT");
	private static TokenType[] wordsTokensTypes = { INT_KWORD, BLN_KWORD, STG_KWORD, VARIABLE_KWORD, CODE_KWORD,
			PUT_KWORD, IN_KWORD, ASK_KWORD, ADD_KWORD, SUB_KWORD, MUL_KWORD, DIV_KWORD, MOD_KWORD, GRT_KWORD, GRE_KWORD,
			LET_KWORD, LEE_KWORD, EQL_KWORD, AND_KWORD, OHR_KWORD, NON_KWORD, PRT_KWORD };

	private String source;
	private int currentPos;
	private List<Token> tokens;

	public Lexer(String source) {
		this.source = source;
		this.currentPos = 0;

	}

	private char peek(int pos) {
		if (currentPos + pos >= source.length())
			return '\0';
		return source.charAt(currentPos + pos);
	}

	private char peek() {
		return peek(0);
	}

	private char read() {
		if (currentPos >= source.length())
			return '\0';
		return source.charAt(currentPos++);
	}

	public List<Token> tokenize() {

		tokens = new ArrayList<Token>();

		while (currentPos < source.length()) {

			if (Character.isDigit(peek()))
				parseNumberConst();
			else if (peek() == '$')
				parseStringConst();
			else if (Character.isLetter(peek()))
				parseID();
			else if (ONE_CHAR_TOKENS.indexOf(peek()) != -1)
				parseOneCharToken();
			else if (peek() == '\n' || peek() == '\t' || peek() == ' ')
				read();
			else throw new IllegalArgumentException(
					String.format("Invalid character at position %d, '%s'", currentPos + 1, peek()));

		}

		return tokens;

	}

	private void parseOneCharToken() {
		tokens.add(new Token(currentPos + 1, ONE_CHAR_TOKENS_TYPE[ONE_CHAR_TOKENS.indexOf(peek())], read() + ""));
	}

	private void parseID() {
		StringBuffer buffer = new StringBuffer();
		int startIndex = currentPos + 1;

		while (Character.isAlphabetic(peek()) || Character.isDigit(peek()))
			buffer.append(read());

		String text = buffer.toString();
		if (text.equals("true") || text.equals("false"))
			tokens.add(new Token(startIndex, BLN_CONST, text));
		else if (wordsTokens.contains(text)) {
			int index = wordsTokens.indexOf(text);
			tokens.add(new Token(startIndex, wordsTokensTypes[index], text));
		} else tokens.add(new Token(startIndex, ID, text));
	}

	private void parseStringConst() {
		read(); // First $

		StringBuffer buffer = new StringBuffer();
		int startIndex = currentPos + 1;

		while (peek(-1) == '\\' || peek() != '$')
			buffer.append(read());

		read(); // last $

		String data = buffer.toString();

		tokens.add(new Token(startIndex, STG_CONST, data));

	}

	private void parseNumberConst() {
		StringBuffer buffer = new StringBuffer();
		int startIndex = currentPos + 1;

		while (Character.isDigit(peek()))
			buffer.append(read());

		tokens.add(new Token(startIndex, INT_CONST, buffer.toString()));

	}

}
