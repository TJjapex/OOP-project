package jumpingalien.model.program.types;

import jumpingalien.model.GameObject;

public class ObjectType extends Type {

	// Geen idee of da wel lekker werkt met die Kind enzo want dan meot ge een GameObject omzetten naar zo'n kind ofzo? Heb het tijdelijk weggedaan
	
//	public ObjectType(Kind value){
//		this.value = value;
//	}
//	
//	public ObjectType(){
//		this(null);
//	}
//	
//	public Kind getValue() {
//		return this.value;
//	}
//
//	private final Kind value;
	

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
	
	@Override
	public String toString() {
		return this.getValue().toString();
	}
	
	@Override
	public BooleanType equals(Type o){
		if(! ( o instanceof Object ) ){
			return new BooleanType(false);
		}
		
		return new BooleanType(((ObjectType) o).getValue() == this.getValue());
	}
	
}
