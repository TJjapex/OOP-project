package jumpingalien.model.program;

import java.util.HashMap;
import java.util.Map;




import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import jumpingalien.model.GameObject;
import jumpingalien.model.program.statements.*;
import jumpingalien.model.program.types.Type;

public class Program {
	
	public Program(Statement mainStatement, Map<String, Type> globalVariables){
		assert mainStatement != null;
		assert globalVariables != null;
		
		this.mainStatement = mainStatement;
		this.initialGlobalVariables = globalVariables;
		this.globalVariables = globalVariables;
		//System.out.println("Program well formed: " + this.isWellFormed());
	}
	
	/* Global variables */
	
	private final Map<String, Type> initialGlobalVariables;
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
	
	/* GameObject relation */
	@Basic
	public GameObject getGameObject() {
		return this.gameObject;
	}
	
	@Basic
	public void setGameObject(GameObject gameObject){
		assert gameObject.getProgram() == this;
		this.gameObject = gameObject;
	}

	private GameObject gameObject;
	
	/* Main statement */
	
	@Basic @Immutable
	public Statement getMainStatement() {
		return mainStatement;
	}
	
	private final Statement mainStatement;
	
	/* Execution */
	
	public void executeNext(){
		if( this.mainStatement.iterator().hasNext() ){
			getMainStatement().execute(this);
		}else{
			System.out.println("no next statement in mainStatement -> restart mainStatement");
			this.restart();
			this.executeNext();
		}
	}
	
	private void restart(){
		this.mainStatement.resetIterator();
		this.globalVariables = this.initialGlobalVariables;
	}
	
	/* Well formed */
	
	public boolean isWellFormed(){
		return getMainStatement().isWellFormed(false, false);
	}
	
}

