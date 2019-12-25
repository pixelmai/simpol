package simpol.ast.statement;

import simpol.Environment;
import simpol.ast.ASTVisitor;

public class ASKStatement extends Statement {
	private String varName;

	public ASKStatement(String varName) {
		this.varName = varName;
	}

	public String getVarName() {
		return varName;
	}

	@Override
	public String toString() {
		return String.format("ASK[%s]", varName);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public void execute(Environment env) {
		env.setVarFromInput(varName);
	}
}
