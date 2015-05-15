package jumpingalien.model.program.expressions;

import jumpingalien.model.program.Program;
import jumpingalien.model.program.types.GameObjectType;
import jumpingalien.part3.programs.SourceLocation;

public class Self<T> extends Expression<GameObjectType>{
	
	public Self(SourceLocation sourceLocation){
		super(sourceLocation);
	}

	@Override
	public GameObjectType execute(Program program) {
		return new GameObjectType( program.getGameObject() );
	}	
	
}
