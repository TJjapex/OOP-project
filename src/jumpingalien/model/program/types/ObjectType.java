package jumpingalien.model.program.types;

import java.util.HashSet;
import java.util.Set;

import jumpingalien.model.Buzam;
import jumpingalien.model.GameObject;
import jumpingalien.model.IKind;
import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.Shark;
import jumpingalien.model.Slime;
import jumpingalien.model.Tile;
import jumpingalien.model.program.Program;
import jumpingalien.part3.programs.IProgramFactory.Kind;

public class ObjectType extends Type {
	
	public ObjectType(IKind value){
		this.value = value;
	}
		
	public ObjectType(){
			this(null);
	}
	
	public IKind getValue() {
			return this.value;
	}
	
	private final IKind value;
	
	@Override
	public String toString() {
		if(this.getValue() == null)
			return "null";

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
	public static Set<IKind> getObjects(Kind kind, Program program){
		switch (kind) {
		case MAZUB:
			HashSet<IKind> mazubSet= new HashSet<IKind>();
			mazubSet.add(Mazub.getInWorld(program.getGameObject().getWorld()));
			return mazubSet;
		case BUZAM:
			return new HashSet<IKind> (Buzam.getAllInWorld(program.getGameObject().getWorld()));
		case SLIME:
			return new HashSet<IKind> (Slime.getAllInWorld(program.getGameObject().getWorld()));
		case SHARK:
			return new HashSet<IKind> (Shark.getAllInWorld(program.getGameObject().getWorld()));
		case PLANT:
			return new HashSet<IKind> (Plant.getAllInWorld(program.getGameObject().getWorld()));
		case TERRAIN:
			return new HashSet<IKind> (program.getGameObject().getWorld().getAllTiles());
		case ANY:
			// TODO waarom hier niet World.getAllGameObjects gebruiken?
			HashSet<IKind> anySet= new HashSet<IKind>();
			anySet.add(Mazub.getInWorld(program.getGameObject().getWorld()));
			anySet.addAll(Buzam.getAllInWorld(program.getGameObject().getWorld()));
			anySet.addAll(Slime.getAllInWorld(program.getGameObject().getWorld()));
			anySet.addAll(Shark.getAllInWorld(program.getGameObject().getWorld()));
			anySet.addAll(Plant.getAllInWorld(program.getGameObject().getWorld()));
			anySet.addAll(program.getGameObject().getWorld().getAllTiles());
			return anySet;
		default:
			throw new IllegalArgumentException();
		}
	}

	
}
