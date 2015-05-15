package jumpingalien.model.program.expressions;

import jumpingalien.model.program.Program;
import jumpingalien.model.program.types.Type;
import jumpingalien.part3.programs.SourceLocation;

public abstract class Expression<T extends Type> {
	
	protected Expression(SourceLocation sourceLocation){
		this.sourceLocation = sourceLocation;
	}
	
	public abstract T execute(Program program);
	
	public SourceLocation getSourceLocation() {
		return sourceLocation;
	}

	private final SourceLocation sourceLocation;
	

    @SuppressWarnings("unchecked")
    public static <R> R cast(Object obj) throws IllegalArgumentException{
    	try{
    		return (R) obj;
    	}catch(ClassCastException exc){
    		throw new IllegalArgumentException();
    	}
    }
}
