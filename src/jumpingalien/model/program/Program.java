package jumpingalien.model.program;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import jumpingalien.model.GameObject;
import jumpingalien.model.program.statements.*;
import jumpingalien.model.program.types.Type;

public class Program {
	
	// OUDE VERSIE
	
	public Program(Statement mainStatement, Map<String, Type> globalVariables){
		assert mainStatement != null;
		assert globalVariables != null;
		
		this.mainStatement = mainStatement;
		this.globalVariables = globalVariables;
		this.mainStatementIterator = ((Statement) mainStatement).iterator();
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
		System.out.println("Program, variable set "+name+ " to " + value);
		this.globalVariables.put(name, value);
	}
	
	// NIEUWE VERSIE
	//
	// private HashMap<String, Variable<?>> variables = new HashMap<>();
	//  bovenstaande is fout! Want ge moet ook nog u type opslaan (zie ProgramFactory createProgram) dus tzal eerder iets zijn als
	//  Map<String, array(Type, Variable)> want er kan per string maar één variabele zijn
	// -> BELANGRIJKE VERANDERING
	//  Keys van globalvariables: String
	//	Values van globalvariables: ArrayList(Type,Variable)
//	
//	public Program(Statement mainStatement, Map<String, Object[]> globalVariables){
//		this.mainStatement = mainStatement;
//		this.globalVariables = globalVariables;
//	}
//	
//	
	// GameObject relation 
	
	public GameObject getGameObject() {
		return this.gameObject;
	}
	
	public void setGameObject(GameObject gameObject){
		this.gameObject = gameObject;
	}

	private GameObject gameObject;

	private final Statement mainStatement;
	
	public void executeNext(){
		
		if( mainStatementIterator.hasNext() ){
			System.out.println("EXECUTING NEXT STATEMENT:");
			((Statement) mainStatementIterator.next()).execute(this);
		}else{
			System.out.println("no next statement in mainstatement -> execution finished");
		}
		
	}
	
	private Iterator<Statement> mainStatementIterator;
	
}

