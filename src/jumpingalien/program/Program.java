package jumpingalien.program;

import java.util.HashMap;
import java.util.Map;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import jumpingalien.model.GameObject;
import jumpingalien.model.exceptions.ProgramRuntimeException;
import jumpingalien.program.statements.*;
import jumpingalien.program.types.Type;

/**
 * A class of Programs for game objects in the World of Mazub.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 * 
 */
public class Program {
	
	/* Constructor */
	
	public Program(Statement mainStatement, Map<String, Type> globalVariables){
		assert mainStatement != null;
		assert globalVariables != null;
		
		this.mainStatement = mainStatement;
		this.initialGlobalVariables = globalVariables;
		this.globalVariables = globalVariables;

	}
	
	/* Global variables */

	@Basic
	public Type getVariable(String name){
		if(!this.globalVariables.containsKey(name))
			return null;
		return this.globalVariables.get(name);
		
	}
	
	@Basic
	public void setVariable(String name, Type value){
		if(!globalVariables.containsKey(name))
			throw new IllegalArgumentException();
		this.globalVariables.put(name, value);
	}
	
	private final Map<String, Type> initialGlobalVariables;
	private Map<String, Type> globalVariables = new HashMap<>();
	
	/* Game object relation */
	
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
		if(!hasError()){
			if( getMainStatement().iterator().hasNext() ){
				try {
					getMainStatement().execute(this);
				} catch (ProgramRuntimeException | ClassCastException e) {
					setHasError(true);
					System.err.println("Error in program!");
				}
			}else{
				this.restart();
				this.executeNext();
			}
		}		
	}
	
	@Basic
	private void restart(){
		this.getMainStatement().resetIterator();
		this.globalVariables = this.initialGlobalVariables;
	}
	
	/* Error */
	
	@Basic
	public boolean hasError() {
		return this.hasError;
	}

	@Basic
	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}

	private boolean hasError = false;
	
	/* Well formed */
	
	/**
	 * @note must be worked out totally
	 */
	public boolean isWellFormed(){
		return getMainStatement().isWellFormed(false, false);
	}
	
}

