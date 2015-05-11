package jumpingalien.model.program.types;

import jumpingalien.model.GameObject;

public class ObjectType extends Type {

	public ObjectType(GameObject value){
		this.value = value;
	}
	
	public ObjectType(){
		this(null);
	}
	
	public GameObject getValue() {
		return this.value;
	}

	private final GameObject value;

	
}
