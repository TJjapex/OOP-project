package jumpingalien.model.program.statements;

import jumpingalien.model.program.Program;
import jumpingalien.model.program.expressions.*;
import jumpingalien.model.program.types.BooleanType;
import jumpingalien.part3.programs.SourceLocation;

public class IfThen extends Statement {

	public IfThen(Expression<?> condition, Statement ifBody, Statement elseBody, SourceLocation sourceLocation){
		super(sourceLocation);
		this.condition = (Expression<BooleanType> ) condition;
		this.ifBody = ifBody;
		this.elseBody = elseBody;
	}
	
	public Expression<BooleanType> getCondition(){
		return this.condition;
	}
	
	Expression<BooleanType> condition;
	
	public Statement getIfBody(){
		return this.ifBody;
	}
	
	Statement ifBody;
	
	public Statement getElseBody(){
		return this.elseBody;
	}
	
	public boolean hasElseBody(){
		return this.elseBody != null;
	}
	
	Statement elseBody;
	
	@Override
	public void execute(Program program){
		System.out.println("IfThen, executing, evaluating condition result: "+ this.getCondition().execute(program));
		
		// TODO zie docs
		
		// TODO dit gaat ervanuit dat this.getCondition().execute() een Type teruggeeft, wat bij een illegale code ook een expression kan zijn. Check doen?
		if( this.getCondition().execute(program).getValue() ){
			this.getIfBody().execute(program);
			
		} else if(this.hasElseBody()){
			this.getElseBody().execute(program);
		}
	}
	
}
