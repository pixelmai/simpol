package simpol.ast.expression;

import java.util.Arrays;
import java.util.List;

import simpol.Environment;
import simpol.ast.ASTVisitor;
import simpol.ast.Value;
import simpol.ast.ValueType;

public class BinaryExpression extends Expression {

	private String operation;
	private Expression op1, op2;

	public BinaryExpression(String operation, Expression op1, Expression op2) {
		this.operation = operation;
		this.op1 = op1;
		this.op2 = op2;
	}

	public String getOperation() {
		return operation;
	}

	public Expression getOp1() {
		return op1;
	}

	public Expression getOp2() {
		return op2;
	}

	@Override
	public String toString() {
		return String.format("BIN_OP[%s %s %s]", operation, op1.toString(), op2.toString());
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
		op1.accept(visitor);
		op2.accept(visitor);
	}

	@Override
	public Value calculate(Environment env) {

		Value v1 = op1.calculate(env);
		Value v2 = op2.calculate(env);

		List<String> names = Arrays.asList("ADD", "SUB", "MUL", "DIV", "MOD", "GRT", "GRE", "LET", "LEE", "EQL", "AND",
				"OHR", "NON");
		List<String> args = Arrays.asList("INT", "INT", "INT", "INT", "INT", "INT", "INT", "INT", "INT", "INT", "BLN",
				"BLN", "BLN");
		// check args
		int id = names.indexOf(operation);
		if (id < 0)
			throw new IllegalArgumentException("Action is not defined " + operation);
		if (v1.getType() != ValueType.valueOf(args.get(id)))
			throw new IllegalArgumentException("Argument 1 wrong type " + v1.getType() + " expected " + args.get(id));
		if (v2.getType() != ValueType.valueOf(args.get(id)))
			throw new IllegalArgumentException("Argument 2 wrong type " + v2.getType() + " expected " + args.get(id));

		// execute
		switch (operation) {
		case "ADD":
			return new Value(v1.getIntVal() + v2.getIntVal());
		case "SUB":
			return new Value(v1.getIntVal() - v2.getIntVal());
		case "MUL":
			return new Value(v1.getIntVal() * v2.getIntVal());
		case "DIV":
			return new Value(v1.getIntVal() / v2.getIntVal());
		case "MOD":
			return new Value(v1.getIntVal() % v2.getIntVal());
		case "GRT":
			return new Value(v1.getIntVal() > v2.getIntVal());
		case "GRE":
			return new Value(v1.getIntVal() >= v2.getIntVal());
		case "LET":
			return new Value(v1.getIntVal() < v2.getIntVal());
		case "LEE":
			return new Value(v1.getIntVal() <= v2.getIntVal());
		case "EQL":
			return new Value(v1.getIntVal() == v2.getIntVal());
		case "AND":
			return new Value(v1.getBoolVal() && v2.getBoolVal());
		case "OHR":
			return new Value(v1.getBoolVal() || v2.getBoolVal());
		case "NON":
			return new Value(!v1.getBoolVal());
		default:
			throw new IllegalArgumentException("Action is not defined " + operation);

		}

	}

}
