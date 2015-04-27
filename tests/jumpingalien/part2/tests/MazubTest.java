package jumpingalien.part2.tests;

import static jumpingalien.tests.util.TestUtils.doubleArray;
import static jumpingalien.tests.util.TestUtils.intArray;
import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import jumpingalien.model.Mazub;
import jumpingalien.model.World;
import jumpingalien.model.exceptions.IllegalHeightException;
import jumpingalien.model.exceptions.IllegalPositionXException;
import jumpingalien.model.exceptions.IllegalPositionYException;
import jumpingalien.model.exceptions.IllegalWidthException;
import jumpingalien.model.helper.Animation;
import jumpingalien.model.helper.MazubAnimation;
import jumpingalien.model.helper.Orientation;
import jumpingalien.model.helper.VectorInt;
import jumpingalien.model.terrain.Terrain;
import jumpingalien.part2.facade.Facade;
import jumpingalien.part2.facade.IFacadePart2;
import jumpingalien.util.Sprite;
import jumpingalien.util.Util;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MazubTest {
	
	public static final int FEATURE_AIR = 0;
	public static final int FEATURE_SOLID = 1;
	public static final int FEATURE_WATER = 2;
	public static final int FEATURE_MAGMA = 3;
	
	private Sprite[] sprites;
	private Mazub alien;
	private World world;
	private IFacadePart2 facade;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		// Initiliaze some often used variables. These will be reset each test.
		
		facade = new Facade();
		
		sprites = spriteArrayForSize(3, 3);
		
		alien = facade.createMazub(100, 50, sprites);
		world = facade.createWorld(50, 60, 15, 200, 150, 4, 1);
		
		for(int i = 0; i < 60; i++){
			facade.setGeologicalFeature(world, i, 0, FEATURE_SOLID);
		}
		
		facade.setMazub(world, alien);
	}

	@After
	public void tearDown() throws Exception {
	}	
	
	/************************************************ TIMERS ******************************************/
	
	/** Check if the timers are updated correctly when time is advanced */
	@Test
	public void testTimers(){
		alien.getTimer().setSinceEnemyCollision(0.0);
		assertEquals( 0.0, alien.getTimer().getSinceLastSprite(), Util.DEFAULT_EPSILON);
		assertEquals( 0.0, alien.getTimer().getSinceEnemyCollision(), Util.DEFAULT_EPSILON);
		facade.advanceTime(world, 0.005);
		assertEquals( 0.005, alien.getTimer().getSinceLastSprite(), Util.DEFAULT_EPSILON);
		assertEquals( 0.005, alien.getTimer().getSinceEnemyCollision(), Util.DEFAULT_EPSILON);
		facade.advanceTime(world, 0.15); // exactly the time of 2 sprite changes
		assertEquals( 0.005, alien.getTimer().getSinceLastSprite(), Util.DEFAULT_EPSILON);
		assertEquals( 0.155, alien.getTimer().getSinceEnemyCollision(), Util.DEFAULT_EPSILON);
	}
		
	/* from test case 1 */
	/**
	 * Check if Mazub is initialized with the right X position.
	 */
	@Test
	public void constructorPositionX_LegalCase(){
		Mazub alien = facade.createMazub(10, 5, spriteArrayForSize(2, 2) );
		assertEquals(alien.getRoundedPositionX(), 10);
	}
	
	/**
	 * Check if Mazub is initialized with the right Y position.
	 */
	@Test
	public void constructorPositionY_LegalCase(){
		Mazub alien = facade.createMazub(10, 5, spriteArrayForSize(2, 2) );
		assertEquals(alien.getRoundedPositionY(), 5);
	}
	
	/**
	 * Check if Mazub cannot be initialized with an out of bound X position.
	 */
	@Test(expected=IllegalPositionXException.class)
	public void constructorPositionX_IllegalCase() throws Exception{
		new Mazub(-5, 0, spriteArrayForSize(2, 2) );
	}
	
	/**
	 * Check if Mazub cannot be initialized with an out of bound Y position.
	 */
	@Test(expected=IllegalPositionYException.class)
	public void constructorPositionY_IllegalCase() throws Exception{
		new Mazub(0, -5, spriteArrayForSize(2, 2) );
	}
	
	/**
	 * Check if Mazub's default initial horizontal velocity is equal to 1.0 .
	 */
	@Test
	public void correctDefaultVelocityXInit() {
		assertEquals(1.0, alien.getVelocityXInit(), Util.DEFAULT_EPSILON);
	}
	
	/**
	 * Check if Mazub's default maximal horizontal velocity is equal to 3.0 .
	 */
	@Test
	public void correctDefaultVelocityXMax() {
		assertEquals(3.0, alien.getVelocityXMax(), Util.DEFAULT_EPSILON);
	}
	
	/**
	 * Check if the helper classes (Timer and Animation) are properly initiated.
	 */
	@Test
	public void helperClassesCorrectlyInitiated(){
		Mazub alien = facade.createMazub(10, 5, spriteArrayForSize(2, 2) );
		
		assertNotNull(alien.getTimer());
		assertNotNull(alien.getAnimation());
	}
	
	/**
	 * Check number of mazubs
	 */
	@Test
	public void testMazubInWorld(){
		assertEquals(1, Mazub.getNbInWorld(world));
		assertEquals(alien, alien.getWorld().getMazub());
	}

	/*************************************** WIDTH AND HEIGHT ***************************************/

	/**
	 * check if the sprite width is correctly retrieved.
	 */
	@Test
	public void checkDefaultSpriteWidth() {
		// The default idle sprite index is 0
		assertEquals( facade.getSize(alien)[0], sprites[0].getWidth() );
	}
	
	/**
	 * Check if the sprite height is correctly retrieved.
	 */
	@Test
	public void checkDefaultSpriteHeight() {
		// The default idle sprite index is 0
		assertEquals( facade.getSize(alien) [1], sprites[0].getHeight() );
	}
	
