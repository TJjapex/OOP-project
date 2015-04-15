package jumpingalien.part1.tests;

import static jumpingalien.tests.util.TestUtils.intArray;
import static jumpingalien.tests.util.TestUtils.doubleArray;
import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import static org.junit.Assert.*;
import jumpingalien.model.Mazub;
import jumpingalien.model.exceptions.IllegalPositionXException;
import jumpingalien.model.exceptions.IllegalPositionYException;
import jumpingalien.part2.facade.Facade;
import jumpingalien.part1.facade.IFacade;
import jumpingalien.util.Sprite;
import jumpingalien.util.Util;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * A test suite for the class Mazub and helper classes.
 * 
 * @author Thomas Verelst, Hans Cauwenbergh
 * @version 1.0
 */
public class TestCase {

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
		sprites = spriteArrayForSize(3, 4);
		alien_0_0 = new Mazub(0, 0, sprites);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	private IFacade facade;
	private Mazub alien_0_0;
	private Sprite[] sprites;
	
	/********************************************* CONSTRUCTOR ****************************************/
	
	/**
	 * Checks if Mazub is initialized with the right X position.
	 */
	@Test
	public void constructorPositionX_LegalCase(){
		Mazub alien = facade.createMazub(10, 5, spriteArrayForSize(2, 2) );
		assertEquals(alien.getRoundedPositionX(), 10);
	}
	
	/**
	 * Checks if Mazub is initialized with the right Y position.
	 */
	@Test
	public void constructorPositionY_LegalCase(){
		Mazub alien = facade.createMazub(10, 5, spriteArrayForSize(2, 2) );
		assertEquals(alien.getRoundedPositionY(), 5);
	}
	
	/**
	 * Checks if Mazub cannot be initialized with an out of bound X position.
	 */
	@Test(expected=IllegalPositionXException.class)
	public void constructorPositionX_IllegalCase() throws Exception{
		new Mazub(-5, 0, spriteArrayForSize(2, 2) );
	}
	
	/**
	 * Checks if Mazub cannot be initialized with an out of bound Y position.
	 */
	@Test(expected=IllegalPositionYException.class)
	public void constructorPositionY_IllegalCase() throws Exception{
		new Mazub(0, -5, spriteArrayForSize(2, 2) );
	}
	
	/**
	 * Checks if Mazub's default initial horizontal velocity is equal to 1.0 .
	 */
	@Test
	public void correctDefaultVelocityXInit() {
		assertEquals(1.0, alien_0_0.getVelocityXInit(), Util.DEFAULT_EPSILON);
	}
	
	/**
	 * Checks if Mazub's default maximal horizontal velocity is equal to 3.0 .
	 */
	@Test
	public void correctDefaultVelocityXMax() {
		assertEquals(3.0, alien_0_0.getVelocityXMax(), Util.DEFAULT_EPSILON);
	}
	
	/**
	 * Checks if the helper classes (Timer and Animation) are properly initiated.
	 */
	@Test
	public void helperClassesCorrectlyInitiated(){
		Mazub alien = facade.createMazub(10, 5, spriteArrayForSize(2, 2) );
		
		assertNotNull(alien.getTimer());
		assertNotNull(alien.getAnimation());
	}
	
	/*************************************** WIDTH AND HEIGHT ***************************************/

	/**
	 * Checks if the sprite width is correctly retrieved.
	 */
	@Test
	public void checkDefaultSpriteWidth() {
		// The default idle sprite index is 0
		assertEquals( facade.getSize(alien_0_0)[0], sprites[0].getWidth() );
	}
	
	/**
	 * Checks if the sprite height is correctly retrieved.
	 */
	@Test
	public void checkDefaultSpriteHeight() {
		// The default idle sprite index is 0
		assertEquals( facade.getSize(alien_0_0) [1], sprites[0].getHeight() );
	}
	
	/********************************************* POSITION ****************************************/
	
	/**
	 * Checks if the positions of Mazub are correctly rounded down to the right integer.
	 */
	@Test
	public void positionsCorrectlyRounded(){
		facade.startMoveRight(alien_0_0);
		facade.startJump(alien_0_0);
		facade.advanceTime(alien_0_0, 0.14);
		
		// x_new [m] = 0 [m] + 1 [m/s] * 0.14 [s] + 1/2 * 0.9 [m/s^2] * (0.14 [s]) ^2 =
		// 0.14882 [m] = 14.882 [cm] 
		// y_new [m] = 0 [m] + 8 [m/s] * 0.14 [s] + 1/2 * (-10.0) [m/s^2] * (0.14 [s])^2 =
		// 1.022 [m] = 102.20 [cm]
		// The position of Mazub should be (14, 102) because the doubles are always rounded down.

		assertEquals(14, alien_0_0.getRoundedPositionX());
		assertEquals(102, alien_0_0.getRoundedPositionY());
	}
	
