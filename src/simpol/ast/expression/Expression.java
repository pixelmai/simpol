package simpol.ast.expression;

import simpol.Environment;
import simpol.ast.ASTVisitor;
import simpol.ast.Value;

public abstract class Expression {
	public abstract void accept(ASTVisitor visitor);

	public abstract Value calculate(Environment env);
}
