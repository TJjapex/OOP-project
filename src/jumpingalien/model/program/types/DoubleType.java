package jumpingalien.model.program.types;

public class DoubleType extends Type{
	
	public DoubleType(double value){
		this.value = value;
	}
	
	public DoubleType(int value){
		this.value = (double) value;
	}
	
	public DoubleType(){
		this(0.0);
	}
	
	public double getValue() {
		return value;
	}
	
	private final double value;
	
	@Override
	public String toString() {
		return String.valueOf(this.getValue());
	}
	
//	public boolean equals(Object o){
//		if(! ( o instanceof DoubleType ) ){
//			return false; // TODO eventueel comparen met de overeenkomstige echte types (dus BooleanType met Boolean enzo)
//		}
//		
//		if(this.getValue() == ((DoubleType) o).getValue()){
//			return true;
//		}
//		
//		return false;
//	}
//	
//	public BooleanType equalsAsBooleanType(Object o){
//		return new BooleanType(this.equals(o));
//	}
	
	public DoubleType add(DoubleType o){
		return new DoubleType( this.getValue() + o.getValue() );
	}
	
	public BooleanType equals(Type o){
		if(! ( o instanceof DoubleType ) ){
			return new BooleanType(false);
		}
		
		return new BooleanType(((DoubleType) o).getValue() == this.getValue());
	}	
	
}
