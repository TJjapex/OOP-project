package jumpingalien.part2.tests;

import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import static org.junit.Assert.*;
import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.World;
import jumpingalien.model.helper.Orientation;
import jumpingalien.part2.facade.Facade;
import jumpingalien.part2.facade.IFacadePart2;
import jumpingalien.util.Sprite;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PlantTest {

	public static final int FEATURE_AIR = 0;
	public static final int FEATURE_SOLID = 1;
	public static final int FEATURE_WATER = 2;
	public static final int FEATURE_MAGMA = 3;

	private Sprite[] sprites;
	private Plant plant;
	private Mazub alien;
	private World world;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		IFacadePart2 facade = new Facade();
		
		sprites = spriteArrayForSize(54, 27, 2);
		plant = facade.createPlant(160, 180, sprites);
		world = facade.createWorld(50, 20, 15, 400, 400, 4, 1);
		
		Sprite[] alienSprites = spriteArrayForSize(20, 20);
		alien = facade.createMazub(100,100, alienSprites); // At least one alien in world
		facade.setMazub(world, alien);
		facade.addPlant(world, plant);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	/***************************************************** CONSTRUCTOR *************************************************/
	
	/**
	 * Check if the Plant is correctly initiated.
	 */
	@Test
	public void testConstructor(){
		IFacadePart2 facade = new Facade();
		facade.startGame(world);
		
		assertEquals(160, facade.getLocation(plant)[0]);
		assertEquals(180, facade.getLocation(plant)[1]);
		assertEquals(sprites[0], facade.getCurrentSprite(plant));
	}
	
	/******************************************************* MOVEMENT **************************************************/
	
	/**
	 * Check if the Plant moves alternating to the left and to the right.
	 */
	@Test
	public void testAlternateMovement(){
		IFacadePart2 facade = new Facade();
		facade.startGame(world);
		
		assertTrue(world.hasStarted());
		
		facade.advanceTime(world, 0.2);
		
		assertEquals(Orientation.LEFT, plant.getOrientation());
		assertTrue(plant.isMoving());
		for (int i=0; i<2; i += 1){
			facade.advanceTime(world, 0.2);
		}
		// After 0.5s, the Plant should move to the other direction. ( Check after 0.6s )
		assertEquals(Orientation.RIGHT, plant.getOrientation());
		assertTrue(plant.isMoving());
	}
	
	/****************************************************** COLLISION **************************************************/
	
	/**
	 * Check if the Plant changes direction upon horizontal collision.
	 */
	@Test
	public void testChangeDirectionUponHorizontalCollision(){
		IFacadePart2 facade = new Facade();
		
		// Set a solid tile to the left of the plant
		facade.setGeologicalFeature(world,2, 4, 1);
		
		facade.startGame(world);
		assertTrue(world.hasStarted());

		// Plant starts moving to the left
		facade.advanceTime(world, 0.2);
		// Plant collides with the solid tile and changes direction
		facade.advanceTime(world, 0.2);
		assertEquals(Orientation.RIGHT, plant.getOrientation());
	}
	
	/******************************************************* OVERLAP **************************************************/
	
	/**
	 * Check if the Plant gets killed when a Mazub overlaps with it.
	 */
	@Test
	public void testKilledUponMazubOverlap(){
		IFacadePart2 facade = new Facade();
		
		World world2 = facade.createWorld(50, 20, 15, 400, 400, 4, 1);
		Mazub alien2 = facade.createMazub(170, 210, spriteArrayForSize(66, 92, 10));
		Plant plant2 = facade.createPlant(160, 180, sprites);
		facade.addPlant(world2, plant2);
		facade.setMazub(world2, alien2);

		facade.startGame(world2);
		assertTrue(world2.hasStarted());
		
		facade.advanceTime(world2, 0.2);
		
		// Mazub falls on top of the plant and overlaps with the top row pixels of the plant
		assertEquals(plant2.getRoundedPositionY()+plant2.getHeight()-1, alien2.getRoundedPositionY());
		assertTrue(plant2.isKilled());
	}
	
	/***************************************************** TERMINATION *************************************************/
	
	/**
	 * Check that the Plant gets terminated with a 0.6s delay after it's killed.
	 */
	@Test
	public void testTerminate(){
		IFacadePart2 facade = new Facade();
		Plant deadPlant = new Plant(60, 60, 0.5, 0, 0.5, 0, sprites, 0, 1);
		facade.addPlant(world, deadPlant);
		
		facade.startGame(world);
		
		assertTrue(deadPlant.isKilled());
		
		for (int i=0; i<3; i += 1){
			facade.advanceTime(world,0.2);
		}
		facade.advanceTime(world, 0.1);
		assertTrue(deadPlant.isTerminated());
		assertFalse(deadPlant.hasWorld());		
	}
	
}
