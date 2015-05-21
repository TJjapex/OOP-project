package jumpingalien.model.program.statements;

import org.antlr.v4.codegen.model.chunk.ThisRulePropertyRef_ctx;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import jumpingalien.model.program.Program;
import jumpingalien.model.program.expressions.*;
import jumpingalien.model.program.types.*;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class of Assignment Statements as defined in a Program.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 * 
 */
public class Assignment extends Statement {

	/* Constructor */
	
	public Assignment(String variableName, Type variableType,
			Expression<? extends Type> value, SourceLocation sourceLocation){
		super(sourceLocation);
		this.variableName = variableName;
		this.variableType = variableType;
		this.value = value;
	}
	
	/* Variable name */
	
	@Basic @Immutable
	public String getVariableName(){
		return this.variableName;
	}
	
	private final String variableName;
	
	/* Variable type */
	
	@Basic @Immutable
	public Type getType(){
		return this.variableType;
	}
		
	private final Type variableType;
	
	/* Value */
	
	@Basic @Immutable
	public Expression<? extends Type> getValue(){
		return this.value;
	}
		
	private final Expression<? extends Type> value;
	
	/* Execution */
	
	@Override
	public void execute(final Program program) throws IllegalStateException{
		if(this.iterator().hasNext()){
			program.setVariable(getVariableName(), getValue().execute(program));
			setStatementUsed(true);
		}else{
			throw new IllegalStateException("Statement executed while not having next useful statement!");
		}
		
	}
	
}
