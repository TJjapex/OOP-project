package jumpingalien.model.program.statements;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.antlr.v4.codegen.model.chunk.ThisRulePropertyRef_ctx;

import jumpingalien.model.program.Program;
import jumpingalien.part3.programs.SourceLocation;

public class Sequence extends Statement{
	public Sequence(List<Statement> statements, SourceLocation sourceLocation){
		super(sourceLocation);
		this.statements = statements;
	}
	
	public List<Statement> getStatements(){
		return this.statements;
	}
	
	private final List<Statement> statements;
	
	@Override
	public void execute(Program program) {
		if(this.iterator().hasNext()){
			( (Statement) this.iterator().next() ).execute(program);
		}
	}
}
