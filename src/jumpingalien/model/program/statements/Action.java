package jumpingalien.model.program.statements;

import java.util.function.BiConsumer;

import jumpingalien.model.GameObject;
import jumpingalien.model.exceptions.ProgramRuntimeException;
import jumpingalien.model.program.Program;
import jumpingalien.model.program.expressions.Expression;
import jumpingalien.model.program.types.ObjectType;
import jumpingalien.part3.programs.SourceLocation;

public class Action extends Statement {

	public Action(BiConsumer<Object, Program> operator, Expression<ObjectType> gameObject, SourceLocation sourceLocation){
		super(sourceLocation);
		this.operator = operator;
		this.gameObject = gameObject;
	}
	
	public BiConsumer<Object, Program> getOperator(){
		return this.operator;
	}
	
	private final BiConsumer<Object, Program> operator;
	
	public Expression<ObjectType> getGameObject(){
		return this.gameObject;
	}
	
	private final Expression<ObjectType> gameObject;
	
	@Override
	public void execute(Program program) throws IllegalStateException{
		if(this.iterator().hasNext()){
				System.out.println("Program, ACTION");
			this.getOperator().accept( this.getGameObject().execute(program).getValue(), program);
			this.statementUsed = true;
		}else{
			throw new ProgramRuntimeException("Statement executed while not having next useful statement!");
		}
	
	}
	
}
