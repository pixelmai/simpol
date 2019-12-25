package simpol.ast.statement;

import simpol.Environment;
import simpol.ast.ASTVisitor;

public class VariableDefineStatement extends Statement {
	private String type;
	private String name;

	public VariableDefineStatement(String type, String name) {
		this.type = type;
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return String.format("VAR_DEF[%s %s]", type, name);
	}   

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public void execute(Environment env) {
		env.addVar(type, name);
	}

}
