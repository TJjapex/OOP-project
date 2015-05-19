package jumpingalien.model.program.expressions;


import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.stringtemplate.v4.compiler.STParser.ifstat_return;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import jumpingalien.model.GameObject;
import jumpingalien.model.IKind;
import jumpingalien.model.helper.Orientation;
import jumpingalien.model.program.Program;
import jumpingalien.model.program.types.DirectionType;
import jumpingalien.model.program.types.DoubleType;
import jumpingalien.model.program.types.ObjectType;
import jumpingalien.model.program.types.Type;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.part3.programs.IProgramFactory.Kind;
import jumpingalien.part3.programs.IProgramFactory.SortDirection;

public class SearchObject extends Expression<ObjectType>{
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

	/* Execution */
	
	@Override
	public ObjectType execute(Program program) throws IllegalStateException{
		
//		IKind searcher = program.getGameObject();
//		Set<IKind> objects = ObjectType.getObjects(Kind.ANY, program);	
//		Orientation direction = getDirection().execute(program).getValue();
//		
//		int closestDistance = 0;
//		IKind closestObject = null;
//		
//		
//		for(IKind object: objects){
//			int thisDistance = Integer.MAX_VALUE;
//			switch(direction){
//			case LEFT:
//				if(object.getRoundedPositionX() < searcher.getRoundedPositionX()){
//					if(GameObject.doPixelsOverlap(object.getRoundedPositionY(), object.getHeight(), searcher.getRoundedPositionY(), searcher.getHeight())){
//						thisDistance = searcher.getRoundedPositionX() - ( object.getRoundedPositionX() + object.getWidth() );
//					}
//				}
//				break;
//			case RIGHT:
//				if(object.getRoundedPositionX() > searcher.getRoundedPositionX()){
//					if(GameObject.doPixelsOverlap(object.getRoundedPositionY(), object.getHeight(), searcher.getRoundedPositionY(), searcher.getHeight())){
//						thisDistance = object.getRoundedPositionX()- searcher.getRoundedPositionX();
//					}
//				}
//				break;
//			case TOP:
//				break;
//			case BOTTOM:
//				break;
//			}
//			
//			if(thisDistance < closestDistance){
//				closestDistance = thisDistance;
//				closestObject = object;
//			}
//			
//		}
//		
//		return new ObjectType(closestObject);
		

		// Implementatie met streams
		Stream.Builder<IKind> builder = Stream.builder();
		
		// TODO method zoeken om gewoon in een keer toe te voegen?
		for (IKind object: ObjectType.getObjects(Kind.ANY, program)){
			builder.accept(object);
		}
		
		Stream<IKind> stream = builder.build();
		Optional<IKind> result = stream
			.filter( // Filter items not in right direction
				o -> { return objectInRightDirection(o, program); }
			).filter( // Filter items not in direct line of sight
				o -> { return objectInRightDirection(o, program); }
			).reduce( // Finds closest object
					(closestObject, o) -> { return (getDistance(o, program) < getDistance(closestObject, program)) ? o : closestObject; }
			);
		
		if(! result.isPresent()){
			throw new IllegalStateException("No object found in given direction, but assumed Game world would have solid tiles as borders!");
		}
		
		return new ObjectType(result.get());
	}
	
	public boolean objectInRightDirection(IKind object, Program program) throws IllegalArgumentException{
		Orientation direction = getDirection().execute(program).getValue();
		IKind searcher = program.getGameObject(); // Bad name but couldn't think of anything else

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
		IKind searcher = program.getGameObject(); // Bad name but couldn't think of anything else

		switch(direction){
		case LEFT:
		case RIGHT:
			return GameObject.doPixelsOverlap(object.getRoundedPositionY(), object.getHeight(), searcher.getRoundedPositionY(), searcher.getHeight());
		case TOP:
		case BOTTOM:
			return GameObject.doPixelsOverlap(object.getRoundedPositionX(), object.getWidth(), searcher.getRoundedPositionX(), searcher.getWidth());
		default:
			throw new IllegalArgumentException();
		}
	}
	
	
	/**
	 * Returns the distance between the object the program is executed on and the given object. (distance calculation varies according to search direction)
	 * @param object
	 * @param program
	 * @return
	 */
	public int getDistance(IKind object, Program program){
		Orientation direction = getDirection().execute(program).getValue();
		IKind searcher = program.getGameObject(); // Bad name but couldn't think of anything else

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
	
}
