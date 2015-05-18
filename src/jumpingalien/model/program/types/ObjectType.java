package jumpingalien.model.program.types;

import java.util.HashSet;
import java.util.Set;

import jumpingalien.model.Buzam;
import jumpingalien.model.GameObject;
import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.Shark;
import jumpingalien.model.Slime;
import jumpingalien.model.program.Program;
import jumpingalien.part3.programs.IProgramFactory.Kind;

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
	

	public ObjectType(Object value){
		this.value = value;
	}
		
	public ObjectType(){
			this(null);
	}
	
	public Object getValue() {
			return this.value;
	}
	
	private final Object value;
	
	@Override
	public String toString() {
		return this.getValue().toString();
	}
	
	@Override
	public BooleanType equals(Type o){
		if(! ( o instanceof ObjectType ) ){
			return new BooleanType(false);
		}
		
		return new BooleanType(((ObjectType) o).getValue() == this.getValue());
	}
	
	
	// TODO niet echt proper dat deze gewoon een set van objects returned ipv objecttype maar anders moet er zoveel verandert worden...
	public static Set<Object> getObjects(Kind kind, Program program){
		switch (kind) {
		case MAZUB:
			HashSet<Object> mazubSet= new HashSet<Object>();
			mazubSet.add(Mazub.getInWorld(program.getGameObject().getWorld()));
			return mazubSet;
		case BUZAM:
			return new HashSet<Object> (Buzam.getAllInWorld(program.getGameObject().getWorld()));
		case SLIME:
			return new HashSet<Object> (Slime.getAllInWorld(program.getGameObject().getWorld()));
		case SHARK:
			return new HashSet<Object> (Shark.getAllInWorld(program.getGameObject().getWorld()));
		case PLANT:
			return new HashSet<Object> (Plant.getAllInWorld(program.getGameObject().getWorld()));
		case TERRAIN:
			// TODO
			throw new IllegalArgumentException();
			//for(Terrain terrain : program.getGameObject().getWorld().getAllGeologicalFeatures());....
		default:
			throw new IllegalArgumentException();
		}
	}

	
}
