package jumpingalien.model.program.statements;

import java.util.stream.Stream;

import jumpingalien.model.World;
import jumpingalien.model.program.Program;
import jumpingalien.model.program.expressions.Expression;
import jumpingalien.model.program.expressions.Variable;
import jumpingalien.model.program.types.GameObjectType;
import jumpingalien.part3.programs.SourceLocation;

public class ForEachDo extends Statement {

	// The body of a for-each loop shall not contain action statements.
	// For-each loops should be worked out using lambda expressions. -> streams???
	
	public ForEachDo(Kind kind, String loopVariableName, Expression<?> whereCondition,
				   	 Expression<?> sortCondition, Boolean ascending, Statement body, SourceLocation sourceLocation){
		super(sourceLocation);
		assert (sortCondition.execute(program) instanceof Double);
		this.loopVariableName = loopVariableName;
		this.whereCondition = whereCondition;
		this.sortCondition = sortCondition;
		this.ascending = ascending;
		this.body = body;
	}
	
	public String getLoopVariableName(){
		return this.loopVariableName;
	}
	
	private final String loopVariableName;
	
	public Expression<?> getWhereCondition(){
		return this.whereCondition;
	}
	
	private final Expression<?> whereCondition;
	
	public Expression<?> getSortCondition(){
		return this.sortCondition;
	}
	
	private final Expression<?> sortCondition;
	
	public Boolean mustBeAscending(){
		return this.ascending;
	}
	
	private final Boolean ascending;
	
	public Statement getBody(){
		return this.body;
	}
	
	private final Statement body;	
	
	// no ordering, no lambda expressions
	
	@Override
	void execute(Program program){
		for (Kind kind: World.getAll(kind)){ 
			if ( (Boolean) this.getWhereCondition().execute(program)){
				program.setVariable(this.getLoopVariableName(),
									new Object[]{ new GameObjectType() , new Variable<GameObjectType>(kind, this.getSourceLocation())});
				this.getBody().executeAll(program);
			}
		}
	}
	
	// version with ordering and lambda expressions
	
	void execute2(Program program){
		Stream.Builder<?> builder = Stream.builder();
		for (Kind kind: World.getAll(kind)){ // TODO: implement Kind en method to get all instances of some kind (return Kind)
			builder.accept(kind);
		}
		Stream<?> stream = builder.build();
		stream.forEach( e -> program.setVariable(this.getLoopVariableName(),
												 new Object[]{ new GameObjectType() , new Variable<GameObjectType>(ObjectType((Kind)e), this.getSourceLocation())}))
			  .filter( () -> (Boolean) this.getWhereCondition().execute()); // geen idee of dit zou werken: dat hij eerst da variabele verandert en dan voor elke verandere variabele filtert (goed!) of eerst alle variabelen verzet en dan pas filtert (fout!)
	
		// stream = stream.filter( () -> this.getWhereCondition()).sorted( (e1, e2) -> this.getSortCondition().execute(program).compareTo(
		//    this.getSortCondition().execute(program)));
		// stream.forEach(e -> this.getBody().executeAll(program)); -> problemen omdat lokale variabele elke iteration verandert
	}
	
}
