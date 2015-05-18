package jumpingalien.model.program.statements;

public interface Loop {
	public Statement getBody();
	
	public void breakLoop();
	
	public boolean isBroken();
}
