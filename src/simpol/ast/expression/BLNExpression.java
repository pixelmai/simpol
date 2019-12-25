package simpol.ast.expression;

import simpol.Environment;
import simpol.ast.ASTVisitor;
import simpol.ast.Value;

public class BLNExpression extends Expression {
	private boolean value;

	public BLNExpression(boolean value) {
		this.value = value;
	}

	public boolean getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.format("BLN_VAL[%s]", value + "");
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public Value calculate(Environment env) {
		return new Value(value);
	}
}
