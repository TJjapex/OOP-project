package jumpingalien.model.program.expressions;

import jumpingalien.model.GameObject;
import jumpingalien.model.program.Program;
import jumpingalien.part3.programs.SourceLocation;

public class Self<T> extends Expression<GameObject>{
	public Self(SourceLocation sourceLocation){
		super(sourceLocation);
	}

	@Override
	public GameObject execute(Program program) {
		return program.getGameObject();
	}	
}
