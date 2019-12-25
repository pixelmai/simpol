package simpol;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import simpol.ast.Value;
import simpol.ast.checkers.VarInitChecker;
import simpol.ast.statement.StatementBlock;
import simpol.lexer.Lexer;
import simpol.parser.ParseException;
import simpol.parser.Parser;
import simpol.tokens.Token;
import simpol.utils.SIMFile;

public class Simpol {

	public Simpol() {
		String fileName = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		System.out.print("Enter path to sim file: ");
		try {
			fileName = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		reader = null;

		processSIMFile(fileName, true, true, true, true);
	}

	public void processSIMFile(String filePath, boolean printSource, boolean printTokenList,
			boolean printAST, boolean printVariables) {
		SIMFile simFile = new SIMFile(new File(filePath));
		try {
			simFile.load();
		} catch (IOException e) {
			System.out.println("Can't load source code(" + e.getMessage() + ")");
			return;
		}
		if (printSource) {
			System.out.println("[SOURCE]");
			System.out.println(simFile.getSource());
			System.out.println("-------------------------");
		}

		List<Token> tokens = new Lexer(simFile.getSource()).tokenize();

		if (printTokenList)
			printTokens(tokens);

		StatementBlock program = null;
		try {
			program = new Parser((message, position) -> {
				System.err.printf("%s , at line %d\n", message, simFile.getLineNumber(position));
			}).parse(tokens);
		} catch (ParseException e) {
			return;
		}
		if (printAST) {
			System.out.println("[AST]");
			System.out.println(program);
			System.out.println("-------------------------");
		}
		VarInitChecker varInitChecker = new VarInitChecker();

		program.accept(varInitChecker);

		Environment environment = new Environment();

		program.execute(environment);
		System.out.println();

		if (printVariables)
			printVariables(environment);
	}

	public static void main(String[] args) {
		new Simpol();
	}

	private void printVariables(Environment environment) {
		System.out.println("[Symbol Table]");
		System.out.println("+-------------------------------+-----+-------------------------------+");
		System.out.printf("|%30s | %s|%30s |\n", "Name", "Type", "Value");
		System.out.println("+-------------------------------+-----+-------------------------------+");

		for (Map.Entry<String, Value> entry : environment.getVariableValues().entrySet())
			System.out.printf("|%30s | %s |%30s |\n", entry.getKey(), entry.getValue().getType().name(),
					entry.getValue().toString());
		System.out.println("+-------------------------------+-----+-------------------------------+");
	}

	private void printTokens(List<Token> tokens) {
		System.out.println("[Tokens/Lexemes Table]");
		System.out.println("+----------------+-------------------------------+");
		System.out.printf("|%15s |%30s |\n", "Type", "Text");
		System.out.println("+----------------+-------------------------------+");

		for (Token token : tokens)
			System.out.printf("|%15s |%30s |\n", token.getType().name(),
					token.getText() == null ? "" : token.getText());
		System.out.println("+----------------+-------------------------------+");
	}

}
