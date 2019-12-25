package simpol.tokens;

public class Token {

	private int startPos;
	private TokenType type;
	private String text;

	public Token(int startPos, TokenType type) {
		this(startPos, type, null);
	}

	public Token(int startPos, TokenType type, String text) {
		this.startPos = startPos;
		this.type = type;
		this.text = text;
	}

	public TokenType getType() {
		return type;
	}

	public String getText() {
		return text;
	}

	public int getStartPos() {
		return startPos;
	}

	@Override
	public String toString() {
		return String.format("%s" + (text != null ? "[%s]" : ""), type.name(), text);
	}

}
