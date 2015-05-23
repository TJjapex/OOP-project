package jumpingalien.program.types;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Value;
import jumpingalien.model.helper.Orientation;;

/**
 * A class of Direction Types as defined in a Program.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 * 
 */
@Value
public class DirectionType extends Type {

	/* Constructor */
	
	public DirectionType(Orientation value){
		this.value = value;
	}
	
	public DirectionType(jumpingalien.part3.programs.IProgramFactory.Direction value) throws IllegalArgumentException{
		this(DirectionType.directionToOrientation(value));
	}

	/* Convert Direction to Orientation */
	
	public static Orientation directionToOrientation(jumpingalien.part3.programs.IProgramFactory.Direction direction) throws IllegalArgumentException{
		switch(direction){
		case LEFT:
			return Orientation.LEFT;
		case RIGHT:
			return Orientation.RIGHT;
		case UP:
			return Orientation.TOP;
		case DOWN:
			return Orientation.BOTTOM;
		default:
			throw new IllegalArgumentException();
		}
	}
	
	
	/* Value */
	@Basic @Immutable
	public Orientation getValue() {
		return this.value;
	}
	
	private final Orientation value;
	
	/* Object method overrides */
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DirectionType other = (DirectionType) obj;
		return this.typeEquals(other).getValue();
	}

	@Override
	public BooleanType typeEquals(Type o){
		if(! ( o instanceof DirectionType ) ){
			return new BooleanType(false);
		}
		
		return new BooleanType(((DirectionType) o).getValue() == this.getValue());
	}	
	
	@Immutable @Override
	public String toString() {
		return String.valueOf(this.getValue());
	}
	
	@Immutable @Override
	public int hashCode() {
		return getValue().hashCode();
	}
		
}
