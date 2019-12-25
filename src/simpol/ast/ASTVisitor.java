package simpol.ast;

import simpol.ast.expression.BLNExpression;
import simpol.ast.expression.BinaryExpression;
import simpol.ast.expression.INTExpression;
import simpol.ast.expression.STRExpression;
import simpol.ast.expression.UnaryExpression;
import simpol.ast.expression.VariableExpression;
import simpol.ast.statement.ASKStatement;
import simpol.ast.statement.PRTStatement;
import simpol.ast.statement.PUTStatement;
import simpol.ast.statement.StatementBlock;
import simpol.ast.statement.VariableDefineStatement;

public abstract class ASTVisitor {
	public void visit(BinaryExpression expression) {}

	public void visit(BLNExpression expression) {}

	public void visit(INTExpression expression) {}

	public void visit(STRExpression expression) {}

	public void visit(VariableExpression expression) {}

	public void visit(ASKStatement statement) {}

	public void visit(PRTStatement statement) {}

	public void visit(PUTStatement statement) {}

	public void visit(StatementBlock statement) {}

	public void visit(VariableDefineStatement statement) {}

	public void visit(UnaryExpression statement) {}

}
