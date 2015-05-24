package jumpingalien.program.types;

/**
 * A class of Types as defined in a Program.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 * 
 */
public abstract class Type {
	
	public abstract BooleanType typeEquals(Type o);
	
	/* Object method override */
	
	@Override
	public abstract String toString();
	
	@Override
	public abstract int hashCode();
	
	@Override
	public abstract boolean equals(Object o);
	
}