	/**
	 * Checks if isValidRoundedPosition() correctly determines which positions are valid and which
	 * are not.
	 */
//	@Test
//	public void correctValidRoundedPositions(){
//		assertFalse(Mazub.isValidRoundedPositionX(-1));
//		assertTrue(Mazub.isValidRoundedPositionX(0));
//		assertTrue(Mazub.isValidRoundedPositionX(546));
//		assertFalse(Mazub.isValidRoundedPositionX(1024));
//		assertTrue(Mazub.isValidRoundedPositionX(1023));
//		assertFalse(Mazub.isValidRoundedPositionY(-1));
//		assertTrue(Mazub.isValidRoundedPositionY(0));
//		assertTrue(Mazub.isValidRoundedPositionY(546));
//		assertFalse(Mazub.isValidRoundedPositionY(768));
//		assertTrue(Mazub.isValidRoundedPositionY(767));
//	}
//	
//	/**
//	 * Checks if isValidPosition() correctly determines which positions are valid and which are not.
//	 */
//	@Test
//	public void correctValidPositions(){
//		assertFalse(Mazub.isValidPositionX(-0.00001));
//		assertTrue(Mazub.isValidPositionX(0.00001));
//		assertTrue(Mazub.isValidPositionX(546.72));
//		assertFalse(Mazub.isValidPositionX(1024.00001));
//		assertTrue(Mazub.isValidPositionX(1023.99999));
//		assertFalse(Mazub.isValidPositionY(-0.00001));
//		assertTrue(Mazub.isValidPositionY(0.00001));
//		assertTrue(Mazub.isValidPositionY(546.72));
//		assertFalse(Mazub.isValidPositionY(768.00001));
//		assertTrue(Mazub.isValidPositionY(767.99999));
//	}
//	
	/********************************************* VELOCITY ****************************************/
	
	/**
	 * Checks if isValidVelocityX() correctly determines which velocities are valid and which are not.
	 */
	@Test
	public void correctValidVelocityX(){
		IFacade facade = new Facade();
		Mazub alien = facade.createMazub(0, 0, spriteArrayForSize(2, 2));
		
		assertTrue(alien.isValidVelocityX(0));
		assertTrue(alien.isValidVelocityX(1.0));
		assertTrue(alien.isValidVelocityX(-1.75));
		assertTrue(alien.isValidVelocityX(3.0));
		assertFalse(alien.isValidVelocityX(3.00001));
		assertFalse(alien.isValidVelocityX(54.54));
	}
	
	/**
	 * Checks if canHaveAsVelocityXMax() correctly determines which maximal velocities are valid for 
	 * this instance.
	 */
	@Test
	public void correctMaximalVelocityX(){
		assertTrue( alien_0_0.canHaveAsVelocityXMax( alien_0_0.getVelocityXInit()));
		assertFalse( alien_0_0.canHaveAsVelocityXMax( alien_0_0.getVelocityXInit() - 0.001  ));
	}
	
	
	/****************************************** ACCELERATION **************************************/
	
	/**
	 * Checks if the acceleration of Mazub is 0.9 while running to the right.
	 */
	@Test
	public void checkAccelerationXWhileRunningRight(){
		facade.startMoveRight(alien_0_0);
		assertEquals(0.9, facade.getAcceleration(alien_0_0)[0], Util.DEFAULT_EPSILON);
	}
	
	/**
	 * Checks if the acceleration of Mazub is 0 when running to the right is ended.
	 */
	@Test
	public void checkAccelerationXWhenRunningRightEnded(){
		facade.startMoveRight(alien_0_0);
		facade.advanceTime(alien_0_0, 0.10);
		facade.endMoveRight(alien_0_0);
		assertEquals(0, facade.getAcceleration(alien_0_0)[0], Util.DEFAULT_EPSILON);
	}
	
