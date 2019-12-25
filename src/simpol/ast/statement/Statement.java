package simpol.ast.statement;

import simpol.Environment;
import simpol.ast.ASTVisitor;

public abstract class Statement {
	public abstract void accept(ASTVisitor visitor);

	public abstract void execute(Environment env);
}
