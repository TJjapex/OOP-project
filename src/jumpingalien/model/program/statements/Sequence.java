package jumpingalien.model.program.statements;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import jumpingalien.model.program.Program;
import jumpingalien.part3.programs.SourceLocation;

public class Sequence extends Statement{
	
//	public Sequence(List<Statement> statements, SourceLocation sourceLocation){
//		super(sourceLocation);
//		this.statements = statements;
//	}
//	
//	public List<Statement> getStatements(){
//		return this.statements;
//	}
//	
//	private final List<Statement> statements;
//	
//	@Override
//	public Iterator<Statement> iterator() {
//		
//		return new Iterator<Statement>(){
//			
//			@Override
//			public boolean hasNext(){
//				
//				if (this.innerIterator.hasNext())
//					return true;
//				else if (currentIndex != Sequence.this.getStatements().size() - 1){
//					currentIndex++;
//					innerIterator = (Sequence.this).getStatements().get(currentIndex).iterator();
//					return this.hasNext();
//				} else 
//					return false;
//			
//			}
//			
//			@Override
//			public Statement next() throws NoSuchElementException{
//				if (this.hasNext()){
//					return innerIterator.next();
//				} else {
//					throw new NoSuchElementException();
//				}
//			}
//			
//			private int currentIndex = 0;
//			private Iterator<Statement> innerIterator = (Sequence.this).getStatements().get(currentIndex).iterator();		
//			
//		};
//		
//	}
//	
//	@Override
//	public void execute(Program program) {
//		
//	}
//	
//	@Override
//	public void resetIterator(){
//		for (int i = 0; i < (Sequence.this).getStatements().size(); i++){
//			(Sequence.this).getStatements().get(i).resetIterator();
//		}
//	}
	
	
	// Nieuwe implementatie ;)
	
	
	public Sequence(List<Statement> statements, SourceLocation sourceLocation){
		super(sourceLocation);
		this.statements = statements;
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
				
//				if (this.innerIterator.hasNext())
//					return true;
//				else if (currentIndex != Sequence.this.getStatements().size() - 1){
//					currentIndex++;
//					innerIterator = (Sequence.this).getStatements().get(currentIndex).iterator();
//					return this.hasNext();
//				} else 
//					return false;
//			
				while(Sequence.this.currentIndex < Sequence.this.getStatements().size()){
					if(Sequence.this.getStatements().get(Sequence.this.currentIndex).iterator().hasNext()){
						return true;
					}else {
						Sequence.this.currentIndex++;
						return this.hasNext();
					}
				}
				return false;
			}
			
			@Override
			public Statement next() throws NoSuchElementException{
				while(Sequence.this.currentIndex < Sequence.this.getStatements().size()){
					if(Sequence.this.getStatements().get(Sequence.this.currentIndex).iterator().hasNext()){
						return Sequence.this.getStatements().get(Sequence.this.currentIndex);
					}
				}
				
				throw new NoSuchElementException();
			}
			
			
			//private Iterator<Statement> innerIterator = (Sequence.this).getStatements().get(currentIndex).iterator();		
			
		};
		
	}
	private int currentIndex = 0;
	
	@Override
	public void execute(Program program) {
		if(this.iterator().hasNext()){
			this.iterator().next().execute(program);
		}
	}
	
	@Override
	public void resetIterator(){
		for (int i = 0; i < (Sequence.this).getStatements().size(); i++){
			(Sequence.this).getStatements().get(i).resetIterator();
		}
		this.currentIndex = 0;
	}
}
