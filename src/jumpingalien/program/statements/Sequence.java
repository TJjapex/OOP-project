package jumpingalien.program.statements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import jumpingalien.model.exceptions.ProgramRuntimeException;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.program.Program;

/**
 * A class of Sequences of Statements as defined in a Program.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 * 
 */
public class Sequence extends Statement{	
	
	/* Constructor */
	
	public Sequence(List<Statement> statements, SourceLocation sourceLocation){
		super(sourceLocation);
		this.statements = statements;
	}
	
	/* Statements */
	
	@Basic @Immutable
	public List<Statement> getStatements(){
		return this.statements;
	}
	
	@Basic @Immutable
	public Statement getStatementAt(int index) throws IndexOutOfBoundsException{
		return this.statements.get(index);
	}
	
	private final List<Statement> statements;
	
	/* Current index */
	
	@Basic
	public int getCurrentIndex() {
		return currentIndex;
	}

	@Basic
	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}
	
	public void increaseCurrentIndex(){
		setCurrentIndex(getCurrentIndex() + 1 );
	}
	
	private int currentIndex = 0;
	
	/* Iterator */
	
	@Override
	public Iterator<Statement> iterator() {
		
		return new Iterator<Statement>(){
			
			@Override
			public boolean hasNext(){
				
				while(getCurrentIndex() < Sequence.this.getStatements().size()){
					if(getStatementAt(getCurrentIndex()).iterator().hasNext()){
						return true;
					}else {
						increaseCurrentIndex();
						return this.hasNext();
					}
				}
				return false;
			}
			
			@Override
			public Statement next() throws NoSuchElementException{
				while(getCurrentIndex() < Sequence.this.getStatements().size()){
					if(getStatementAt(getCurrentIndex()).iterator().hasNext()){
						return getStatementAt(getCurrentIndex());
					}
				}
				
				throw new NoSuchElementException();
			}
		};
		
	}
	
	@Override
	public void resetIterator(){
		for (int i = 0; i < getStatements().size(); i++){
			getStatementAt(i).resetIterator();
		}
		setCurrentIndex(0);
	}
	
	/* Execution */

	@Override
	public void execute(Program program) throws ProgramRuntimeException{
		if(this.iterator().hasNext()){
			this.iterator().next().execute(program);
		}else{
			throw new ProgramRuntimeException("Statement executed while not having next useful statement!");
		}
	}
	
	/* Children statements */
	
	@Override
	public List<Statement> getChildrenStatements(){
		List<Statement> childrenStatements = new ArrayList<>();
		for (int i = 0; i < getStatements().size(); i++){
			childrenStatements.add(this.getStatementAt(i));
		}
		
		return childrenStatements;
	}
	
}
