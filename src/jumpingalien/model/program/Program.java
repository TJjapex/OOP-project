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
		this.initialGlobalVariables = globalVariables;
		this.globalVariables = globalVariables;
		
	}
	
	private Map<String, Type> initialGlobalVariables = new HashMap<>();
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
	
	// GameObject relation 
	
	public GameObject getGameObject() {
		return this.gameObject;
	}
	
	public void setGameObject(GameObject gameObject){
		this.gameObject = gameObject;
	}

	private GameObject gameObject;

	private final Statement mainStatement;
	
	private void restart(){
		this.mainStatement.resetIterator();
		this.globalVariables = this.initialGlobalVariables;
	}
	
	public void executeNext(){
		if( this.mainStatement.iterator().hasNext() ){
			System.out.println("EXECUTING NEXT STATEMENT:");
			this.mainStatement.execute(this);
		}else{
			System.out.println("no next statement in mainStatement -> restart mainStatement");
			this.restart();
			this.executeNext();
		}
		
	}	
}

