package jumpingalien.model.program.types;

/**
 * A class of Double Types as defined in a Program.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 * 
 */
public class DoubleType extends Type{
	
	/* Constructor */
	
	public DoubleType(double value){
		this.value = value;
	}
	
	public DoubleType(int value){
		this.value = (double) value;
	}
	
	public DoubleType(){
		this(0.0);
	}
	
	/* Value */
	
	public double getValue() {
		return value;
	}
	
	private final double value;
	
	/* Object method overrides */
	
	@Override
	public String toString() {
		return String.valueOf(this.getValue());
	}
	
	@Override
	public BooleanType equals(Type o){
		if(! ( o instanceof DoubleType ) ){
			return new BooleanType(false);
		}
		
		return new BooleanType(((DoubleType) o).getValue() == this.getValue());
	}	
	
	/* Operations */
	
	public DoubleType add(DoubleType o){
		return new DoubleType( this.getValue() + o.getValue() );
	}
	
	public DoubleType subtract(DoubleType o){
		return new DoubleType( this.getValue() - o.getValue() );
	}
	
	public DoubleType multiply(DoubleType o){
		return new DoubleType( this.getValue() * o.getValue() );
	}
	
	public DoubleType divide(DoubleType o){
		return new DoubleType( this.getValue() / o.getValue() );
	}
	
	public DoubleType sqrt(){
		return new DoubleType( Math.sqrt(this.getValue()) );
	}
	
	public BooleanType lessThan(DoubleType o){
		return new BooleanType( this.getValue() < o.getValue());
	}
	
	public BooleanType lessThanOrEqualTo(DoubleType o){
		return new BooleanType( this.getValue() <= o.getValue());
	}
	
	public BooleanType greaterThan(DoubleType o){
		return new BooleanType( this.getValue() > o.getValue());
	}
	
	public BooleanType greaterThanOrEqualTo(DoubleType o){
		return new BooleanType( this.getValue() >= o.getValue());
	}
	
	public BooleanType notEquals(DoubleType o){
		return new BooleanType( this.getValue() != o.getValue());
	}
	
}
