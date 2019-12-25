package simpol.ast.expression;

import simpol.Environment;
import simpol.ast.ASTVisitor;
import simpol.ast.Value;

public class STRExpression extends Expression {
	private String value;

	public STRExpression(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.format("STR_VAL[%s]", value + "");
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
