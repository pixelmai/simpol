package simpol.parser;

import static simpol.tokens.TokenType.ADD_KWORD;
import static simpol.tokens.TokenType.AND_KWORD;
import static simpol.tokens.TokenType.ASK_KWORD;
import static simpol.tokens.TokenType.BLN_CONST;
import static simpol.tokens.TokenType.BLN_KWORD;
import static simpol.tokens.TokenType.CLOSE_BLOCK;
import static simpol.tokens.TokenType.CODE_KWORD;
import static simpol.tokens.TokenType.DIV_KWORD;
import static simpol.tokens.TokenType.END;
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

import simpol.ast.expression.BLNExpression;
import simpol.ast.expression.BinaryExpression;
import simpol.ast.expression.Expression;
import simpol.ast.expression.INTExpression;
import simpol.ast.expression.STRExpression;
import simpol.ast.expression.UnaryExpression;
import simpol.ast.expression.VariableExpression;
import simpol.ast.statement.ASKStatement;
import simpol.ast.statement.PRTStatement;
import simpol.ast.statement.PUTStatement;
import simpol.ast.statement.Statement;
import simpol.ast.statement.StatementBlock;
import simpol.ast.statement.VariableDefineStatement;
import simpol.tokens.Token;
import simpol.tokens.TokenType;

public class Parser {

	private List<TokenType> unaryOps = Arrays.asList(NON_KWORD);
	private List<TokenType> binaryOps = Arrays.asList(ADD_KWORD, SUB_KWORD, MUL_KWORD, DIV_KWORD, MOD_KWORD, GRE_KWORD,
			GRT_KWORD, LET_KWORD, LEE_KWORD, EQL_KWORD, AND_KWORD, OHR_KWORD);

	private int currentPos;
	private List<Token> tokens;
	private ParserListener listener;

	public Parser(ParserListener listener) {
		this.listener = listener;
	}

	private Token peek(int pos) {
		if (currentPos + pos >= tokens.size())
			return new Token(0, END);
		return tokens.get(currentPos + pos);
	}

	private Token peek() {
		return peek(0);
	}

	private Token read() {
		if (currentPos >= tokens.size())
			return new Token(0, END);
		return tokens.get(currentPos++);
	}

	private Token readType(TokenType type) throws ParseException {
		peekType(type);
		return read();
	}

	private Token peekType(TokenType type) throws ParseException {
		if (peek().getType() != type) {
			String message = String.format("Not expected token %s,expected %s", peek().getType().name(), type.name());
			listener.logError(message, peek().getStartPos());
			throw new ParseException(message);
		}
		return peek();
	}

	public StatementBlock parse(List<Token> tokens) throws ParseException {

		this.tokens = tokens;
		currentPos = 0;

		List<Statement> statements = new ArrayList<Statement>();

		peekType(VARIABLE_KWORD);
		statements.add(parserVariableBlock());
		peekType(CODE_KWORD);
		statements.add(parserCodeBlock());
		if (currentPos != tokens.size()) {
			listener.logError("Invalid token " + peek() + " , expected end of file.", peek().getStartPos());
			throw new ParseException("Invalid token " + peek());
		}

		return new StatementBlock(statements);
	}

	private Statement parserCodeBlock() throws ParseException {
		List<Statement> statements = new ArrayList<Statement>();
		read();
		readType(OPEN_BLOCK);
		while (peek().getType() != CLOSE_BLOCK)
			statements.add(parseCodeStatements());

		readType(CLOSE_BLOCK);

		return new StatementBlock(statements);
	}

	private Statement parseCodeStatements() throws ParseException {
		if (peek().getType() == PUT_KWORD)
			return parsePUTStatement();
		else if (peek().getType() == ASK_KWORD)
			return parseASKStatement();
		else if (peek().getType() == PRT_KWORD)
			return parsePRTStatement();
		else {
			String message = "Invalid token " + peek();
			listener.logError(message, peek().getStartPos());
			throw new ParseException(message);
		}

	}

	private Statement parsePRTStatement() throws ParseException {
		read();// PRT token
		return new PRTStatement(parseExpression());
	}

	private Statement parseASKStatement() throws ParseException {
		read();
		String varName = readType(ID).getText();
		return new ASKStatement(varName);
	}

	private Statement parsePUTStatement() throws ParseException {

		read();
		Expression value = parseExpression();
		readType(IN_KWORD);
		String varName = readType(ID).getText();

		return new PUTStatement(varName, value);
	}

	private Expression parseExpression() throws ParseException {

		if (binaryOps.contains(peek().getType()))
			return parseBinaryExpression();
		if (unaryOps.contains(peek().getType()))
			return parseUnaryExpression();
		else if (peek().getType() == INT_CONST)
			return parseNumberExpression();
		else if (peek().getType() == STG_CONST)
			return parseStringExpression();
		else if (peek().getType() == BLN_CONST)
			return parseBooleanExpression();
		else if (peek().getType() == ID)
			return parseVariableExpression();
		else {
			String message = "Unexpected token type: " + peek().getType().name();
			listener.logError(message, peek().getStartPos());
			throw new ParseException(message);
		}
	}

	private Expression parseVariableExpression() {
		return new VariableExpression(read().getText());
	}

	private Expression parseBooleanExpression() {
		return new BLNExpression(read().getText().equals("true"));
	}

	private Expression parseStringExpression() {
		return new STRExpression(read().getText().replace("\\n", "\n").replace("\\t", "\t").replace("\\$", "$"));
	}

	private Expression parseNumberExpression() {
		return new INTExpression(Integer.parseInt(read().getText()));
	}

	private Expression parseUnaryExpression() throws ParseException {
		Token name = read();
		Expression op = parseExpression();

		return new UnaryExpression(name.getText(), op);
	}

	private Expression parseBinaryExpression() throws ParseException {
		Token name = read();
		Expression op1 = parseExpression();
		Expression op2 = parseExpression();

		return new BinaryExpression(name.getText(), op1, op2);
	}

	private Statement parserVariableBlock() throws ParseException {

		List<Statement> statements = new ArrayList<Statement>();

		read(); // ID[variable]
		readType(OPEN_BLOCK);

		while (peek().getType() != CLOSE_BLOCK)
			statements.add(parseVariableDefineStatement());

		readType(CLOSE_BLOCK);

		return new StatementBlock(statements);
	}

	private Statement parseVariableDefineStatement() throws ParseException {

		Token type = read();
		Token name = readType(ID);

		if (!(type.getType() == INT_KWORD || type.getType() == STG_KWORD || type.getType() == BLN_KWORD)) {

			String message = "Invalid argument type " + type.getText();
			listener.logError(message, type.getStartPos());
			throw new ParseException(message);
		}

		return new VariableDefineStatement(type.getType().name().substring(0, 3), name.getText());
	}

}
