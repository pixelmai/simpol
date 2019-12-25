package simpol.ast.expression;

import simpol.Environment;
import simpol.ast.ASTVisitor;
import simpol.ast.Value;

public class INTExpression extends Expression {
	private int value;

	public INTExpression(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.format("INT_VAL[%s]", value + "");
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
