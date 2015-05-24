package jumpingalien.program.types;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Value;

/**
 * A class of Boolean Types as defined in a Program.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 * 
 */
@Value
public class BooleanType extends Type{
	
	/* Constructor */
	
	public BooleanType(Boolean value){
		this.value = value;
	}
	
	/* Value */
	@Basic @Immutable
	public Boolean getValue() {
		return value;
	}

	private final Boolean value;

	/* Object method overrides */
	
	@Immutable @Override
	public String toString() {
		return String.valueOf(this.getValue());
	}
	
	@Override
	public BooleanType typeEquals(Type o){
		if(! ( o instanceof BooleanType ) ){
			return new BooleanType(false);
		}
		
		return new BooleanType(((BooleanType) o).getValue().equals(this.getValue()));
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BooleanType other = (BooleanType) obj;
		if (getValue() == null) {
			if (other.getValue() != null)
				return false;
		} else if (!getValue().equals(other.getValue()))
			return false;
		return true;
	}

	
	@Immutable @Override
	public int hashCode() {
		return getValue().hashCode();
	}
	
	/* Operations */
	
	public BooleanType not() {
		return new BooleanType(!this.getValue());
	}
	
	public BooleanType conjunct(BooleanType o){
		return new BooleanType( this.getValue() && o.getValue());
	}
	
	public BooleanType disjunct(BooleanType o){
		return new BooleanType( this.getValue() || o.getValue());
	}
	
}
