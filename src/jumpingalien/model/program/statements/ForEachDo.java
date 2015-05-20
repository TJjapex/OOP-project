package jumpingalien.model.program.statements;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jumpingalien.model.IKind;
import jumpingalien.model.exceptions.ProgramRuntimeException;
import jumpingalien.model.program.Program;
import jumpingalien.model.program.expressions.Expression;
import jumpingalien.model.program.types.BooleanType;
import jumpingalien.model.program.types.DoubleType;
import jumpingalien.model.program.types.ObjectType;
import jumpingalien.part3.programs.IProgramFactory.Kind;
import jumpingalien.part3.programs.IProgramFactory.SortDirection;
import jumpingalien.part3.programs.SourceLocation;

public class ForEachDo extends Statement implements ILoop {

	public ForEachDo(Kind kind, String loopVariableName, Expression<BooleanType> whereCondition,
		   	 Expression<BooleanType> sortCondition, SortDirection sortDirection, Statement body, SourceLocation sourceLocation){
		super(sourceLocation);
		this.loopVariableName = loopVariableName;
		this.whereCondition = whereCondition;
		this.sortCondition = sortCondition;
		this.sortDirection = sortDirection;
		this.body = body;
		this.kind = kind;
	}
	
	/* Kind */
	
	private Kind kind;
	
	public Kind getKind(){
		return this.kind;
	}
	
	/* Loop variable */
	
	public String getLoopVariableName(){
		return this.loopVariableName;
	}
	
	private final String loopVariableName;
	
	public void loadLoopObject(Program program, ObjectType o){
		program.setVariable(getLoopVariableName(), o);
	}
	
	public void loadNextLoopObject(Program program){
		loadLoopObject(program, getObjectListIterator().next());
	}
	
	/* Where condition */
	
	public Expression<BooleanType> getWhereCondition(){
		return this.whereCondition;
	}
	
	private final Expression<BooleanType> whereCondition;
	
	/* Sort condition */
	
	public Expression<?> getSortCondition(){
		return this.sortCondition;
	}
	
	private final Expression<?> sortCondition;
	
	/* Sort direction */
	
	public SortDirection getSortDirection(){
		return this.sortDirection;
	}
	
	private final SortDirection sortDirection;
	
	/* Statement body */
	
	public Statement getBody(){
		return this.body;
	}
	
	private final Statement body;	
	
	/* Execution */
	
	public void execute(Program program) throws ProgramRuntimeException{
		if(!hasObjectListIterator()){
			//System.out.println("Foreach, object list built");
			setObjectListIterator(buildObjectList(program).iterator());
			
			// Load in first looping variable
			if (this.getObjectListIterator().hasNext()){
				loadNextLoopObject(program);
			} else{
				System.out.println("empty loop set");
				this.breakLoop();
			}
				
		}else{
			if(getBody().iterator().hasNext()){
				getBody().iterator().next().execute(program);
			}else{
				
				// If all statements in the foreach body were executed, load in a new loop variable and start again
				if(this.iterator().hasNext()){
					//System.out.println("Foreach, new iterating element" );

					loadNextLoopObject(program);
					getBody().resetIterator();
					this.execute(program); // restart and go to body iteration
				}else{
					throw new ProgramRuntimeException("Foreach executed without having valid next state");
				}
			}
		}
	}
	
	/* Iterator */
	
	@Override
	public Iterator<Statement> iterator() {
		
		return new Iterator<Statement>(){
			
			@Override
			public boolean hasNext(){	
			
				if (isBroken())
					return false;
				
				if(!hasObjectListIterator())
					return true;
				if(getBody().iterator().hasNext())
					return true;
				
				return getObjectListIterator().hasNext();
			}
			
			@Override
			public Statement next() throws NoSuchElementException{				
				if(this.hasNext()){

					if(!hasObjectListIterator())
						return ForEachDo.this;
					
					return getBody();
				}
				throw new NoSuchElementException();		
			}
		};
		
	}
	
	@Override
	public void resetIterator(){
		setObjectListIterator(null);
		this.stop = false;
		this.getBody().resetIterator();
		
	}	
	
	/* Object list */
	
	public boolean hasObjectListIterator(){
		return this.objectListIterator != null;
	}
	
	public Iterator<ObjectType> getObjectListIterator(){
		return this.objectListIterator;
	}
	
	private void setObjectListIterator(Iterator<ObjectType> objectListIterator){
		this.objectListIterator = objectListIterator;
	}
	
	private Iterator<ObjectType> objectListIterator = null;

	public List<ObjectType> buildObjectList(Program program){
		
		Stream.Builder<ObjectType> builder = Stream.builder();
		for (IKind object: ObjectType.getObjects(kind, program)){
			builder.accept(new ObjectType(object));
		}
		
		Stream<ObjectType> stream = builder.build();
		List<ObjectType> listStream = stream.filter( 
				o -> {
					loadLoopObject(program, o);
					return getWhereCondition() == null || getWhereCondition().execute(program).getValue();
				}
			).sorted(
				(o1, o2) -> this.compareOnSortCondition(program, o1, o2)).collect(Collectors.toList());
		
		if (getSortDirection() == SortDirection.DESCENDING)
			Collections.reverse(listStream);
		
		//System.out.println("STREAM: " + Arrays.toString(listStream.toArray()));
		return listStream;

	}
	
	public int compareOnSortCondition(Program program, ObjectType o1, ObjectType o2){
		if (getSortCondition() == null)
			return 0;
		loadLoopObject(program, o1);
		double r1 = ((DoubleType) getSortCondition().execute(program)).getValue();
		loadLoopObject(program, o2);
		double r2 = ((DoubleType) getSortCondition().execute(program)).getValue();
		return Double.compare(r1, r2);
	}
	
	/* Loop control */
	
	public void breakLoop(){
		this.stop = true;
	}
	
	public boolean isBroken(){
		return this.stop;
	}
	
	private boolean stop;
	
}