	/**
	 * Checks if the horizontal velocity does not exceed the maximal horizontal 
	 * velocity after some time.
	 */
	@Test
	public void checkVelocityXNotExceedingVelocityXMax(){
		facade.startMoveRight(alien_0_0);
		for (int i = 0; i < 100; i++) {
			facade.advanceTime(alien_0_0, 0.2 / 9);
		}
		// Mazub reached his maximal velocity.
		facade.advanceTime(alien_0_0, 0.1);
		assertEquals(alien_0_0.getVelocityXMax(), alien_0_0.getVelocityX(), Util.DEFAULT_EPSILON);
	}
	
	/**
	 * Checks if the acceleration of Mazub is 0 when he reached his maximal velocity.
	 */
	@Test
	public void checkAccelerationXAtMaxVelocityX(){
		facade.startMoveRight(alien_0_0);
		for (int i = 0; i < 100; i++) {
			facade.advanceTime(alien_0_0, 0.2 / 9);
		}
		// Mazub reached his maximal velocity.
		assertEquals(alien_0_0.getAccelerationX(), 0, Util.DEFAULT_EPSILON);
	}
	
	/********************************************* JUMPING ****************************************/

	/**
	 * Checks the y-position when jumping.
	 */
	@Test
	public void jumpCorrectly() {
		facade.startJump(alien_0_0);
		facade.advanceTime(alien_0_0, 0.1);

		// y_new [m] = 0 [m] + 8 [m/s] * 0.1 [s] + 1/2 * (-10.0) [m/s^2] * (0.1 [s])^2 =
		// 0.75 [m] = 75.00 [cm], which falls into pixel (0, 75)

		assertArrayEquals(intArray(0, 75), facade.getLocation(alien_0_0));
	}
	
	/**
	 * Checks endJump method.
	 */
	@Test
	public void endJumpCorrectly() {
		facade.startJump(alien_0_0);

		facade.advanceTime(alien_0_0, 0.1);
		facade.endJump(alien_0_0);
		
		assertTrue( Util.fuzzyEquals(0, facade.getVelocity(alien_0_0)[1]) );
	}
	
	/**
	 * Checks if Mazub is back on the ground on the expected moment, after invoking endJump.
	 */
	@Test
	public void backOnGroundCorrectly() {
		facade.startJump(alien_0_0);

		facade.advanceTime(alien_0_0, 0.025);
		facade.endJump(alien_0_0);
		
		// Height after 0.025 seconds:
		// y_new [m] = 0 + 8 [m/s] * 0.025 [s] + 1/2 * (-10.0) [m/s^2] * (0.025 [s])^2 =
		// 0.1968750 [m] = 19.68750 [cm], which falls into pixel (0, 20)

		// Time step until Mazub has reached the ground:
		// 0.1968750 [m] + 0 [m/s] * dt [s] + 1/2 * (-10.0) [m/s^2] * (dt [s])^2 = 0
		// Solving the equation for a positive solution gives: dt = 0.1984313483
		
		facade.advanceTime(alien_0_0, 0.199);
		assertArrayEquals(intArray(0, 0), facade.getLocation(alien_0_0));
	}
	
	/**
	 * Checks if Mazub's vertical velocity is equal to zero at a certain moment during his jump. Also checks if
	 * Mazub is still jumping at that moment.
	 */
	@Test
	public void midAirVelocityZero(){
		facade.startJump(alien_0_0);
			
		// velocity_y_new [m/s] = 8 [m/s] - 10 [m/s^2] * dt [s] = 0 [m/s]
		// solving for dt we find: dt = 0.8 s
		
		for (int i = 0; i < 10; i++){
			facade.advanceTime(alien_0_0, 0.08);
		}
		
		assertEquals(0, facade.getVelocity(alien_0_0)[0], Util.DEFAULT_EPSILON);	
		assert !alien_0_0.isOnGround();
	}
	
	/********************************************* DUCKING ****************************************/
	
	/**
	 * Checks if Mazub's maximal horizontal velocity while ducking is correct.
	 */
	@Test
	public void maxSpeedDuckingCorrectly() {
		facade.startDuck(alien_0_0);
		assertTrue(alien_0_0.isDucking());
		
		facade.startMoveRight(alien_0_0);
		facade.advanceTime(alien_0_0, 0.1);	
		
		// Horizontal velocity after a time step of 0.1 seconds.
		// velocity_x_new [m/s] = 1.0 [m/s] + 0.9 [m/s^2] * 0.1 [s] = 1.09 [m/s]
		// However the velocity should be limited to 1 m/s whilst ducking, so the new velocity is equal 
		// to 1 m/s.

		assertEquals(1, facade.getVelocity(alien_0_0)[0], Util.DEFAULT_EPSILON);
	}
	
