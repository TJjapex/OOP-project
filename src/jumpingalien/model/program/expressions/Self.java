package jumpingalien.model.program.expressions;

import jumpingalien.model.program.Program;
import jumpingalien.model.program.types.ObjectType;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class of Selfs as defined in a Program.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 * 
 */
public class Self<T> extends Expression<ObjectType>{
	
	/* Constructor */
	
	public Self(SourceLocation sourceLocation){
		super(sourceLocation);
	}

	/* Execution */
	
	@Override
	public ObjectType execute(final Program program) {
		return new ObjectType( program.getGameObject() );
	}	
	
}