//	/********************************************* POSITION ****************************************/
//	
	/**
	 * Check if the positions of Mazub are correctly rounded down to the right integer.
	 */
	@Test
	public void positionsCorrectlyRounded(){
		facade.advanceTime(world, 0.1);
		assertTrue(alien.isOnGround());
		assertEquals(49,alien.getRoundedPositionY());
		facade.startMoveRight(alien);
		assertEquals(Orientation.RIGHT, alien.getOrientation());
		
		facade.startJump(alien);
		facade.advanceTime(world, 0.01);
		assertFalse(alien.isOnGround());
		facade.advanceTime(world, 0.13);

		// x_new [m] = 1 [m] + 1 [m/s] * 0.14 [s] + 1/2 * 0.9 [m/s^2] * (0.14 [s]) ^2 =
		// 1.14882 [m] = 114.882 [cm] 
		// y_new [m] = 0.49 [m] + 8 [m/s] * 0.14 [s] + 1/2 * (-10.0) [m/s^2] * (0.14 [s])^2 =
		// 1.022 [m] = 151.20 [cm]
		// The position of Mazub should be (114, 151) because the doubles are always rounded down.

		assertEquals(114, alien.getRoundedPositionX());
		assertEquals(151, alien.getRoundedPositionY());
	}
	
	/**
	 * Check if canHaveAsPosition() correctly determines which positions are valid and which
	 * are not.
	 * 
	 * world width is 60*50 px = 3000 px, world height is 15 * 50 px = 750px
	 */
	@Test
	public void correctValidRoundedPositions(){
		assertFalse(alien.canHaveAsPositionX(-1));
		assertTrue(alien.canHaveAsPositionX(0));
		assertTrue(alien.canHaveAsPositionX(546));
		assertTrue(alien.canHaveAsPositionX(3000 - alien.getWidth()));
		assertFalse(alien.canHaveAsPositionX(3001 - alien.getWidth()));
		
		assertFalse(alien.canHaveAsPositionY(-1));
		assertTrue(alien.canHaveAsPositionY(0));
		assertTrue(alien.canHaveAsPositionY(546));
		assertTrue(alien.canHaveAsPositionY(750 - alien.getHeight()));
		assertFalse(alien.canHaveAsPositionY(751 - alien.getHeight()));
	}
	
	/**
	 * Check if canHaveAsPosition() correctly determines which positions are valid and which
	 * are not.
	 * 
	 * world width is 20*50 px = 1000 px, world height is 15 * 50 px = 750px
	 */
	@Test
	public void correctValidPositions(){
		assertFalse(alien.canHaveAsPositionX(-0.001));
		assertTrue(alien.canHaveAsPositionX(0.12));
		assertTrue(alien.canHaveAsPositionX(546.654));
		assertTrue(alien.canHaveAsPositionX(3000 - alien.getWidth()));
		assertFalse(alien.canHaveAsPositionX(3001.0 - alien.getWidth()));
		
		assertFalse(alien.canHaveAsPositionY(-0.001));
		assertTrue(alien.canHaveAsPositionY(0.12));
		assertTrue(alien.canHaveAsPositionY(546.654));
		assertTrue(alien.canHaveAsPositionY(750 - alien.getHeight()));
		assertFalse(alien.canHaveAsPositionY(751.0 - alien.getHeight()));
	}
	
	
	/************************************************** MOVEMENT ********************************************/

	@Test
	public void startMoveRightCorrect() {
		IFacadePart2 facade = new Facade();

		// 2 vertical tiles, size 500px
		// ....
		// a...
		// XXXX
		// XXXX
		World world = facade.createWorld(500, 1, 2, 1, 1, 1, 1);
		facade.setGeologicalFeature(world, 0, 0, FEATURE_SOLID);
		Mazub alien = facade.createMazub(0, 499, spriteArrayForSize(3, 3));
		facade.setMazub(world, alien);
		facade.startMoveRight(alien);
		assertEquals(Orientation.RIGHT, alien.getOrientation());
		facade.advanceTime(world, 0.1);

		// x_new [m] = 0 + 1 [m/s] * 0.1 [s] + 1/2 0.9 [m/s^2] * (0.1 [s])^2 =
		// 0.1045 [m] = 10.45 [cm], which falls into pixel (10, 0)

		assertArrayEquals(intArray(10, 499), facade.getLocation(alien));
	}
	

	@Test
	public void startMoveLeftCorrect() {
		IFacadePart2 facade = new Facade();

		// 2 vertical tiles, size 500px
		// ....
		// a...
		// XXXX
		// XXXX
		World world = facade.createWorld(500, 1, 2, 1, 1, 1, 1);
		facade.setGeologicalFeature(world, 0, 0, FEATURE_SOLID);
		Mazub alien = facade.createMazub(50, 499, spriteArrayForSize(3, 3));
		facade.setMazub(world, alien);
		facade.startMoveLeft(alien);
		assertEquals(Orientation.LEFT, alien.getOrientation());
		facade.advanceTime(world, 0.1);

		// x_new [m] = 0.5 + 1 [m/s] * 0.1 [s] + 1/2 0.9 [m/s^2] * (0.1 [s])^2 =
		// 0.5-0.1045 [m] = 0.5 - 10.45 [cm], which falls into pixel (39, 0)

		assertArrayEquals(intArray(39, 499), facade.getLocation(alien));
	}
	
	/**
	 * Move left & right test
	 */
	@Test
	public void moveLeftRightEndCorrect(){
		facade.startGame(world);
		facade.advanceTime(world, 0.1);
		
		facade.startMoveRight(alien);
		assertEquals(Orientation.RIGHT, alien.getOrientation());
		assertEquals(1.0, alien.getVelocityX(), Util.DEFAULT_EPSILON);
		facade.advanceTime(world, 0.1);
		facade.startMoveLeft(alien);
		assertEquals(Orientation.LEFT, alien.getOrientation());
		assertEquals(-1.0, alien.getVelocityX(), Util.DEFAULT_EPSILON);
		facade.advanceTime(world, 0.1);
		facade.endMoveLeft(alien);
		assertEquals(Orientation.RIGHT, alien.getOrientation());
		assertEquals(1.0, alien.getVelocityX(), Util.DEFAULT_EPSILON);
	}	
	
	/** 
	 * Checks if Mazub is on target tile 
	 * */
	
	@Test
	public void testTargetTile(){
		facade.startGame(world);
		facade.advanceTime(world, 0.1);
		
		// Target tile is on tile (4, 1) , which as as pixel coordinates (200, 50)
		// Mazubs starts at (100, 50), so move 100 px to right (actually less because of the width of mazub)
		// t = 0.75 s
		
		facade.startMoveRight(alien);
		facade.advanceTime(world, 0.2);
		facade.advanceTime(world, 0.2);
		facade.advanceTime(world, 0.2);
		facade.advanceTime(world, 0.15);
		assertTrue(facade.isGameOver(world));
		assertTrue(facade.didPlayerWin(world));
		assertTrue(alien.isOnTargetTile());
	}
	

	/** 
	 * Checks if Mazub is water
	 * */
	
	@Test
	public void testInWater(){
		facade.setGeologicalFeature(world, 3, 1, FEATURE_WATER);
		facade.startGame(world);
		facade.advanceTime(world, 0.1);
		
		// Water is on tile (3, 1) , which as as pixel coordinates (150, 50)
		// Mazubs starts at (100, 50), so move 100 px to right (actually less because of the width of mazub)
		// t = 0.43 s
		
		facade.startMoveRight(alien);
		facade.advanceTime(world, 0.2);
		facade.advanceTime(world, 0.2);
		facade.advanceTime(world, 0.03);
		assertTrue(alien.isSubmergedIn(Terrain.WATER));
	}

	
	/** 
	 * Checks if Mazub is magma
	 * */
	
	@Test
	public void testInMagma(){
		facade.setGeologicalFeature(world, 3, 1, FEATURE_MAGMA);
		facade.startGame(world);
		facade.advanceTime(world, 0.1);
		
		// Magma is on tile (3, 1) , which as as pixel coordinates (150, 50)
		// Mazubs starts at (100, 50), so move 100 px to right (actually less because of the width of mazub)
		// t = 0.43 s
		
		facade.startMoveRight(alien);
		facade.advanceTime(world, 0.2);
		facade.advanceTime(world, 0.2);
		facade.advanceTime(world, 0.03);
		assertTrue(alien.isSubmergedIn(Terrain.MAGMA));
	}
	
	/********************************************* HITPOINTS ***************************************/
	
	/** 
	 * Checks if hit-points are correctly changed when in water. Also checks termination.
	 */
	@Test
	public void damageInWaterAndTerminate(){
		facade.setGeologicalFeature(world, 2, 1, FEATURE_WATER);
		facade.startGame(world);
		assertTrue(alien.isSubmergedIn(Terrain.WATER));
		facade.advanceTime(world, 0.01);
		assertEquals(100, alien.getNbHitPoints()); // No hitpoints deduced at initial contact with water
		facade.advanceTime(world, 0.191);
		assertEquals(98, alien.getNbHitPoints()); // 2 hitpoints deduced
		
		for(int i = 96; i >= 0; i-=2){
			facade.advanceTime(world, 0.10);
			facade.advanceTime(world, 0.10);
			assertEquals(0.0, alien.getTimer().getSinceLastTerrainDamage(Terrain.WATER), Util.DEFAULT_EPSILON);
			assertEquals(i, alien.getNbHitPoints()); // No hitpoints deduced
		}
		
		/* Termination */
		assertTrue(alien.isKilled());
		assertEquals(0.0, alien.getTimer().getSinceKilled(), Util.DEFAULT_EPSILON);
		assertFalse(alien.isTerminated());	
		assertTrue(alien.getWorld().isGameOver());
		assertFalse(alien.getWorld().didPlayerWin());
		
		for(int i = 0; i < 3; i++){
			facade.advanceTime(world, 0.10);
			facade.advanceTime(world, 0.10);
			assertEquals(0.20 * (i+1), alien.getTimer().getSinceKilled(), Util.DEFAULT_EPSILON);
		}
	}

	/** 
	 * Checks if hit-points are correctly changed when in Magma
	 */
	@Test
	public void damageInMagma(){
		facade.setGeologicalFeature(world, 2, 1, FEATURE_MAGMA);
		facade.startGame(world);
		
		assertTrue(alien.isSubmergedIn(Terrain.MAGMA));
		facade.advanceTime(world, 0.001);
		assertEquals(0.0, alien.getTimer().getSinceLastTerrainDamage(Terrain.MAGMA), Util.DEFAULT_EPSILON);
		assertEquals(50, alien.getNbHitPoints()); // 50 hitpoints deduced at initial contact with magma
		facade.advanceTime(world, 0.010);
		facade.advanceTime(world, 0.191);
		assertEquals(0.0, alien.getTimer().getSinceLastTerrainDamage(Terrain.MAGMA), Util.DEFAULT_EPSILON);
		assertEquals(0, alien.getNbHitPoints()); // 50 hitpoints deduced
		
		assertTrue(alien.isKilled());
		assertEquals(0.0, alien.getTimer().getSinceKilled(), Util.DEFAULT_EPSILON);
		assertFalse(alien.isTerminated());
	}

	/********************************************* VELOCITY ****************************************/
	
	@Test
	public void startMoveRightMaxSpeedAtRightTime() {
		IFacadePart2 facade = new Facade();

		// 2 vertical tiles, size 500px
		// ....
		// a...
		// XXXX
		// XXXX
		World world = facade.createWorld(500, 1, 2, 1, 1, 1, 1);
		facade.setGeologicalFeature(world, 0, 0, FEATURE_SOLID);
		Mazub alien = facade.createMazub(0, 499, spriteArrayForSize(3, 3));
		facade.setMazub(world, alien);
		facade.startMoveRight(alien);
		assertEquals(Orientation.RIGHT, alien.getOrientation());
		// maximum speed reached after 20/9 seconds
		for (int i = 0; i < 100; i++) {
			facade.advanceTime(world, 0.2 / 9);
		}

		assertArrayEquals(doubleArray(3, 0), facade.getVelocity(alien),
				Util.DEFAULT_EPSILON);
	}
	
	/**
	 * Check if canHaveAsVelocityX() correctly determines which velocities are valid and which are not.
	 */
	@Test
	public void correctValidVelocityX(){
		IFacadePart2 facade = new Facade();
		Mazub alien = facade.createMazub(0, 0, spriteArrayForSize(2, 2));
		
		assertTrue(alien.canHaveAsVelocityX(0));
		assertTrue(alien.canHaveAsVelocityX(1.0));
		assertTrue(alien.canHaveAsVelocityX(-1.75));
		assertTrue(alien.canHaveAsVelocityX(3.0));
		assertFalse(alien.canHaveAsVelocityX(3.00001));
		assertFalse(alien.canHaveAsVelocityX(54.54));
	}
	
	/**
	 * Check if canHaveAsVelocityXMax() correctly determines which maximal velocities are valid for 
	 * this instance.
	 */
	@Test
	public void correctMaximalVelocityX(){
		assertTrue( alien.canHaveAsVelocityXMax( alien.getVelocityXInit()));
		assertFalse( alien.canHaveAsVelocityXMax( alien.getVelocityXInit() - 0.001  ));
	}
	
	
	/****************************************** ACCELERATION **************************************/
	@Test
	public void testZeroAccellerationOnGround() {
		IFacadePart2 facade = new Facade();

		// 2 vertical tiles, size 500px
		// ....
		// a...
		// XXXX
		// XXXX
		World world = facade.createWorld(500, 1, 2, 1, 1, 1, 1);
		facade.setGeologicalFeature(world, 0, 0, FEATURE_SOLID);
		Mazub alien = facade.createMazub(0, 499, spriteArrayForSize(3, 3));
		facade.setMazub(world, alien);

		assertArrayEquals(doubleArray(0.0, 0.0), facade.getAcceleration(alien),
				Util.DEFAULT_EPSILON);
	}	
	
	@Test
	public void testAccellerationZeroWhenNotMoving() {
		facade.startGame(world);
		facade.advanceTime(world, 0.1);
		assertArrayEquals(doubleArray(0.0, 0.0), facade.getAcceleration(alien),
				Util.DEFAULT_EPSILON);
	}
	
	/**
	 * Check if the acceleration of Mazub is 0.9 while running to the right.
	 */
	@Test
	public void checkAccelerationXWhileRunningRight(){
		facade.startGame(world);
		facade.advanceTime(world, 0.1);
		
		facade.startMoveRight(alien);
		assertEquals(Orientation.RIGHT, alien.getOrientation());
		
		assertEquals(0.9, facade.getAcceleration(alien)[0], Util.DEFAULT_EPSILON);
	}
	
	/**
	 * Check if the acceleration of Mazub is 0.9 while running to the left.
	 */
	@Test
	public void checkAccelerationXWhileRunningLeft(){
		facade.startGame(world);
		facade.advanceTime(world, 0.1);

		facade.startMoveLeft(alien);
		assertEquals(Orientation.LEFT, alien.getOrientation());
		
		assertEquals(-0.9, facade.getAcceleration(alien)[0], Util.DEFAULT_EPSILON);
	}
	
	/**
	 * Check if the acceleration of Mazub is 0 when running to the right is ended.
	 */
	@Test
	public void checkAccelerationXWhenRunningRightEnded(){
		facade.startGame(world);

		facade.startMoveRight(alien);
		assertEquals(Orientation.RIGHT, alien.getOrientation());
		
		facade.advanceTime(world, 0.10);
		facade.endMoveRight(alien);
		assertEquals(0, facade.getAcceleration(alien)[0], Util.DEFAULT_EPSILON);
	}
	
	/**
	 * Check if the horizontal velocity does not exceed the maximal horizontal 
	 * velocity after some time.
	 */
	
	@Test
	public void checkVelocityXNotExceedingVelocityXMax(){
		facade.startGame(world);

		facade.startMoveRight(alien);
		assertEquals(Orientation.RIGHT, alien.getOrientation());
		
		for (int i = 0; i < 100; i++) {
			facade.advanceTime(world,  0.2 / 9);
		}
		
		// Mazub reached his maximal velocity.
		assertEquals(alien.getVelocityXMax(), alien.getVelocityX(), Util.DEFAULT_EPSILON);
		assertEquals(544, alien.getRoundedPositionX());
		assertEquals(alien.getAccelerationX(), 0, Util.DEFAULT_EPSILON);
	}

	
	/********************************************* JUMPING ****************************************/

	/**
	 * Check the y-position when jumping.
	 */
	@Test
	public void jumpCorrectly() {
		facade.startGame(world);
		
		// wait until mazub is on ground
		facade.advanceTime(world, 0.1);
		assertTrue(alien.isOnGround());
		assertEquals(0, alien.getVelocityY(), Util.DEFAULT_EPSILON);
		
		facade.startJump(alien);
		facade.advanceTime(world, 0.1);
		assertFalse(alien.isOnGround());


		// y_new [m] = 0.49 [m] + 8 [m/s] * 0.1 [s] + 1/2 * (-10.0) [m/s^2] * (0.1 [s])^2 =
		// 1.24 [m] = 124.00 [cm], which falls into pixel (100, 124)µ
		assertArrayEquals(intArray(100, 124), facade.getLocation(alien));
		
	}

	/**
	 * Check endJump method.
	 */
	@Test
	public void endJumpCorrectly() {
		facade.startGame(world);
		
		// wait until mazub is on ground
		facade.advanceTime(world, 0.1);
		assertTrue(alien.isOnGround());
		facade.startJump(alien);
		facade.advanceTime(world, 0.05);
		assertFalse(alien.isOnGround());
		
		facade.advanceTime(world,  0.05);
		facade.endJump(alien);
		assertFalse(alien.isOnGround());
		assertTrue( Util.fuzzyEquals(0, facade.getVelocity(alien)[1]) );
		assertEquals(0, alien.getVelocityY(), Util.DEFAULT_EPSILON );
		
		// Wait until mazub is on ground
		facade.advanceTime(world, 0.05);
		assertFalse(alien.isOnGround());
		
		facade.advanceTime(world, 0.2);
		facade.advanceTime(world, 0.2);
		assertTrue(alien.isOnGround());
		
		assertTrue( Util.fuzzyEquals(49, facade.getLocation(alien)[1]) );
		assertEquals(0, alien.getVelocityY(), Util.DEFAULT_EPSILON );
	}
	
	/**
	 * Check if Mazub is back on the ground on the expected moment, after invoking endJump.
	 */
	@Test
	public void backOnGroundCorrectly() {
		facade.startGame(world);
		facade.advanceTime(world, 0.1);
		
		assertTrue(alien.isOnGround());
		
		facade.startJump(alien);
		facade.advanceTime(world, 0.025);
		assertFalse(alien.isOnGround());
		facade.endJump(alien);
		
		// Height after 0.025 seconds:
		// y_new [m] = 0.49 + 8 [m/s] * 0.025 [s] + 1/2 * (-10.0) [m/s^2] * (0.025 [s])^2 =
		// 0.49+ 0.1968750 [m] = 49+19.68750 [cm], which falls into pixel (0, 49+20) = (0, 69)
		assertArrayEquals(intArray(100, 49+20), facade.getLocation(alien));

		facade.advanceTime(world, 0.199);
		// Time step until Mazub has reached the ground:
		// 0.1968750 [m] + 0 [m/s] * dt [s] + 1/2 * (-10.0) [m/s^2] * (dt [s])^2 = 0
		// Solving the equation for a positive solution gives: dt = 0.1984313483
		
		facade.advanceTime(world, 0.199);
		assertArrayEquals(intArray(100, 49), facade.getLocation(alien));
	}
	
	/**
	 * Check if Mazub's vertical velocity is equal to zero at a certain moment during his jump. Also checks if
	 * Mazub is still jumping at that moment.
	 */
	@Test
	public void midAirVelocityZero(){
		facade.startGame(world);
		facade.advanceTime(world, 0.1);
		
		facade.startJump(alien);
			
		// velocity_y_new [m/s] = 8 [m/s] - 10 [m/s^2] * dt [s] = 0 [m/s]
		// solving for dt we find: dt = 0.8 s
		
		for (int i = 0; i < 10; i++){
			facade.advanceTime(world, 0.08);
		}
		
		assertEquals(0, facade.getVelocity(alien)[0], Util.DEFAULT_EPSILON);	
		assertFalse(alien.isOnGround());
	}
	
	/********************************************* DUCKING ****************************************/
	
	/**
	 * Check if Mazub's maximal horizontal velocity while ducking is correct.
	 */
	@Test
	public void maxSpeedDuckingCorrectly() {
		facade.startGame(world);
		facade.startDuck(alien);
		assertTrue(alien.isDucking());
		
		facade.startMoveRight(alien);
		assertEquals(Orientation.RIGHT, alien.getOrientation());
		facade.advanceTime(world, 0.1);	
		
		// Horizontal velocity after a time step of 0.1 seconds.
		// velocity_x_new [m/s] = 1.0 [m/s] + 0.9 [m/s^2] * 0.1 [s] = 1.09 [m/s]
		// However the velocity should be limited to 1 m/s whilst ducking, so the new velocity is equal 
		// to 1 m/s.

		assertEquals(1, facade.getVelocity(alien)[0], Util.DEFAULT_EPSILON);
	}
	
	/**
	 * Check if Mazub's acceleration is correct when he was moving right
	 * whilst ducking and then stops ducking while still moving right.
	 */
	@Test
	public void correctAccelerationStopDuckingWhileMoving() {
		facade.startGame(world);
		facade.startDuck(alien);
		assertTrue(alien.isDucking());
		
		facade.startMoveRight(alien);
		assertEquals(Orientation.RIGHT, alien.getOrientation());
		facade.advanceTime(world, 0.1);
		
		facade.endDuck(alien);
		assertFalse(alien.isDucking());
		facade.advanceTime(world, 0.001);
		
		assertEquals(0.9, facade.getAcceleration(alien)[0], Util.DEFAULT_EPSILON);
	}
	
	/**
	 * Check if Mazub's maximal horizontal velocity is correct when he was moving right at maximum velocity
	 * whilst ducking and then stops ducking while still moving right.
	 */
	@Test
	public void correctSpeedStopDuckingWhileMoving() {
		facade.startGame(world);
		facade.startDuck(alien);
		assertTrue(alien.isDucking());
		
		facade.startMoveRight(alien);
		assertEquals(Orientation.RIGHT, alien.getOrientation());
		facade.advanceTime(world, 0.1);
		
		// Horizontal velocity of Mazub is now equal to the maximal velocity while ducking, which is 1 m/s.
		
		facade.endDuck(alien);
		assertFalse(alien.isDucking());
		facade.advanceTime(world, 0.05);
		
		// velocity_x_new [m/s] = 1 [m/s] + 0.9 [m/s^2] * 0.05[s] = 1.045 [m/s]
		
		assertEquals(1.045, facade.getVelocity(alien)[0], Util.DEFAULT_EPSILON);
	}
	
	/********************************************* TIMERS ****************************************/
	
	/**
	 * Check if the time since the last move by Mazub was made, is tracked correctly.
	 */
	@Test
	public void correctTimeSinceLastMove() {
		facade.startGame(world);
		facade.startMoveLeft(alien);
		facade.advanceTime(world, 0.15);
		facade.endMoveLeft(alien);
		for (int i = 0; i < 4; i++){
			facade.advanceTime(world, 0.10);
		}
		
		assertEquals(0.4, alien.getTimer().getSinceLastMove(), Util.DEFAULT_EPSILON);
	}
	

	/**
	 * Check if the time since the last sprite by Mazub was selected, is tracked correctly.
	 */
	@Test
	public void correctTimeSinceLastSprite(){
		facade.startGame(world);
		facade.advanceTime(world, 0.020);
		
		assertEquals(0.020, alien.getTimer().getSinceLastSprite(), Util.DEFAULT_EPSILON);
		
		for(int i = 0; i < 10; i++){
			facade.advanceTime(world, 0.10);
		}
		
		// total time passed: 0.020 + 10 * 0.010 = 0.120;
		// calculated time since last sprite: 0.120 - 0.075 = 0.045
		assertEquals(0.045, alien.getTimer().getSinceLastSprite(),  Util.DEFAULT_EPSILON);
	}
	
	/********************************************* SPRITES ****************************************/
	@Test
	public void testWalkAnimationLastFrame() {
		IFacadePart2 facade = new Facade();

		// 2 vertical tiles, size 500px
		// ....
		// a...
		// XXXX
		// XXXX
		World world = facade.createWorld(500, 1, 2, 1, 1, 1, 1);
		facade.setGeologicalFeature(world, 0, 0, FEATURE_SOLID);

		int m = 10;
		Sprite[] sprites = spriteArrayForSize(3, 3, 10 + 2 * m);
		Mazub alien = facade.createMazub(0, 499, sprites);
		facade.setMazub(world, alien);

		facade.startMoveRight(alien);
		assertEquals(Orientation.RIGHT, alien.getOrientation());
		
		facade.advanceTime(world, 0.005);
		for (int i = 0; i < m; i++) {
			facade.advanceTime(world, 0.075);
		}

		assertEquals(sprites[8 + m], facade.getCurrentSprite(alien));
	}
	
	/**
	 * Check ducking sprite.
	 */
	@Test
	public void spriteDucking(){	
		facade.startGame(world);
		// The sprite index should be 0
		assertEquals(sprites[0], facade.getCurrentSprite(alien));
		
		facade.startDuck(alien);
		facade.advanceTime(world, 0.010);
		
		// The sprite index should be 1
		assertEquals(sprites[1], facade.getCurrentSprite(alien));
		
		facade.endDuck(alien);
		facade.advanceTime(world, 0.010);

		// The sprite index should be 0
		assertEquals(sprites[0], facade.getCurrentSprite(alien));
	}
	
	/**
	 * Check the sprite:
	 * Mazub is not moving horizontally but its last horizontal movement was to the right
	 * 						(within 1s), and the character is not ducking. (index 2)
	 */
	@Test
	public void spriteHasMovedRightAndNotDucking(){
		facade.startGame(world);
		facade.startMoveRight(alien);
	assertEquals(Orientation.RIGHT, alien.getOrientation());
		facade.advanceTime(world, 0.10);
		
		facade.endMoveRight(alien);
		for(int i = 0; i < 10 ; i ++){
			facade.advanceTime(world, 0.10);
		}
		
		// Total time passed since last move right is one second.
		// Therefore, the sprite index should be 2.
		assertEquals(sprites[2], facade.getCurrentSprite(alien));
	}
	
	/**
	 * Check if the sprite index is set back to 0 (which represents the idle sprite)
	 * just after the one second limit.
	 */
	@Test
	public void spriteIdleAfterRunning(){
		facade.startGame(world);
		facade.startMoveRight(alien);
		facade.advanceTime(world, 0.10);
		
		facade.endMoveRight(alien);
		for(int i = 0; i < 10 ; i ++){
			facade.advanceTime(world, 0.10);
		}
		
		facade.advanceTime(world, 0.001);
		
		// Total time passed since last move right is one second.
		// Therefore, the sprite index should be 2.
		assertEquals(sprites[0], facade.getCurrentSprite(alien));
	}
	
	/**
	 * Check sprite for jumping and moving right.
	 */
	@Test
	public void spriteJumpingMovingRight(){
		facade.startGame(world);
		facade.advanceTime(world, 0.1);
		assertTrue(alien.isOnGround());
		
		facade.startMoveRight(alien);
		facade.startJump(alien);
		facade.advanceTime(world, 0.10);
		assertFalse(alien.isOnGround());
		assertEquals(Orientation.RIGHT, alien.getOrientation());
		
		// The sprite index should be 4.
		assertEquals(sprites[4], facade.getCurrentSprite(alien));
	}
	
	/**
	 * Check sprite for ducking and moving right.
	 */
	@Test
	public void spriteDuckingMovingRight(){
		facade.startGame(world);
		facade.startMoveRight(alien);
		facade.startDuck(alien);
		facade.advanceTime(world, 0.10);
		
		// The sprite index should be 6.
		assertEquals(sprites[6], facade.getCurrentSprite(alien));
	}
	
	/**
	 * Check sprite for ducking and moving left.
	 */
	@Test
	public void spriteDuckingMovingLeft(){
		facade.startGame(world);
		facade.startMoveLeft(alien);
		facade.startDuck(alien);
		facade.advanceTime(world, 0.10);
		
		// The sprite index should be 7.
		assertEquals(sprites[7], facade.getCurrentSprite(alien));
	}
	
	
	/**
	 * Check sprite for ducking after moving right.
	 */
	@Test
	public void spriteDuckingAfterMovingRight(){
		facade.startGame(world);
		facade.startMoveRight(alien);
		facade.startDuck(alien);
		facade.advanceTime(world, 0.10);
		facade.endMoveRight(alien);
		facade.advanceTime(world, 0.10);
		// The sprite index should be 6.
		assertEquals(sprites[6], facade.getCurrentSprite(alien));
	}
	
	/**
	 * Check sprite for ducking after moving left.
	 */
	@Test
	public void spriteDuckingAfterMovingLeft(){
		facade.startGame(world);
		facade.startMoveLeft(alien);
		facade.startDuck(alien);
		facade.advanceTime(world, 0.10);
		facade.endMoveLeft(alien);
		facade.advanceTime(world, 0.10);
		// The sprite index should be 7.
		assertEquals(sprites[7], facade.getCurrentSprite(alien));
	}
	
	
	/********************************************* ADVANCE TIME ****************************************/	
	
	// TODO MOET NAAR WORLD
	
	/**
	 * Check if advanceTime cannot be used with a negative time step.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void advanceTime_SmallerThanZeroCase(){
		world.advanceTime(-0.001);
	}
	
	/**
	 * Check if advanceTime cannot be used with a time step greater than 0.2.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void advanceTime_GreaterThanCase(){
		world.advanceTime(0.201);
	}
	
	/******************************************* COLLISION ****************************************/
	/**
	 * Check if horizontal speed is 0 after collision with a wall
	 *
	 */

	@Test
	public void collisionRight() {
		IFacadePart2 facade = new Facade();

		// 2 vertical tiles, size 500px
		// ....X
		// a...X
		// XXXXX
		// XXXXX
		World world = facade.createWorld(50, 4, 2, 1, 1, 1, 1);
		facade.setGeologicalFeature(world, 0, 0, FEATURE_SOLID);
		facade.setGeologicalFeature(world, 1, 0, FEATURE_SOLID);
		facade.setGeologicalFeature(world, 2, 0, FEATURE_SOLID);
		facade.setGeologicalFeature(world, 3, 0, FEATURE_SOLID);
		facade.setGeologicalFeature(world, 3, 1, FEATURE_SOLID);
		Mazub alien = facade.createMazub(0, 49, spriteArrayForSize(3, 3));
		facade.setMazub(world, alien);
		facade.startGame(world);
		
		facade.startMoveRight(alien);
		assertEquals(Orientation.RIGHT, alien.getOrientation());
		facade.advanceTime(world, 0.1);

		// x_new [m] = 0 + 1 [m/s] * 0.1 [s] + 1/2 0.9 [m/s^2] * (0.1 [s])^2 =
		// 0.1045 [m] = 10.45 [cm], which falls into pixel (10, 0)
		assertArrayEquals(intArray(10, 49), facade.getLocation(alien));
		
		for(int i = 0; i < 10; i++){
			facade.advanceTime(world, 0.2);
		}
		assertTrue(alien.doesOverlap(Orientation.RIGHT));
		assertEquals(3 * 50 - alien.getWidth() + 1, alien.getRoundedPositionX()); // +1 is because it may overlap one pixel
		
	}
	
	/********************************************* HELPER CLASSES ***********************************************/
	@Test(expected = IllegalWidthException.class)
	public void invalidSpriteWidth(){
		Sprite[] sprites = spriteArrayForSize(-5, 3);
		new Animation(alien, sprites);
	}
	
	@Test
	public void invalidSpriteWidth_checkException(){
		Sprite[] sprites = spriteArrayForSize(-5, 3);
		try{
			new MazubAnimation(alien, sprites);
		}catch(IllegalWidthException exc){
			assertEquals(-5, exc.getWidth(), Util.DEFAULT_EPSILON);
		}
		
	}
	
	@Test(expected = IllegalHeightException.class)
	public void invalidSpriteHeight(){
		Sprite[] sprites = spriteArrayForSize(5, -3);
		new Animation(alien, sprites);
	}
	
	@Test
	public void invalidSpriteHeight_checkException(){
		Sprite[] sprites = spriteArrayForSize(5, -3);
		try{
			new MazubAnimation(alien, sprites);
		}catch(IllegalHeightException exc){
			assertEquals(-3, exc.getHeight(), Util.DEFAULT_EPSILON);
		}
		
	}
	
	
	// This test will only work when assertion errors are enabled in the run config
	@Test(expected = AssertionError.class)
	public void invalidNbOfSprites(){
		try{
			assert false;
			System.out.println("Assertion errors disabled! Test not executed!");
		}catch(AssertionError exc){
			// Assertion errors are enabled!
			Sprite[] sprites = spriteArrayForSize(5, 3, 2);
			new MazubAnimation(alien, sprites);
		}
	}
	
	// This test will only work when assertion errors are enabled in the run config
	@Test(expected = AssertionError.class)
	public void oddNbOfSprites(){
		try{
			assert false;
			System.out.println("Assertion errors disabled! Test not executed!");
		}catch(AssertionError exc){
			// Assertion errors are enabled!
			Sprite[] sprites = spriteArrayForSize(5, 3, 11);
			new MazubAnimation(alien, sprites);
		}
	}
	
	public void vectorIntEqualsOther(){
		VectorInt vector1 = new VectorInt(20, 18);
		VectorInt vector2 = new VectorInt(16, 18);
		VectorInt vector3 = new VectorInt(20, 15);
		VectorInt vector4 = new VectorInt(20, 18);
		
		assertTrue(vector1.equals(vector4));
		assertFalse(vector1.equals(vector3));
		assertFalse(vector1.equals(vector2));
		assertFalse(vector1.equals(alien));
	}
	
	public void vectorIntToArary(){
		VectorInt vector1 = new VectorInt(20, 18);
		assertArrayEquals(new int[] {20, 18}, vector1.toArray());
	}


}
