package simpol.ast.checkers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import simpol.ast.ASTVisitor;
import simpol.ast.statement.VariableDefineStatement;

public class VarInitChecker extends ASTVisitor {

	public Map<String, String> vars = new HashMap<String, String>();

	@Override
	public void visit(VariableDefineStatement statement) {
		List<String> types = Arrays.asList("INT", "STG", "BLN");
		if (!types.contains(statement.getType()))
			throw new IllegalArgumentException(
					"Variable " + statement.getName() + " type not defined. " + statement.getType());
		if (vars.containsKey(statement.getName()))
			throw new IllegalArgumentException("Variable " + statement.getName() + " already defined.");
		vars.put(statement.getName(), statement.getType());
	}

	public Map<String, String> getVars() {
		return vars;
	}

}
