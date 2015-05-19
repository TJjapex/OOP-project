package jumpingalien.model.program.statements;

public interface ILoop {
	public void breakLoop();
	
	public boolean isBroken();
	
	public Statement getBody();
	
}
