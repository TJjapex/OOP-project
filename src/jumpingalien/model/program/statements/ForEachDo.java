package jumpingalien.model.program.statements;

import java.util.Collections;
import java.util.List;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import jumpingalien.model.IKind;
import jumpingalien.model.exceptions.BreakLoopException;
import jumpingalien.model.exceptions.ProgramRuntimeException;
import jumpingalien.model.program.Program;
import jumpingalien.model.program.expressions.Expression;
import jumpingalien.model.program.types.BooleanType;
import jumpingalien.model.program.types.DoubleType;
import jumpingalien.model.program.types.ObjectType;
import jumpingalien.part3.programs.IProgramFactory.Kind;
import jumpingalien.part3.programs.IProgramFactory.SortDirection;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class of For Each Statements as defined in a Program.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 * 
 */
public class ForEachDo extends Statement implements ILoop {

	/* Constructor */
	
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
	
	@Basic @Immutable
	public Kind getKind(){
		return this.kind;
	}
	
	private Kind kind;

	/* Loop variable */
	
	@Basic @Immutable
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
	
	@Basic @Immutable
	public Expression<BooleanType> getWhereCondition(){
		return this.whereCondition;
	}
	
	private final Expression<BooleanType> whereCondition;
	
	/* Sort condition */
	
	@Basic @Immutable
	public Expression<?> getSortCondition(){
		return this.sortCondition;
	}
	
	private final Expression<?> sortCondition;
	
	/* Sort direction */
	
	@Basic @Immutable
	public SortDirection getSortDirection(){
		return this.sortDirection;
	}
	
	private final SortDirection sortDirection;
	
	/* Loop body */
	
	@Basic @Immutable @Override
	public Statement getBody(){
		return this.body;
	}
	
	private final Statement body;	
	
	/* Execution */
	
	@Override
	public void execute(final Program program) throws ProgramRuntimeException{
		if(!hasObjectListIterator()){
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
				try{
					getBody().iterator().next().execute(program);
				} catch (BreakLoopException exc){
					this.breakLoop();
				}
			}else{
				// If all statements in the foreach body were executed, load in a new loop variable and start again
				if(this.iterator().hasNext()){
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
		this.broken = false;
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
	
	@Basic @Override
	public void breakLoop(){
		this.broken = true;
	}
	
	@Basic @Override
	public boolean isBroken(){
		return this.broken;
	}
	
	private boolean broken;
	
}
