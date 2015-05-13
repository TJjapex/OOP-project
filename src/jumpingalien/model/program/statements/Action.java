package jumpingalien.model.program.statements;

import java.util.function.BiFunction;
import java.util.function.Function;

import jumpingalien.model.GameObject;
import jumpingalien.model.helper.Orientation;
import jumpingalien.model.program.Program;
import jumpingalien.model.program.expressions.Expression;
import jumpingalien.part3.programs.SourceLocation;

public class Action extends Statement {

	public Action(Function<GameObject, Void> operator, GameObject gameObject, Expression<?> waitTime, Boolean skip, SourceLocation sourceLocation){
		super(sourceLocation);
		this.operator = operator;
		this.gameObject = gameObject;
		this.waitTime = waitTime.execute(program);
		this.skip = skip;
	}
	
	public Action(Function<GameObject, Void> operator, GameObject gameObject, SourceLocation sourceLocation){
		this(operator, gameObject, 0.0,false, sourceLocation); // waitTime moet expression zijn! bv. new constant(0.0) -> moet nog geimplementeerd worden
	}
	
	public Double getWaitTime(){
		return this.waitTime;
	}
	
	private Double waitTime;
	
	public Boolean getSkip(){
		return this.skip;
	}
	
	private Boolean skip;
	
	public Function<GameObject, Void> getOperator(){
		return this.operator;
	}
	
	private final Function<GameObject, Void> operator;
	
	public GameObject getGameObject(){
		return this.gameObject;
	}
	
	private final GameObject gameObject;
	
	@Override
	public void execute(Program program) {
		if (!this.getSkip() && this.getWaitTime() == 0.0)
			this.getOperator().apply(this.getGameObject());
		// TODO: implement skip and wait actions!
	}
	
}
