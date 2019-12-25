package simpol.ast.expression;

import simpol.Environment;
import simpol.ast.ASTVisitor;
import simpol.ast.Value;

public class VariableExpression extends Expression {
	private String varName;

	public VariableExpression(String varName) {
		super();
		this.varName = varName;
	}

	public String getVarName() {
		return varName;
	}

	@Override
	public String toString() {
		return String.format("VAR_VAL[%s]", varName);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public Value calculate(Environment env) {
		return env.getVarValue(varName);
	}
}
