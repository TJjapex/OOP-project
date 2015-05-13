package jumpingalien.model.program.statements;

import java.util.List;
import java.util.Iterator;
import java.util.NoSuchElementException;

import jumpingalien.model.program.Program;
import jumpingalien.part3.programs.SourceLocation;

public abstract class Statement {

	public Statement( SourceLocation sourceLocation ){
		this.sourceLocation = sourceLocation;
	}
	
	public SourceLocation getSourceLocation(){
		return this.sourceLocation;
	}
	
	private final SourceLocation sourceLocation;
	
	// iterator om te itereren over substatements
	// casten is hier sowieso nog omslachtig gedaan, moet met generics..
	public Iterator iterator() {
		
		return new Iterator(){
			
			@Override
			public boolean hasNext(){
				if ( !(Statement.this instanceof List) && singleStatementUsed == false)
					return true;
				else if ( Statement.this instanceof List && (currentIndex < ( (List) Statement.this).size() ||
							subStatementIterator.hasNext())){
					return true;
				} else {
					return false;
				}				
			}
			
			@Override
			public Object next() throws NoSuchElementException{
				if ( !(Statement.this instanceof List) && this.hasNext()){
					System.out.println("single statement returned, this: "+this);
					singleStatementUsed = true;
					return Statement.this;
				}
				else if ( Statement.this instanceof List && this.hasNext()){
					
					subStatementIterator = ( (Statement) ( (List) Statement.this).get(currentIndex)).iterator();
					currentIndex++;
					return subStatementIterator.next();
				} else{
					throw new NoSuchElementException();
				}
					
			}
			
			boolean singleStatementUsed = false;
			Iterator subStatementIterator;
			int currentIndex = 0;
		};
	}
	
//	void executeAll(Program program){
//		while (this.iterator().hasNext()){
//			( (Statement) this.iterator().next() ).execute(program);
//		}
//	}
	
	public abstract void execute(Program program);
	
}
