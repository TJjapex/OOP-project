package jumpingalien.model.program.types;

import jumpingalien.model.helper.Orientation;;

public class DirectionType extends Type {

	public DirectionType(Orientation value){
		this.value = value;
	}
	
	public DirectionType(jumpingalien.part3.programs.IProgramFactory.Direction value) throws IllegalArgumentException{
		switch(value){
		case LEFT:
			this.value = Orientation.LEFT;
		break;
		case RIGHT:
			this.value = Orientation.RIGHT;
		break;
		case UP:
			this.value = Orientation.TOP;
		break;
		case DOWN:
			this.value = Orientation.BOTTOM;
		break;
		default:
			throw new IllegalArgumentException();
		}
	}
	
	public DirectionType(){
		this(Orientation.RIGHT);
	}
	
	public Orientation getValue() {
		return this.value;
	}
	
	private final Orientation value;
	
	@Override
	public String toString() {
		return String.valueOf(this.getValue());
	}
	
	@Override
	public BooleanType equals(Type o){
		if(! ( o instanceof DirectionType ) ){
			return new BooleanType(false);
		}
		
		return new BooleanType(((DirectionType) o).getValue() == this.getValue());
	}

}
