package jumpingalien.model.program;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

import jumpingalien.model.program.expressions.Variable;
import jumpingalien.model.program.statements.Statement;
import jumpingalien.model.program.types.Type;

public class Program {
	
	
	//private HashMap<String, Variable<?>> variables = new HashMap<>();
	// TODO bovenstaande is fout! Want ge moet ook nog u type opslaan (zie ProgramFactory createProgram) dus tzal eerder iets zijn als
	//  Map<String, array(Type, Variable)>
	// Want er kan per string maar variabele zijn
	public Program(Statement mainStatement, Map<String, Type> globalVariables){
		this.mainStatement = mainStatement;
		this.globalVariables = globalVariables;
	}
	
	private Map<String, Type> globalVariables = new HashMap<>();
	
	public Type getVariable(String name){
		if(!this.globalVariables.containsKey(name))
			return null;
		return this.globalVariables.get(name);
		
	}
	
	public void setVariable(String name, Type value){
		if(!globalVariables.containsKey(name))
			throw new IllegalArgumentException();
		this.globalVariables.put(name, value);
	}
	
	private final Statement mainStatement;
}

