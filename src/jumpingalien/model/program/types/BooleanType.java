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

}
