package jumpingalien.model.program.expressions;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import jumpingalien.model.program.Program;
import jumpingalien.model.program.types.Type;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class of Expressions as defined in a Program.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 * 
 */
public abstract class Expression<T extends Type> {
	
	/* Constructor */
	
	protected Expression(SourceLocation sourceLocation){
		this.sourceLocation = sourceLocation;
	}
	
	/* Source location */
	
	@Basic @Immutable
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}

	private final SourceLocation sourceLocation;

	/* Cast */
	
    @SuppressWarnings("unchecked")
    public static <R> R cast(Object obj) throws IllegalArgumentException{
    	try{
    		return (R) obj;
    	}catch(ClassCastException exc){
    		throw new IllegalArgumentException();
    	}
    }
    
    /* Execution */
    
    public abstract T execute(final Program program);
    
}
