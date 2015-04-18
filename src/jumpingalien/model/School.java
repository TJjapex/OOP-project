package jumpingalien.model;

import java.util.HashSet;
import java.util.Set;

// All aspects shall ONLY be specified in a formal way.

/**
 * A class of Schools, groups of Slimes in the game world of Mazub.
 * 
 * @author Thomas Verelst, Hans Cauwenbergh
 * @version 1.0
 */

/*
 * Uitwerking:
 * 
 * members = set of list of... van slimes?
 */

public class School {
	
	/************************************************** GENERAL ***********************************************/
	

	
	/************************************************ CONSTRUCTOR *********************************************/
	
	public School(){
		
	}
	
	/************************************************** RELATIONS **********************************************/
	
	public boolean canHaveAsSlime(Slime slime){
		return (!(this.hasAsSlime(slime)) && (slime != null) && (!slime.isTerminated()));
	}
	
	public boolean hasProperSlimes(){
		// in een set kunnen geen slimes zitten die equal zijn?
		return true;
	}
	
	public boolean hasAsSlime(Slime slime){
		return this.getAllSlimes().contains(slime);
	}
	
	public void addAsSlime(Slime slime){
		if (canHaveAsSlime(slime)){
			slime.setSchool(this);
			slimes.add(slime);
		}
	}
	
	public void removeAsSlime(Slime slime){
		slime.setSchool(null);
		slimes.remove(slime);
	}
	
	public Set<Slime> getAllSlimes(){
		HashSet<Slime> slimesClone =  new HashSet<Slime>(this.slimes);
		return slimesClone;
	}
	
	public int getNbSlimes(){
		return this.getAllSlimes().size();
	}
	
	public Set<Slime> slimes = new HashSet<Slime>();
	
	public void terminate(){

		for (Slime slime: this.getAllSlimes()){
			this.removeAsSlime(slime);
		}
		this.terminated = true;
	}
	
	private boolean terminated = false;
	
	
}
