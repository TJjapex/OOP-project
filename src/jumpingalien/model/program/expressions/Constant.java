package jumpingalien.model.program.expressions;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import jumpingalien.model.program.*;
import jumpingalien.model.program.types.*;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class of Constants as defined in a Program.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 * 
 */
public class Constant<T extends Type> extends Expression<T>{	

	public Constant(T value, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.value = value;
	}
	
	@Basic @Immutable
	public T getValue(){
		return this.value;
	}
	
	private final T value;

	@Override
	public T execute(final Program program) {
		return this.getValue();
	}
	
}
