package simpol.ast.statement;

import simpol.Environment;
import simpol.ast.ASTVisitor;
import simpol.ast.expression.Expression;

public class PRTStatement extends Statement {
	private Expression expression;

	public PRTStatement(Expression expression) {
		this.expression = expression;
	}

	public Expression getExpression() {
		return expression;
	}

	@Override
	public String toString() {
		return String.format("PRT[%s]", expression.toString());
	}

	@Override
	public void accept(ASTVisitor visitor) {
		expression.accept(visitor);
		visitor.visit(this);
	}

	@Override
	public void execute(Environment env) {
		env.printValue(expression.calculate(env));

	}

}