	/**
	 * Checks if Mazub's acceleration is correct when he was moving right
	 * whilst ducking and then stops ducking while still moving right.
	 */
	@Test
	public void correctAccelerationStopDuckingWhileMoving() {
		facade.startDuck(alien_0_0);
		assertTrue(alien_0_0.isDucking());
		
		facade.startMoveRight(alien_0_0);
		facade.advanceTime(alien_0_0, 0.1);
		
		facade.endDuck(alien_0_0);
		assertFalse(alien_0_0.isDucking());
		facade.advanceTime(alien_0_0, 0.001);
		
		assertEquals(0.9, facade.getAcceleration(alien_0_0)[0], Util.DEFAULT_EPSILON);
	}
	
	/**
	 * Checks if Mazub's maximal horizontal velocity is correct when he was moving right at maximum velocity
	 * whilst ducking and then stops ducking while still moving right.
	 */
	@Test
	public void correctSpeedStopDuckingWhileMoving() {
		facade.startDuck(alien_0_0);
		assertTrue(alien_0_0.isDucking());
		
		facade.startMoveRight(alien_0_0);
		facade.advanceTime(alien_0_0, 0.1);
		
		// Horizontal velocity of Mazub is now equal to the maximal velocity while ducking, which is 1 m/s.
		
		facade.endDuck(alien_0_0);
		assertFalse(alien_0_0.isDucking());
		facade.advanceTime(alien_0_0, 0.05);
		
		// velocity_x_new [m/s] = 1 [m/s] + 0.9 [m/s^2] * 0.05[s] = 1.045 [m/s]
		
		assertEquals(1.045, facade.getVelocity(alien_0_0)[0], Util.DEFAULT_EPSILON);
	}
	
	/********************************************* TIMERS ****************************************/
	
	/**
	 * Checks if the time since the last move by Mazub was made, is tracked correctly.
	 */
	@Test
	public void correctTimeSinceLastMove() {
		facade.startMoveLeft(alien_0_0);
		facade.advanceTime(alien_0_0, 0.15);
		facade.endMoveLeft(alien_0_0);
		for (int i = 0; i < 4; i++){
			facade.advanceTime(alien_0_0, 0.10);
		}
		
		assertEquals(0.4, alien_0_0.getTimer().getSinceLastMove(), Util.DEFAULT_EPSILON);
	}
	

	/**
	 * Checks if the time since the last sprite by Mazub was selected, is tracked correctly.
	 */
	@Test
	public void correctTimeSinceLastSprite(){
		
		facade.advanceTime(alien_0_0, 0.020);
		
		assertEquals(0.020, alien_0_0.getTimer().getSinceLastSprite(), Util.DEFAULT_EPSILON);
		
		for(int i = 0; i < 10; i++){
			facade.advanceTime(alien_0_0, 0.10);
		}
		
		// total time passed: 0.020 + 10 * 0.010 = 0.120;
		// calculated time since last sprite: 0.120 - 0.075 = 0.045
		assertEquals(0.045, alien_0_0.getTimer().getSinceLastSprite(),  Util.DEFAULT_EPSILON);
	}
	
	/********************************************* SPRITES ****************************************/

	/**
	 * Checks ducking sprite.
	 */
	@Test
	public void spriteDucking(){		
		// The sprite index should be 0
		assertEquals(sprites[0], facade.getCurrentSprite(alien_0_0));
		
		facade.startDuck(alien_0_0);
		facade.advanceTime(alien_0_0, 0.010);
		
		// The sprite index should be 1
		assertEquals(sprites[1], facade.getCurrentSprite(alien_0_0));
		
		facade.endDuck(alien_0_0);
		facade.advanceTime(alien_0_0, 0.010);

		// The sprite index should be 0
		assertEquals(sprites[0], facade.getCurrentSprite(alien_0_0));
	}
	
