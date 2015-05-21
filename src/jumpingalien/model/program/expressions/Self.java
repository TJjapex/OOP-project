package jumpingalien.model.program.expressions;

import jumpingalien.model.program.Program;
import jumpingalien.model.program.types.ObjectType;
import jumpingalien.part3.programs.SourceLocation;

public class Self<T> extends Expression<ObjectType>{
	
	public Self(SourceLocation sourceLocation){
		super(sourceLocation);
	}

	@Override
	public ObjectType execute(final Program program) {
		return new ObjectType( program.getGameObject() );
	}	
	
}
