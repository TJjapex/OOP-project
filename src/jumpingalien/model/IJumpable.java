package jumpingalien.model;

public interface IJumpable {
	
	// overrides plaatsen! jump methodes uit game object weghalen

	public boolean isJumping();	
	
	//private void setJumping(boolean jumping); // private gaat blijkbaar niet in interfaces? 
	
	public void startJump();
	
	public void endJump();
	
}
