package jumpingalien.model.program;

import java.util.HashMap;
import java.util.Map;

import jumpingalien.model.program.expressions.Variable;
import jumpingalien.model.program.statements.Statement;
import jumpingalien.model.program.types.Type;

public class Program {
	
	// OUDE VERSIE
	
//	public Program(Statement mainStatement, Map<String, Type> globalVariables){
//		this.mainStatement = mainStatement;
//		this.globalVariables = globalVariables;
//	}
//	
//	private Map<String, Type> globalVariables = new HashMap<>();
//	
//	public Type getVariable(String name){
//		if(!this.globalVariables.containsKey(name))
//			return null;
//		return this.globalVariables.get(name);
//		
//	}
//	
//	public void setVariable(String name, Type value){
//		if(!globalVariables.containsKey(name))
//			throw new IllegalArgumentException();
//		this.globalVariables.put(name, value);
//	}
	
	// NIEUWE VERSIE
	//
	// private HashMap<String, Variable<?>> variables = new HashMap<>();
	//  bovenstaande is fout! Want ge moet ook nog u type opslaan (zie ProgramFactory createProgram) dus tzal eerder iets zijn als
	//  Map<String, array(Type, Variable)> want er kan per string maar één variabele zijn
	// -> BELANGRIJKE VERANDERING
	//  Keys van globalvariables: String
	//	Values van globalvariables: ArrayList(Type,Variable)
	
	public Program(Statement mainStatement, Map<String, Object[]> globalVariables){
		this.mainStatement = mainStatement;
		this.globalVariables = globalVariables;
	}
	
	private Map<String, Object[]> globalVariables = new HashMap<>();
	
	public Variable<?> getVariable(String name){
		if(!this.globalVariables.containsKey(name))
			return null;
		return (Variable<?>) this.globalVariables.get(name)[1]; // is er een andere methode dan dit met een cast te doen?
	}
	
	public void setVariable(String name, Object[] typeAndVariable){
//		if(!globalVariables.containsKey(name))
//			throw new IllegalArgumentException(); -> het moet ook nog mogelijk zijn om variables toe te voegen tijdens het 
//													 programma, e.g. loopvariable
		if(!(typeAndVariable[0] instanceof Type || typeAndVariable[0] instanceof Integer)
		   || !(typeAndVariable[1] instanceof Variable) )
			throw new IllegalArgumentException();
		this.globalVariables.put(name, typeAndVariable);
	}
	
	private final Statement mainStatement;
}

