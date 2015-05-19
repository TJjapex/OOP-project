package jumpingalien.model;

import java.util.HashSet;
import java.util.Set;

import jumpingalien.model.terrain.Terrain;

public class Tile implements IKind {

	public Tile(int tileLength, int positionX, int positionY, Terrain type){
		this.tileLength = tileLength;
		this.positionX = positionX;
		this.positionY = positionY;
		this.type = type;
	}
	
	public Terrain getTerrainType(){
		return this.type;
	}
	
	private Terrain type;
	
	@Override
	public int getRoundedPositionX() {
		return this.positionX;
	}
	
	private int positionX;

	@Override
	public int getRoundedPositionY() {
		return this.positionY;
	}
	
	private int positionY;

	public int getTileLength(){
		return this.tileLength;
	}
	
	private int tileLength;
	
	@Override
	public int getWidth() {
		return this.getTileLength();
	}

	@Override
	public int getHeight() {
		return this.getTileLength();
	}
	
	public boolean terrainTypeIs(Terrain type){
		return this.getTerrainType() == type;
	}
	
	@Override
	public String toString(){
		return "Tile x:"+getRoundedPositionX()+" y:"+getRoundedPositionY();
	}
	
}
