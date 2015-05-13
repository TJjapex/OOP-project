package jumpingalien.model.program.statements;

import jumpingalien.model.program.Program;
import jumpingalien.model.program.expressions.*;
import jumpingalien.part3.programs.SourceLocation;

public class WhileDo extends Statement {

	public WhileDo(Expression<?> condition, Statement body, SourceLocation sourceLocation){
		super(sourceLocation);
		this.condition = condition;
		this.body = body;
	}
	
	public Expression<?> getCondition(){
		return this.condition;
	}
	
	private final Expression<?> condition;
	
	public Statement getBody(){
		return this.body;
	}
	
	private final Statement body;
	
	@Override
	void execute(Program program){
		while ( (Boolean) this.getCondition().execute(program) ){
			this.getBody().executeAll(program);
		}
	}
	
}
