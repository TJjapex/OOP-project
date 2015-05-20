package jumpingalien.model;

public interface IJumpable {
	
	// overrides plaatsen! jump methodes uit game object weghalen

	//public boolean isJumping(); // staat nog niet in Mazub maar is wel makkelijk af te leiden
	
	//private void setJumping(boolean jumping); // private gaat blijkbaar niet in interfaces? 
	
	public void startJump();
	
	public void endJump();
	
}
