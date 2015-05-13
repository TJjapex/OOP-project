package jumpingalien.model.program.statements;

import jumpingalien.model.program.Program;
import jumpingalien.model.program.expressions.*;
import jumpingalien.model.program.types.BooleanType;
import jumpingalien.part3.programs.SourceLocation;

public class WhileDo extends Statement {

	public WhileDo(Expression<?> condition, Statement body, SourceLocation sourceLocation){
		super(sourceLocation);
		this.condition = (Expression<BooleanType>) condition;
		this.body = body;
	}
	
	public Expression<BooleanType> getCondition(){
		return this.condition;
	}
	
	private final Expression<BooleanType> condition;
	
	public Statement getBody(){
		return this.body;
	}
	
	private final Statement body;
	
	@Override
	public void execute(Program program){
		System.out.println("WhileDo, executing, evaluating condition: "+this.getCondition()+",  result of evalution: "+ this.getCondition().execute(program));
		
		// While hier mag niet want dan doet hij meerdere statements per execute
		// TODO volgens mij moet dit dan ook met een iterator, en hasNext()
		if ( this.getCondition().execute(program).getValue() ){
			System.out.println("WhileDo, executing body"+this.getBody());
			this.getBody().execute(program);
		}
	}
	
}
