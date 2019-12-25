package simpol.ast;

public class Value {
	private int intVal;
	private String stringVal;
	private boolean boolVal;
	private ValueType type;

	public Value() {}

	public Value(int value) {
		this.intVal = value;
		type = ValueType.INT;
	}

	public Value(boolean value) {
		boolVal = value;
		type = ValueType.BLN;
	}

	public Value(String value) {
		stringVal = value;
		type = ValueType.STG;
	}

	public int getIntVal() {
		return intVal;
	}

	public String getStringVal() {
		return stringVal;
	}

	public boolean getBoolVal() {
		return boolVal;
	}

	public ValueType getType() {
		return type;
	}

	@Override
	public String toString() {
		if (type == null)
			return "NULL";
		switch (type) {
		case STG:
			return "\"" + stringVal.replace("\n", "\\n").replace("\t", "\\t") + "\"";
		case INT:
			return intVal + "";
		case BLN:
			return boolVal ? "true" : "false";

		}

		return "";
	}

}
