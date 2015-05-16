package jumpingalien.model.program.types;

public class BooleanType extends Type{
	
	public BooleanType(Boolean value){
		this.value = value;
	}
	
	public BooleanType(){
		this(false);
	}
	
	public Boolean getValue() {
		return value;
	}

	private final Boolean value;

	@Override
	public String toString() {
		return String.valueOf(this.getValue());
	}
	
	public BooleanType conjunct(BooleanType o){
		return new BooleanType( this.getValue() && o.getValue());
	}
	
	public BooleanType disjunct(BooleanType o){
		return new BooleanType( this.getValue() || o.getValue());
	}
	
	public BooleanType equals(Type o){
		if(! ( o instanceof BooleanType ) ){
			return new BooleanType(false);
		}
		
		return new BooleanType(((BooleanType) o).getValue() == this.getValue());
	}

	public BooleanType not() {
		return new BooleanType(!this.getValue());
	}
}
