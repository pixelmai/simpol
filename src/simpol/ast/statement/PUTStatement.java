package simpol.ast.statement;

import simpol.Environment;
import simpol.ast.ASTVisitor;
import simpol.ast.expression.Expression;

public class PUTStatement extends Statement {
	private String varName;
	private Expression value;

	public PUTStatement(String varName, Expression value) {
		super();
		this.varName = varName;
		this.value = value;
	}

	public String getVarName() {
		return varName;
	}

	public Expression getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.format("PUT[%s %s]", getVarName(), getValue());
	}

	@Override
	public void accept(ASTVisitor visitor) {
		value.accept(visitor);
		visitor.visit(this);
	}

	@Override
	public void execute(Environment env) {
		env.setVar(varName, value.calculate(env));

	}

}
