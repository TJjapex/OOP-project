package jumpingalien.program.expressions;

import java.util.Optional;
import java.util.stream.Stream;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import jumpingalien.model.helper.Collision;
import jumpingalien.model.helper.Orientation;
import jumpingalien.model.interfaces.IKind;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.part3.programs.IProgramFactory.Kind;
import jumpingalien.program.Program;
import jumpingalien.program.types.DirectionType;
import jumpingalien.program.types.ObjectType;

/**
 * A class of Search Objects as defined in a Program.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 * 
 */
public class SearchObject extends Expression<ObjectType>{
	
	/* Constructor */
	
	public SearchObject(Expression<DirectionType> direction, SourceLocation sourceLocation){
		super( sourceLocation);
		this.direction = direction;
	}
	
	/* Direction */
	
	@Basic @Immutable
	public Expression<DirectionType> getDirection() {
		return this.direction;
	}

	private final Expression<DirectionType> direction;

	/* Search */
	
	public boolean objectInRightDirection(IKind object, Program program) throws IllegalArgumentException{
		Orientation direction = getDirection().execute(program).getValue();
		IKind searcher = program.getGameObject();

		switch(direction){
		case LEFT:
			return object.getRoundedPositionX() < searcher.getRoundedPositionX();
		case RIGHT:
			return object.getRoundedPositionX() > searcher.getRoundedPositionX();
		case TOP:
			return object.getRoundedPositionY() > searcher.getRoundedPositionY();
		case BOTTOM:
			return object.getRoundedPositionY() < searcher.getRoundedPositionY();
		default:
			throw new IllegalArgumentException();
		}
	}
	
	public boolean objectInLineOfSight(IKind object, Program program) throws IllegalArgumentException{
		Orientation direction = getDirection().execute(program).getValue();
		IKind searcher = program.getGameObject();

		switch(direction){
		case LEFT:
		case RIGHT:
			return Collision.doPixelsOverlap(object.getRoundedPositionY(), object.getHeight(), searcher.getRoundedPositionY(), searcher.getHeight());
		case TOP:
		case BOTTOM:
			return Collision.doPixelsOverlap(object.getRoundedPositionX(), object.getWidth(), searcher.getRoundedPositionX(), searcher.getWidth());
		default:
			throw new IllegalArgumentException();
		}
	}
	
	public int getDistance(IKind object, Program program){
		Orientation direction = getDirection().execute(program).getValue();
		IKind searcher = program.getGameObject();

		switch(direction){
		case LEFT:
			return searcher.getRoundedPositionX() - ( object.getRoundedPositionX() + object.getWidth() );
		case RIGHT:
			return object.getRoundedPositionX() - searcher.getRoundedPositionX();
		case TOP:
			return object.getRoundedPositionY() - searcher.getRoundedPositionY();
		case BOTTOM:
			return searcher.getRoundedPositionY() - ( object.getRoundedPositionY() + object.getHeight() );
		default:
			throw new IllegalArgumentException();
		}
	}
	
	/* Execution */
	
	@Override
	public ObjectType execute(Program program) throws IllegalStateException{
		Stream.Builder<IKind> builder = Stream.builder();
		
		for (IKind object: ObjectType.getObjects(Kind.ANY, program)){
			builder.accept(object);
		}
		
		Stream<IKind> stream = builder.build();
		Optional<IKind> result = stream
			.filter( // Filter items not in right direction
				o -> { return objectInRightDirection(o, program); }
			).filter( // Filter items not in direct line of sight
				o -> { return objectInLineOfSight(o, program); }
			).reduce( // Find closest object
					(closestObject, o) -> { return (getDistance(o, program) < getDistance(closestObject, program)) ? o : closestObject; }
			);
		
		if(! result.isPresent()){
			throw new IllegalStateException("No object found in given direction, but assumed Game world would have solid tiles as borders!");
		}
		
		return new ObjectType(result.get());
	}
	
}
