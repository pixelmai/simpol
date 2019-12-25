package simpol.ast.statement;

import java.util.List;

import simpol.Environment;
import simpol.ast.ASTVisitor;

public class StatementBlock extends Statement {
	private List<Statement> statements;

	public StatementBlock(List<Statement> statements) {
		this.statements = statements;
	}

	public List<Statement> getStatements() {
		return statements;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		for (Statement statement : statements)
			buffer.append(statement.toString() + "\n");

		return String.format("BLOCK {\n%s}", buffer.toString());
	}

	@Override
	public void accept(ASTVisitor visitor) {
		for (Statement statement : statements)
			statement.accept(visitor);
		visitor.visit(this);
	}

	@Override
	public void execute(Environment env) {
		for (Statement statement : statements)
			statement.execute(env);
	}

}
