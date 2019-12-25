package simpol.ast.expression;

import java.util.Arrays;
import java.util.List;

import simpol.Environment;
import simpol.ast.ASTVisitor;
import simpol.ast.Value;
import simpol.ast.ValueType;

public class UnaryExpression extends Expression {

	private String operation;
	private Expression op;

	public UnaryExpression(String operation, Expression op) {
		this.operation = operation;
		this.op = op;
	}

	public String getOperation() {
		return operation;
	}

	public Expression getOp() {
		return op;
	}

	@Override
	public String toString() {
		return String.format("UN_OP[%s %s ]", operation, op.toString());
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
		op.accept(visitor);
	}

	@Override
	public Value calculate(Environment env) {

		Value v = op.calculate(env);

		List<String> names = Arrays.asList("NON");
		List<String> args = Arrays.asList("BLN");
		// check args
		int id = names.indexOf(operation);
		if (id < 0)
			throw new IllegalArgumentException("Action is not defined " + operation);
		if (v.getType() != ValueType.valueOf(args.get(id)))
			throw new IllegalArgumentException("Argument 1 wrong type " + v.getType() + " expected " + args.get(id));

		// execute
		switch (operation) {
		case "NON":
			return new Value(!v.getBoolVal());
		default:
			throw new IllegalArgumentException("Action is not defined " + operation);

		}

	}

}
