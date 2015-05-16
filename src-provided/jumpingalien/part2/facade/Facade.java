package jumpingalien.part2.facade;

import java.util.Collection;

import jumpingalien.util.ModelException;
import jumpingalien.util.Sprite;
import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.School;
import jumpingalien.model.Shark;
import jumpingalien.model.Slime;
import jumpingalien.model.World;
import jumpingalien.model.exceptions.IllegalHeightException;
import jumpingalien.model.exceptions.IllegalPositionXException;
import jumpingalien.model.exceptions.IllegalPositionYException;
import jumpingalien.model.exceptions.IllegalWidthException;
import jumpingalien.model.terrain.Terrain;

public class Facade extends jumpingalien.part1.facade.Facade implements IFacadePart2 {

	/******************************************************** PART 2 ***************************************************/
	
	@Override
	public int getNbHitPoints(Mazub alien) {
		return alien.getNbHitPoints();
	}

	@Override
	public World createWorld(int tileSize, int nbTilesX, int nbTilesY,
			int visibleWindowWidth, int visibleWindowHeight, int targetTileX,
			int targetTileY) {
		return new World(tileSize, nbTilesX, nbTilesY,
			visibleWindowWidth, visibleWindowHeight, targetTileX,
			targetTileY);
	}

	@Override
	public int[] getWorldSizeInPixels(World world) {
		return new int[] { world.getWorldWidth(), world.getWorldHeight() };
	}

	@Override
	public int getTileLength(World world) {
		return world.getTileLength();
	}

	@Override
	public void startGame(World world) {
		try{
			world.start();
		}catch(IllegalStateException exc){
			throw new ModelException("Illegal state exception: " + exc.getMessage());
		}
	}

	@Override
	public boolean isGameOver(World world) {
		return world.isGameOver();
	}

	@Override
	public boolean didPlayerWin(World world) {
		return world.didPlayerWin();
	}

	@Override
	public void advanceTime(World world, double dt) {
		try{
			world.advanceTime(dt);	
		}catch(IllegalArgumentException exc){
			throw new ModelException("Illegal argument exception: " + exc.getMessage());
		}
		
	}

	@Override
	public int[] getVisibleWindow(World world) {
		return new int[] 
			{ 
				world.getDisplayPositionX(), 
				world.getDisplayPositionY(), 
				world.getDisplayPositionX() + world.getDisplayWidth(), 
				world.getDisplayPositionY() + world.getDisplayHeight()
			};
	}

	@Override
	public int[] getBottomLeftPixelOfTile(World world, int tileX, int tileY) {
		return new int[] { world.getPositionXOfTile(tileX), world.getPositionYOfTile(tileY) };
	}

	@Override
	public int[][] getTilePositionsIn(World world, int pixelLeft, int pixelBottom, int pixelRight, int pixelTop) {
		return world.getTilePositionsIn(pixelLeft, pixelBottom, pixelRight, pixelTop);
	}

	@Override
	public int getGeologicalFeature(World world, int pixelX, int pixelY)
			throws ModelException {
		
		try{
			return world.getGeologicalFeature(pixelX, pixelY).getId();
		}catch(IllegalArgumentException exc){
			throw new ModelException("Illegal argument exception: " + exc.getMessage());
		}
		
	}

	@Override
	public void setGeologicalFeature(World world, int tileX, int tileY, int terrainTypeId) {
		try{
			world.setGeologicalFeature(tileX, tileY,  Terrain.idToType(terrainTypeId));
		}catch(IllegalPositionXException  | IllegalPositionYException exc){
			throw new ModelException(exc.getMessage());
		}catch(IllegalStateException exc){
			throw new ModelException("Invalid state exception!");
		}
				
	}

	@Override
	public void setMazub(World world, Mazub alien) throws ModelException{
		try{
			alien.setWorldTo(world);
		}catch(IllegalArgumentException exc){
			throw new ModelException("Illegal argument exception: " + exc.getMessage());
		}
	
	}

	@Override
	public boolean isImmune(Mazub alien) {
		return alien.isImmune();
	}
	
	// Plants

	@Override
	public Plant createPlant(int x, int y, Sprite[] sprites) {
		try{
			return new Plant(x, y, sprites);
		}catch( IllegalPositionXException | IllegalPositionYException exc){
			throw new ModelException("Invalid position given.");
		}catch( IllegalWidthException | IllegalHeightException exc){
			throw new ModelException("Invalid sprite size given.");
		}	
	}

	@Override
	public void addPlant(World world, Plant plant) throws ModelException{
		try{
			plant.setWorldTo(world);
		}catch(IllegalArgumentException exc){
			throw new ModelException("Illegal argument exception: " + exc.getMessage());
		}
	}
		

	@Override
	public Collection<Plant> getPlants(World world) {
		return Plant.getAllInWorld(world);
	}

	@Override
	public int[] getLocation(Plant plant) {
		return new int[] { plant.getRoundedPositionX(), plant.getRoundedPositionY() };
	}

	@Override
	public Sprite getCurrentSprite(Plant plant) {
		return plant.getCurrentSprite();
	}
	
	// Sharks

	@Override
	public Shark createShark(int x, int y, Sprite[] sprites) {
		try{
			return new Shark(x, y, sprites);
		}catch( IllegalPositionXException | IllegalPositionYException exc){
			throw new ModelException("Invalid position given.");
		}catch( IllegalWidthException | IllegalHeightException exc){
			throw new ModelException("Invalid sprite size given.");
		}
	}

	@Override
	public void addShark(World world, Shark shark) throws ModelException{
		try{
			shark.setWorldTo(world);
		}catch(IllegalArgumentException exc){
			throw new ModelException("Illegal argument exception: " + exc.getMessage());
		}
	}
	
	@Override
	public Collection<Shark> getSharks(World world) {
		return Shark.getAllInWorld(world);
	}

	@Override
	public int[] getLocation(Shark shark) {
		return new int[] { shark.getRoundedPositionX(), shark.getRoundedPositionY() };
	}

	@Override
	public Sprite getCurrentSprite(Shark shark) {
		return shark.getCurrentSprite();
	}
	
	// Schools

	@Override
	public School createSchool() {
		return new School();
	}

	@Override
	public Slime createSlime(int x, int y, Sprite[] sprites, School school) {
		try{
			return new Slime(x,y,sprites,school);
		}catch( IllegalPositionXException | IllegalPositionYException exc){
			throw new ModelException("Invalid position given.");
		}catch( IllegalWidthException | IllegalHeightException exc){
			throw new ModelException("Invalid sprite size given.");
		}
	}
	
	// Slimes

	@Override
	public void addSlime(World world, Slime slime) throws ModelException{
		try{
			slime.setWorldTo(world);
		}catch(IllegalArgumentException exc){
			throw new ModelException("Illegal argument exception: " + exc.getMessage());
		}
	}

	@Override
	public Collection<Slime> getSlimes(World world) {
		return Slime.getAllInWorld(world);
	}

	@Override
	public int[] getLocation(Slime slime) {
		return new int[] { slime.getRoundedPositionX(), slime.getRoundedPositionY() };
	}

	@Override
	public Sprite getCurrentSprite(Slime slime) {
		return slime.getCurrentSprite();
	}

	@Override
	public School getSchool(Slime slime) {
		return slime.getSchool();
	}
	
}