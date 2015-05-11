package jumpingalien.model.program.types;

public class DoubleType extends Type{
	public DoubleType(Double value){
		this.value = value;
	}
	
	public DoubleType(){
		this(0.0);
	}
	
	public Double getValue() {
		return value;
	}

	private final Double value;

}