	/**
	 * Checks the sprite:
	 * Mazub is not moving horizontally but its last horizontal movement was to the right
	 * 						(within 1s), and the character is not ducking. (index 2)
	 */
	@Test
	public void spriteHasMovedRightAndNotDucking(){
		
		facade.startMoveRight(alien_0_0);
		facade.advanceTime(alien_0_0, 0.10);
		
		facade.endMoveRight(alien_0_0);
		for(int i = 0; i < 10 ; i ++){
			facade.advanceTime(alien_0_0, 0.10);
		}
		
		// Total time passed since last move right is one second.
		// Therefore, the sprite index should be 2.
		assertEquals(sprites[2], facade.getCurrentSprite(alien_0_0));
	}
	
	/**
	 * Checks if the sprite index is set back to 0 (which represents the idle sprite)
	 * just after the one second limit.
	 */
	@Test
	public void spriteIdleAfterRunning(){
		
		facade.startMoveRight(alien_0_0);
		facade.advanceTime(alien_0_0, 0.10);
		
		facade.endMoveRight(alien_0_0);
		for(int i = 0; i < 10 ; i ++){
			facade.advanceTime(alien_0_0, 0.10);
		}
		
		facade.advanceTime(alien_0_0, 0.001);
		
		// Total time passed since last move right is one second.
		// Therefore, the sprite index should be 2.
		assertEquals(sprites[0], facade.getCurrentSprite(alien_0_0));
	}
	
	/**
	 * Checks sprite for jumping and moving right.
	 */
	@Test
	public void spriteJumpingMovingRight(){
		facade.startMoveRight(alien_0_0);
		facade.startJump(alien_0_0);
		facade.advanceTime(alien_0_0, 0.10);
		
		// The sprite index should be 4.
		assertEquals(sprites[4], facade.getCurrentSprite(alien_0_0));
	}
	
	/**
	 * Checks sprite for ducking and moving right.
	 */
	@Test
	public void spriteDuckingMovingRight(){
		facade.startMoveRight(alien_0_0);
		facade.startDuck(alien_0_0);
		facade.advanceTime(alien_0_0, 0.10);
		
		// The sprite index should be 6.
		assertEquals(sprites[6], facade.getCurrentSprite(alien_0_0));
	}
	
	
	/********************************************* ADVANCE TIME ****************************************/	
	/**
	 * Checks if advanceTime cannot be used with a negative time step.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void advanceTime_SmallerThanZeroCase(){
		alien_0_0.advanceTime(-0.001);
	}
	
	/**
	 * Checks if advanceTime cannot be used with a time step greater than 0.2.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void advanceTime_GreaterThanCase(){
		alien_0_0.advanceTime(0.201);
	}
	
	
	/********************************************* GIVEN TESTS ****************************************/	
	@Test
	public void startMoveRightCorrect() {
		IFacade facade = new Facade();

		Mazub alien = facade.createMazub(0, 0, spriteArrayForSize(2, 2));
		facade.startMoveRight(alien);
		facade.advanceTime(alien, 0.1);

		// x_new [m] = 0 + 1 [m/s] * 0.1 [s] + 1/2 0.9 [m/s^2] * (0.1 [s])^2 =
		// 0.1045 [m] = 10.45 [cm], which falls into pixel (10, 0)

		assertArrayEquals(intArray(10, 0), facade.getLocation(alien));
	}

	@Test
	public void startMoveRightMaxSpeedAtRightTime() {
		IFacade facade = new Facade();

		Mazub alien = facade.createMazub(0, 0, spriteArrayForSize(2, 2));
		facade.startMoveRight(alien);
		// maximum speed reached after 20/9 seconds
		for (int i = 0; i < 100; i++) {
			facade.advanceTime(alien, 0.2 / 9);
		}

		assertArrayEquals(doubleArray(3, 0), facade.getVelocity(alien),
				Util.DEFAULT_EPSILON);
	}

	@Test
	public void testAccellerationZeroWhenNotMoving() {
		IFacade facade = new Facade();

		Mazub alien = facade.createMazub(0, 0, spriteArrayForSize(2, 2));
		assertArrayEquals(doubleArray(0.0, 0.0), facade.getAcceleration(alien),
				Util.DEFAULT_EPSILON);
	}

	@Test
	public void testWalkAnimationLastFrame() {
		IFacade facade = new Facade();

		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(0, 0, sprites);

		facade.startMoveRight(alien);

		facade.advanceTime(alien, 0.005);
		for (int i = 0; i < m; i++) {
			facade.advanceTime(alien, 0.075);
		}

		assertEquals(sprites[8+m], facade.getCurrentSprite(alien));
	}	
}

