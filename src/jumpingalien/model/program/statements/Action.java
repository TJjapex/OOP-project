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
//		this.waitTime = waitTime.execute(program);
//		this.skip = skip;
	}
	
//	public Action(Function<GameObject, Void> operator, Expression<ObjectType> expression, SourceLocation sourceLocation){
//		this(operator, expression, 0.0,false, sourceLocation); // waitTime moet expression zijn! bv. new constant(0.0) -> moet nog geimplementeerd worden
//	}
//	
	// TODO, wait in aparte klasse, skip is gewoon wait 1 (? nog niet gecheckt) dus kan met dezelfde klasse (gewoon in programfactory time = 1 zetten)
//	public Double getWaitTime(){
//		return this.waitTime;
//	}
//	
//	private Double waitTime;
//	
//	public Boolean getSkip(){
//		return this.skip;
//	}
//	
//	private Boolean skip;
	
	public BiConsumer<GameObject, Program> getOperator(){
		return this.operator;
	}
	
	private final BiConsumer<GameObject, Program> operator;
	
	public Expression<GameObjectType> getGameObject(){
		return this.gameObject;
	}
	
	private final Expression<GameObjectType> gameObject;
	
	@Override
	public void execute(Program program) {
		this.getOperator().accept(this.getGameObject().execute(program).getValue(), program);
		this.statementUsed = true;
	}
	
}
