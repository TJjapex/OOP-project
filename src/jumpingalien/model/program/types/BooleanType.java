package jumpingalien.model.program.types;

/**
 * A class of Boolean Types as defined in a Program.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 * 
 */
public class BooleanType extends Type{
	
	/* Constructor */
	
	public BooleanType(Boolean value){
		this.value = value;
	}
	
	public BooleanType(){
		this(false);
	}
	
	/* Value */
	
	public Boolean getValue() {
		return value;
	}

	private final Boolean value;

	/* Object method overrides */
	
	@Override
	public String toString() {
		return String.valueOf(this.getValue());
	}
	
	@Override
	public BooleanType equals(Type o){
		if(! ( o instanceof BooleanType ) ){
			return new BooleanType(false);
		}
		
		return new BooleanType(((BooleanType) o).getValue() == this.getValue());
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
