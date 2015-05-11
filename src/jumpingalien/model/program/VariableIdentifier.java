package jumpingalien.model.program;

import jumpingalien.model.program.types.Type;

public class VariableIdentifier {
	public VariableIdentifier(String name, Type type){
		this.name = name;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	public Type getType() {
		return type;
	}

	private final String name;
	private final Type type;
}
