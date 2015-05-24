package jumpingalien.part3.tests;

import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import static org.junit.Assert.*;
import jumpingalien.model.Buzam;
import jumpingalien.model.Mazub;
import jumpingalien.model.World;
import jumpingalien.model.helper.Orientation;
import jumpingalien.part3.facade.Facade;
import jumpingalien.part3.facade.IFacadePart3;
import jumpingalien.part3.programs.ParseOutcome;
import jumpingalien.program.Program;
import jumpingalien.util.Sprite;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BuzamTest {

	public static final int FEATURE_AIR = 0;
	public static final int FEATURE_SOLID = 1;
	public static final int FEATURE_WATER = 2;
	public static final int FEATURE_MAGMA = 3;

	private Sprite[] sprites;
	private Buzam buzam;
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
		IFacadePart3 facade = new Facade();
		
		sprites = spriteArrayForSize(54, 27, 10);
		buzam = facade.createBuzam(160, 12, sprites);
		world = facade.createWorld(50, 20, 15, 400, 400, 4, 1);
		
		Sprite[] alienSprites = spriteArrayForSize(20, 20);
		alien = facade.createMazub(100,100, alienSprites); // At least one alien in world
		facade.setMazub(world, alien);
		facade.addBuzam(world, buzam);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * Check if the Plant is correctly initiated.
	 */
	@Test
	public void testConstructor(){
		IFacadePart3 facade = new Facade();
		facade.startGame(world);
		
		assertEquals(160, facade.getLocation(buzam)[0]);
		assertEquals(12, facade.getLocation(buzam)[1]);
		assertEquals(sprites[0], facade.getCurrentSprite(buzam));
	}
	

	/**
	 * Check if the Shark is killed and terminated after a 0.6s delay when he falls until he is out of the World.
	 */
	@Test
	public void deathOutOfGameWorld(){
		Facade facade = new Facade();
		facade.startGame(world);
		
		/* Initial y position is 12, so wait until y-position lower than 0 */
		/* calculations: -12 = -10/2 * t**2 -> t = 0.155 */
		facade.advanceTime(world, 0.17);
		assertEquals(0, buzam.getRoundedPositionY());

		for (int i=0; i<3; i ++){
			assertTrue(buzam.isKilled());
			assertFalse(buzam.isTerminated());
			assertEquals(world, buzam.getWorld());
			facade.advanceTime(world,0.2);
		}
		
		assertTrue(buzam.isTerminated());
		assertFalse(buzam.hasWorld());		

	}
	
	@Test
	public void buzamWithProgram(){
		Facade facade = new Facade();
		
		String testProgram =
		""
		+ "start_run right;"
		+ "wait 0.002;"
		+ "if ismoving(self, right) then "
		+ " stop_run right;"
		+"fi";

		ParseOutcome<?> parseOutcome = facade.parse(testProgram);
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		Buzam buzam2 = facade.createBuzamWithProgram(180, 80, sprites, program);
		facade.addBuzam(world, buzam2);
		facade.startGame(world);
		
		facade.advanceTime(world, 0.002);
		assertTrue(buzam2.isMoving(Orientation.RIGHT));

	}
	
}
