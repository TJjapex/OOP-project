package jumpingalien.model.program.statements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import jumpingalien.model.exceptions.ProgramRuntimeException;
import jumpingalien.model.program.Program;
import jumpingalien.part3.programs.SourceLocation;

public class Sequence extends Statement{	
	
	public Sequence(List<Statement> statements, SourceLocation sourceLocation){
		super(sourceLocation);
		this.statements = statements;
		for (int i = 0; i < this.getStatements().size(); i++){
			this.getStatementAt(i).setParentStatement(this);
		}
	}
	
	/* Statements */
	
	public List<Statement> getStatements(){
		return this.statements;
	}
	
	public Statement getStatementAt(int index){
		return this.statements.get(index);
	}
	
	private final List<Statement> statements;
	
	
	/* Current index */
	
	public int getCurrentIndex() {
		return currentIndex;
	}

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
	
	public void resetIterator(){
		for (int i = 0; i < getStatements().size(); i++){
			getStatementAt(i).resetIterator();
		}
		setCurrentIndex(0);
	}
	
	/* Execute */

	@Override
	public void execute(Program program) throws IllegalStateException{
		if(this.iterator().hasNext()){
			this.iterator().next().execute(program);
		}else{
			throw new ProgramRuntimeException("Statement executed while not having next useful statement!");
		}
	}
	
	@Override
	public List<Statement> getChildrenStatements(){
		List<Statement> childrenStatements = new ArrayList<>();
		for (int i = 0; i < getStatements().size(); i++){
			childrenStatements.add(this.getStatementAt(i));
		}
		
		return childrenStatements;
	}
	
}
