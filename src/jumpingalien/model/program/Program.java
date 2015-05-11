package jumpingalien.model.program;

import java.lang.reflect.Array;
import java.util.HashMap;

import jumpingalien.model.program.expressions.Variable;
import jumpingalien.model.program.types.Type;

public class Program {
	
	
	//private HashMap<String, Variable<?>> variables = new HashMap<>();
	// TODO bovenstaande is fout! Want ge moet ook nog u type opslaan (zie ProgramFactory createProgram) dus tzal eerder iets zijn als
	//  Map<String, array(Type, Variable)>
	// Want er kan per string maar variabele zijn
	public Program(){
		
	}
	
	private HashMap<VariableIdentifier, Variable<? extends Type>> globalVariables = new HashMap<>();
	
	public Variable<? extends Type> getVariable(VariableIdentifier id){
		if(!this.globalVariables.containsKey(id))
			return null;
		return this.globalVariables.get(id);
		
	}
	
	public <T> void setVariable(VariableIdentifier id, Variable<? extends Type> var){
		if(!globalVariables.containsKey(id))
			throw new IllegalArgumentException();
		this.globalVariables.put(id, var);
	}
}

