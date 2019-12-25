package simpol;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import simpol.ast.Value;
import simpol.ast.ValueType;

public class Environment {
	private Map<String, ValueType> variableTypes = new HashMap<String, ValueType>();
	private Map<String, Value> variableValues = new HashMap<String, Value>();
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	private void checkVar(String name) {
		if (!variableTypes.containsKey(name))
			throw new IllegalArgumentException("Variable " + name + " is not defined");
	}

	private void checkVar(String name, Value value) {
		checkVar(name);
		if (variableTypes.get(name) != value.getType())
			throw new IllegalArgumentException("Variable " + name + " incorrect type. Expected " + value.getType().name()
					+ " Has: " + variableTypes.get(name).name());
	}

	public Value getVarValue(String name) {
		checkVar(name);
		return variableValues.get(name);
	}

	public void setVarFromInput(String varName) {
		checkVar(varName);
		System.out.printf("Please input \"%s\": ", varName);
		try {
			switch (variableTypes.get(varName)) {
			case STG:
				setVar(varName, new Value(reader.readLine()));

				break;
			case INT:
				try {
					setVar(varName, new Value(Integer.parseInt(reader.readLine())));
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException("Can't read INT value.");
				}

				break;
			case BLN:
				String data = reader.readLine();
				if (data.equals("true") || data.equals("false"))
					setVar(varName, new Value(data.equals("true")));
				else throw new IllegalArgumentException("Can't read BLN value.");

				break;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void printValue(Value value) {
		switch (value.getType()) {
		case BLN:
			System.out.println(value.getBoolVal());
			break;
		case STG:
			System.out.println(value.getStringVal());
			break;
		case INT:
			System.out.println(value.getIntVal());
			break;
		}

	}

	public void setVar(String varName, Value value) {
		checkVar(varName, value);
		variableValues.put(varName, value);

	}

	public void addVar(String type, String name) {
		variableTypes.put(name, ValueType.valueOf(type));
	}

	public Map<String, Value> getVariableValues() {
		return variableValues;
	}

}
