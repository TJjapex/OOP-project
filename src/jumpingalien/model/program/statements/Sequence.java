package jumpingalien.model.program.statements;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import jumpingalien.model.program.Program;
import jumpingalien.part3.programs.SourceLocation;

public class Sequence extends Statement{
	
	public Sequence(List<Statement> statements, SourceLocation sourceLocation){
		super(sourceLocation);
		this.statements = statements;
		this.sequenceIterator = this.iterator();
	}
	
	public List<Statement> getStatements(){
		return this.statements;
	}
	
	private final List<Statement> statements;
	
	@Override
	public Iterator<Statement> iterator() {
		
		return new Iterator<Statement>(){
			
			@Override
			public boolean hasNext(){
				return innerIterator.hasNext();
			}
			
			@Override
			public Statement next() throws NoSuchElementException{
				if ( this.hasNext() ){
					nextStatement = innerIterator.next();
					if (!this.hasNext() && ( currentIndex != (Sequence.this).getStatements().size() - 1 )){
						currentIndex += 1;
						innerIterator = (Sequence.this).getStatements().get(currentIndex).iterator();
						return nextStatement;
					} else {
						return nextStatement;
					}
				}
				else{
					throw new NoSuchElementException();		
				}
			}
			
			private Statement nextStatement;
			private int currentIndex = 0;
			private Iterator<Statement> innerIterator = (Sequence.this).getStatements().get(currentIndex).iterator();		
			
		};
		
	}
	
	// wordt niet meer gebruikt, Statements in de sequence worden meteen door de iterator aangeroepen
	@Override
	public void execute(Program program) {
		System.out.println("SEQUENCE EXECUTE: " + sequenceIterator.hasNext());
		if(sequenceIterator.hasNext()){
			( (Statement) sequenceIterator.next() ).execute(program);
		}
	}
	
	@Override
	public void resetIterator(){
		this.sequenceIterator = this.iterator();
		for (int i = 0; i < (Sequence.this).getStatements().size(); i++){
			(Sequence.this).getStatements().get(i).resetIterator();
		}
	}
	
	private Iterator<Statement> sequenceIterator;
	
}
