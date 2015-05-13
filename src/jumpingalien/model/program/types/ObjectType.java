package jumpingalien.model.program.types;

import jumpingalien.model.GameObject;

public class ObjectType extends Type {

	public ObjectType(Kind value){
		this.value = value;
	}
	
	public ObjectType(){
		this(null);
	}
	
	public Kind getValue() {
		return this.value;
	}

	private final Kind value;

}
