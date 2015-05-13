package jumpingalien.model.program.types;

public class DoubleType extends Type{
	public DoubleType(double value){
		this.value = value;
	}
	
	public DoubleType(){
		this(0.0);
	}
	
	public double getValue() {
		return value;
	}

	private final double value;
	
	public boolean equals(Object o){
		if(! ( o instanceof DoubleType ) ){
			return false; // TODO eventueel comparen met de overeenkomstige echte types (dus BooleanType met Boolean enzo)
		}
		
		if(this.getValue() == ((DoubleType) o).getValue()){
			return true;
		}
		
		return false;
	}

}
