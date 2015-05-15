package jumpingalien.model.program.statements;

import java.util.Iterator;
import java.util.NoSuchElementException;

import be.kuleuven.cs.som.annotate.*;
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
	
	@Override
	public void execute(Program program) {
		System.out.println("Statement, WAITING, cycles:"+this.cycles);
		this.timePassed = ! ( Wait.this.getCycles() < Wait.this.getAmount().execute(program).getValue() * 1000);
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
					Wait.this.increaseCycles();
					return Wait.this;
				}
				else{
					throw new NoSuchElementException();		
				}
			}
		};
		
	}
	
	public void resetIterator(){
		this.cycles = 0;
		this.timePassed = false;
	}
	
	/* Time passed */ 

	public void setTimePassed(boolean timePassed){
		this.timePassed = timePassed;
	}
	
	public boolean hasTimePassed(){
		return this.timePassed;
	}
	
	// True if and only if the given time amount has passed
	private boolean timePassed;

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
}
