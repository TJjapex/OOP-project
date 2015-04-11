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
	
	public static final int MUTUAL_SCHOOL_DAMAGE = 1;
	public static final int SWITCH_SCHOOL_DAMAGE = 1;
	
	/************************************************ CONSTRUCTOR *********************************************/
	
	public School(){
		
	}
	
	/************************************************** RELATIONS **********************************************/
	
	public boolean canHaveAsSlime(Slime slime){
		return (!(this.hasAsSlime(slime)) && (slime != null) && (!slime.isKilled()));
	}
	
	public boolean hasProperSlimes(){
		for (Slime slime: this.getAllSlimes()){
			if (!this.canHaveAsSlime(slime))
				return false;
		}
		return true;
	}
	
	public boolean hasAsSlime(Slime slime){
		return slimes.contains(slime);
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
		return slimes.size();
	}
	
	public void switchSchool(Slime switchedSlime, School otherSchool){
		this.removeAsSlime(switchedSlime);
		for (Slime slime: slimes){
			slime.increaseNbHitPoints(SWITCH_SCHOOL_DAMAGE);
		}
		for (Slime slime: otherSchool.getAllSlimes()){
			slime.takeDamage(SWITCH_SCHOOL_DAMAGE);
		}
		otherSchool.addAsSlime(switchedSlime);
	}
	
	public void mutualDamage(Slime damagedSlime){
		for (Slime slime: slimes){
			if (!slime.equals(damagedSlime))
				slime.takeDamage(MUTUAL_SCHOOL_DAMAGE);
		}
	}
	
	public Set<Slime> slimes = new HashSet<Slime>();
	
	public void terminate(){

		for (Slime slime: slimes){
			this.removeAsSlime(slime);
		}
		this.terminated = true;
	}
	
	private boolean terminated = false;
	
	
}
