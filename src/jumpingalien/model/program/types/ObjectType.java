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
}
