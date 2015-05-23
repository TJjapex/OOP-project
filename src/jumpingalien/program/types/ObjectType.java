package jumpingalien.program.types;

import java.util.HashSet;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Value;
import jumpingalien.model.Buzam;
import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.Shark;
import jumpingalien.model.Slime;
import jumpingalien.model.interfaces.IKind;
import jumpingalien.part3.programs.IProgramFactory.Kind;
import jumpingalien.program.Program;

/**
 * A class of Object Types as defined in a Program.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 * 
 */
@Value
public class ObjectType extends Type {
	
	/* Constructor */
	
	public ObjectType(IKind value){
		this.value = value;
	}
	
	/* Value */
	@Basic @Immutable
	public IKind getValue() {
		return this.value;
	}
	
	private final IKind value;
	
	/* Object method overrides */
	
	@Immutable @Override
	public String toString() {
		if(this.getValue() == null)
			return "null";

		return this.getValue().toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ObjectType other = (ObjectType) obj;
		if (getValue() == null) {
			if (other.getValue() != null)
				return false;
		} else if (!getValue().equals(other.getValue()))
			return false;
		return true;
	}

	@Override
	public BooleanType typeEquals(Type o){
		if(! ( o instanceof ObjectType ) ){
			return new BooleanType(false);
		}
		
		return new BooleanType(((ObjectType) o).getValue() == this.getValue());
	}
	
	@Override
	public int hashCode(){
		if(this.getValue() == null)
			return 0;
		return this.getValue().hashCode();
	}
	
	
	/* Get all objects of a Kind */
	
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
			HashSet<IKind> anySet= new HashSet<IKind>();
			anySet.addAll(program.getGameObject().getWorld().getAllGameObjects());
			anySet.addAll(program.getGameObject().getWorld().getAllTiles());
			return anySet;
		default:
			throw new IllegalArgumentException();
		}
	}
	
}
