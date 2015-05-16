package jumpingalien.model.program.statements;

import java.util.function.BiConsumer;

import jumpingalien.model.GameObject;
import jumpingalien.model.program.Program;
import jumpingalien.model.program.expressions.Expression;
import jumpingalien.model.program.types.GameObjectType;
import jumpingalien.part3.programs.SourceLocation;

public class Action extends Statement {

	public Action(BiConsumer<GameObject, Program> operator, Expression<GameObjectType> gameObject, SourceLocation sourceLocation){
		super(sourceLocation);
		this.operator = operator;
		this.gameObject = gameObject;
	}
	
	public BiConsumer<GameObject, Program> getOperator(){
		return this.operator;
	}
	
	private final BiConsumer<GameObject, Program> operator;
	
	public Expression<GameObjectType> getGameObject(){
		return this.gameObject;
	}
	
	private final Expression<GameObjectType> gameObject;
	
	@Override
	public void execute(Program program) throws IllegalStateException{
		if(this.iterator().hasNext()){
				System.out.println("Program, ACTION");
			this.getOperator().accept(this.getGameObject().execute(program).getValue(), program);
			this.statementUsed = true;
		}else{
			throw new IllegalStateException("Statement executed while not having next useful statement!");
		}
	
	}
	
}
