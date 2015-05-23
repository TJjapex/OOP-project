package jumpingalien.part2.tests;

import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import static org.junit.Assert.*;
import jumpingalien.model.Mazub;
import jumpingalien.model.School;
import jumpingalien.model.Shark;
import jumpingalien.model.Slime;
import jumpingalien.model.World;
import jumpingalien.model.terrain.Terrain;
import jumpingalien.part2.facade.Facade;
import jumpingalien.part2.facade.IFacadePart2;
import jumpingalien.part3.facade.IFacadePart3;
import jumpingalien.util.Sprite;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SharkTest {

	public static final int FEATURE_AIR = 0;
	public static final int FEATURE_SOLID = 1;
	public static final int FEATURE_WATER = 2;
	public static final int FEATURE_MAGMA = 3;
	
	private Sprite[] sprites;
	private Shark shark;
	private Slime slime;

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
		
		sprites = spriteArrayForSize(66, 42, 2);
		shark = facade.createShark(10, 12, sprites);
		world = facade.createWorld(50, 20, 15, 200, 150, 4, 1);
		
		Sprite[] alienSprites = spriteArrayForSize(20, 20);
		alien = facade.createMazub(100,100, alienSprites); // At least one alien in world
		
		facade.setMazub(world, alien);
		facade.addShark(world, shark);
	} 

	@After
	public void tearDown() throws Exception {
	}
	
	/***************************************************** CONSTRUCTOR *************************************************/
	
	/**
	 * Check if the Shark is correctly initiated.
	 */
	@Test
	public void testConstructor(){
		IFacadePart2 facade = new Facade();
		facade.startGame(world);
		
		assertEquals(10, facade.getLocation(shark)[0]);
		assertEquals(12, facade.getLocation(shark)[1]);
		assertEquals(sprites[0], facade.getCurrentSprite(shark));
	}
	
	/******************************************************* TERRAIN ***************************************************/
	
	/**
	 * Check if the submerged function for the Terrain works correctly.
	 */
	@Test
	public void testOutOfWater(){
		IFacadePart2 facade = new Facade();
		facade.startGame(world);
		
		assertFalse(shark.isSubmergedIn(Terrain.WATER));
		assertTrue(shark.isSubmergedIn(Terrain.AIR));
	}
	
	
	/***************************************************** TERMINATION *************************************************/
	
	/**
	 * Check if the Shark is killed and terminated after a 0.6s delay when he falls until he is out of the World.
	 */
	@Test
	public void deathOutOfGameWorld(){
		Facade facade = new Facade();
		facade.startGame(world);
		
		/* Initial y position is 12, so wait until y-position lower than 0 */
		/* calculations: -12 = -10/2 * t**2 -> t = 0.155 */
		facade.advanceTime(world, 0.16);
		System.out.println(shark.getRoundedPositionY());
		System.out.println(shark.getAccelerationY());
		System.out.println(shark.getVelocityY());
		//assertEquals(0, shark.getRoundedPositionY());
		
		for (int i=0; i<3; i ++){
			assertTrue(shark.isKilled());
			assertFalse(shark.isTerminated());
			assertEquals(world, shark.getWorld());
			facade.advanceTime(world,0.2);
		}
		assertTrue(shark.isTerminated());
		assertFalse(shark.hasWorld());		

	}
	
}
