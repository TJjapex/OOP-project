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

	
	// TODO dirty ma 't moet gewoon zo snel mogelijk werken allemaal :)
//	@Override
//	public boolean equals(Object o){
//		if(! ( o instanceof Type ) ){
//			return false; // TODO eventueel comparen met de overeenkomstige echte types (dus BooleanType met Boolean enzo)
//		}
//		
//		if(this.getValue() == ((Type) o).getValue()){
//			return BooleanType
//		}
//		
//		return false;
//	}
}
