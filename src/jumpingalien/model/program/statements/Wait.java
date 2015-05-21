package jumpingalien.model.program.statements;

import java.util.Iterator;
import java.util.NoSuchElementException;

import be.kuleuven.cs.som.annotate.*;
import jumpingalien.model.exceptions.ProgramRuntimeException;
import jumpingalien.model.program.Program;
import jumpingalien.model.program.expressions.Expression;
import jumpingalien.model.program.types.DoubleType;
import jumpingalien.part3.programs.SourceLocation;

public class Wait extends Statement{
	
	public Wait(Expression<DoubleType> amount, SourceLocation sourceLocation){
		super(sourceLocation);
		
		this.amount = amount;
		this.cycles = 0;
	}
	
	/* Time passed */ 
	@Basic
	public void setTimePassed(boolean timePassed){
		this.timePassed = timePassed;
	}
	
	@Basic
	public boolean hasTimePassed(){
		return this.timePassed;
	}
	
	// True if and only if the given time amount has passed
	private boolean timePassed = false;

	/* Amount */
	
	@Basic @Immutable
	public Expression<DoubleType> getAmount() {
		return this.amount;
	}
	
	// The time amount to wait, given in milliseconds
	private final Expression<DoubleType> amount;
	
	/* Cycles */
	
	@Basic
	public int getCycles() {
		return this.cycles;
	}
	
	@Basic
	public void increaseCycles() {
		this.cycles++;
	}
	
	// the number of waiting cycles that have passed
	private int cycles;
	
	/* Execute */
	
	@Override
	public void execute(final Program program) throws ProgramRuntimeException{
		if(this.iterator().hasNext())
			this.iterator().next();
		else
			throw new ProgramRuntimeException("Statement executed while not having next useful statement!");

		if(this.getCycles() >= this.getAmount().execute(program).getValue() * 1000 ){
			this.setTimePassed(true);
		}	
		
	}
	
	/* Iterator */
	
	@Override
	public Iterator<Statement> iterator() {
		
		return new Iterator<Statement>(){
			
			@Override
			public boolean hasNext(){
				return !Wait.this.hasTimePassed();
			}
			
			@Override
			public Statement next() throws NoSuchElementException{
				if ( this.hasNext() ){
					increaseCycles();
					return Wait.this;
				}
				else{
					throw new NoSuchElementException();		
				}
			}
		};
		
	}
	
	@Override
	public void resetIterator(){
		this.cycles = 0;
		this.timePassed = false;
	}
	
	
}
