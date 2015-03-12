package jumpingalien.model;

import static jumpingalien.tests.util.TestUtils.intArray;
import static jumpingalien.tests.util.TestUtils.doubleArray;
import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import static org.junit.Assert.*;
import jumpingalien.common.gui.AlienGameScreen;
import jumpingalien.part1.facade.IFacade;
import jumpingalien.util.Util;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/*
 * Tests:
 * - Initializeren met correcte positie
 * - Initializeren met incorrecte positie(s)
 * - Verticale positie update (springen)
 * - width / height? Hoe?
 * - isMoving?
 * - acceleration?
 * - sprites?
 * 
 * 
 * benaming pff.
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
	}

	@After
	public void tearDown() throws Exception {
	}
	
	
	/**
	 * Checks if Mazub is initialized with the right x position.
	 */
	@Test
	public void constructorPositionX_LegalCase(){
		IFacade facade = new Facade();
		
		Mazub alien = facade.createMazub(10, 5, spriteArrayForSize(2, 2) );
		assertEquals(alien.getRoundedPositionX(), 10);
	}
	
	/**
	 * Checks if Mazub is initialized with the right y position.
	 */
	@Test
	public void constructorPositionY_LegalCase(){
		IFacade facade = new Facade();
		
		Mazub alien = facade.createMazub(10, 5, spriteArrayForSize(2, 2) );
		assertEquals(alien.getRoundedPositionY(), 5);
	}
	
	/**
	 * Checks if Mazub cannot be initialized with an out of bound x position.
	 */
	@Test(expected=IllegalPositionXException.class)
	public void constructorPositionX_IllegalCase() throws Exception{
		new Mazub(-5, 0, spriteArrayForSize(2, 2) );
	}
	
	/**
	 * Checks if Mazub cannot be initialized with an out of bound y position.
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
		IFacade facade = new Facade();
		Mazub alien = facade.createMazub(0, 0, spriteArrayForSize(2, 2) );
		assertEquals(1.0, alien.getVelocityXInit(),0.00001);
	}
	
	/**
	 * Checks if Mazub's default maximal horizontal velocity is equal to 3.0 .
	 */
	@Test
	public void correctDefaultVelocityXMax() {
		IFacade facade = new Facade();
		Mazub alien = facade.createMazub(0, 0, spriteArrayForSize(2, 2) );
		assertEquals(3.0, alien.getVelocityXMax(),0.00001);
	}
	
	/**
	 * Checks if the helper classes (Time and Animation) are properly initiated.
	 */
	@Test
	public void helperClassesCorrectlyInitiated(){
		IFacade facade = new Facade();
		Mazub alien = facade.createMazub(10, 5, spriteArrayForSize(2, 2) );
		
		assertNotNull(alien.getTime());
		assertNotNull(alien.getAnimation());
	}
	
	/**
	 * Checks if the positions of Mazub are correctly rounded down to the right integer.
	 */
	@Test
	public void positionsCorrectlyRounded(){
		IFacade facade = new Facade();
		Mazub alien = facade.createMazub(0, 0, spriteArrayForSize(2, 2) );
		facade.startMoveRight(alien);
		facade.startJump(alien);
		facade.advanceTime(alien, 0.14);
		
		// x_new [m] = 0 [m] + 1 [m/s] * 0.14 [s] + 1/2 * 0.9 [m/s^2] * (0.14 [s]) ^2 =
		// 0.14882 [m] = 14.882 [cm] 
		// y_new [m] = 0 [m] + 8 [m/s] * 0.14 [s] + 1/2 * (-10.0) [m/s^2] * (0.14 [s])^2 =
		// 1.022 [m] = 102.20 [cm]
		// The position of Mazub should be (14, 102) because the doubles are always rounded down.

		assertEquals(14, alien.getRoundedPositionX());
		assertEquals(102, alien.getRoundedPositionY());
	}
	
	/**
	 * Checks if isValidRoundedPosition() correctly determines which positions are valid and which
	 * are not.
	 */
	@Test
	public void correctValidRoundedPositions(){
		assertFalse(Mazub.isValidRoundedPositionX(-1));
		assertTrue(Mazub.isValidRoundedPositionX(0));
		assertTrue(Mazub.isValidRoundedPositionX(546));
		assertFalse(Mazub.isValidRoundedPositionX(1024));
		assertTrue(Mazub.isValidRoundedPositionX(1023));
		assertFalse(Mazub.isValidRoundedPositionY(-1));
		assertTrue(Mazub.isValidRoundedPositionY(0));
		assertTrue(Mazub.isValidRoundedPositionY(546));
		assertFalse(Mazub.isValidRoundedPositionY(768));
		assertTrue(Mazub.isValidRoundedPositionY(767));
	}
	
	/**
	 * Checks if isValidPosition() correctly determines which positions are valid and which are not.
	 */
	@Test
	public void correctValidPositions(){
		assertFalse(Mazub.isValidPositionX(-0.00001));
		assertTrue(Mazub.isValidPositionX(0.00001));
		assertTrue(Mazub.isValidPositionX(546.72));
		assertFalse(Mazub.isValidPositionX(1024.00001));
		assertTrue(Mazub.isValidPositionX(1023.99999));
		assertFalse(Mazub.isValidPositionY(-0.00001));
		assertTrue(Mazub.isValidPositionY(0.00001));
		assertTrue(Mazub.isValidPositionY(546.72));
		assertFalse(Mazub.isValidPositionY(768.00001));
		assertTrue(Mazub.isValidPositionY(767.99999));
	}
	
	/**
	 * Checks if isValidVelocityX() correctly determines which velocities are valid and which are not.
	 */
	@Test
	public void correctValidVelocityX(){
		IFacade facade = new Facade();
		Mazub alien = facade.createMazub(0, 0, spriteArrayForSize(2, 2));
		
		assertFalse(alien.isValidVelocityX(0.01));
		assertFalse(alien.isValidVelocityX(-0.99));
		assertTrue(alien.isValidVelocityX(1.0));
		assertTrue(alien.isValidVelocityX(-1.75));
		assertTrue(alien.isValidVelocityX(3.0));
		assertFalse(alien.isValidVelocityX(3.00001));
		assertFalse(alien.isValidVelocityX(54.54));
	}
	
	/**
	 * Checks the y-position when jumping.
	 */
	@Test
	public void jumpCorrectly() {
		IFacade facade = new Facade();
		Mazub alien = facade.createMazub(0, 0, spriteArrayForSize(2, 2));
		
		facade.startJump(alien);
		facade.advanceTime(alien, 0.1);

		// y_new [m] = 0 [m] + 8 [m/s] * 0.1 [s] + 1/2 * (-10.0) [m/s^2] * (0.1 [s])^2 =
		// 0.75 [m] = 75.00 [cm], which falls into pixel (0, 75)

		assertArrayEquals(intArray(0, 75), facade.getLocation(alien));
	}
	
	/**
	 * Checks endJump method.
	 */
	@Test
	public void endJumpCorrectly() {
		IFacade facade = new Facade();
		Mazub alien = facade.createMazub(0, 0, spriteArrayForSize(2, 2));
		
		facade.startJump(alien);

		facade.advanceTime(alien, 0.1);
		facade.endJump(alien);
		
		assertTrue( Util.fuzzyEquals(0, facade.getVelocity(alien)[1]) );
	}
	
	/**
	 * Checks if Mazub is back on the ground on the expected moment, after invoking endJump.
	 */
	@Test
	public void backOnGroundCorrectly() {
		IFacade facade = new Facade();
		Mazub alien = facade.createMazub(0, 0, spriteArrayForSize(2, 2));
		
		facade.startJump(alien);

		facade.advanceTime(alien, 0.025);
		facade.endJump(alien);
		
		// Height after 0.025 seconds:
		// y_new [m] = 0 + 8 [m/s] * 0.025 [s] + 1/2 * (-10.0) [m/s^2] * (0.025 [s])^2 =
		// 0.1968750 [m] = 19.68750 [cm], which falls into pixel (0, 20)

		// Time step until Mazub has reached the ground:
		// 0.1968750 [m] + 0 [m/s] * dt [s] + 1/2 * (-10.0) [m/s^2] * (dt [s])^2 = 0
		// Solving the equation for a positive solution gives: dt = 0.1984313483
		
		facade.advanceTime(alien, 0.199);
		assertArrayEquals(intArray(0, 0), facade.getLocation(alien));
	}
	
	/**
	 * Checks if Mazub's maximal horizontal velocity while ducking is correct.
	 */
	@Test
	public void maxSpeedDuckingCorrectly() {
		IFacade facade = new Facade();
		Mazub alien = facade.createMazub(0, 0, spriteArrayForSize(2, 2));
		
		facade.startDuck(alien);
		facade.startMoveRight(alien);
		facade.advanceTime(alien, 0.1);	
		
		// Horizontal velocity after a time step of 0.1 seconds.
		// velocity_x_new [m/s] = 1.0 [m/s] + 0.9 [m/s^2] * 0.1 [s] = 1.09 [m/s]
		// However the velocity should be limited to 1 m/s whilst ducking, so the new velocity is equal to
		// 1 m/s.

		// assertArrayEquals(doubleArray(1, 0), facade.getVelocity(alien));
		// 		-> assertArrayEquals werkt niet om één of andere reden met doubles.
		assertEquals(1, facade.getVelocity(alien)[0],0.00001);
	}
	
	/**
	 * Checks if Mazub's maximal horizontal velocity is correct when he was moving right at maximum velocity
	 * whilst ducking and then stops ducking while still moving right.
	 */
	@Test
	public void correctSpeedStopDuckingWhileMoving() {
		IFacade facade = new Facade();
		Mazub alien = facade.createMazub(0, 0, spriteArrayForSize(2, 2));
		
		facade.startDuck(alien);
		facade.startMoveRight(alien);
		facade.advanceTime(alien, 0.1);
		
		// Horizontal velocity of Mazub is now equal to the maximal velocity while ducking, which is 1 m/s.
		
		facade.endDuck(alien);
		facade.advanceTime(alien, 0.05);
		
		// velocity_x_new [m/s] = 1 [m/s] + 0.9 [m/s^2] * 0.05[s] = 1.045 [m/s]
		
		assertEquals(1.045, facade.getVelocity(alien)[0],0.00001);
	}
	
	/**
	 * Checks if Mazub's vertical velocity is equal to zero at a certain moment during his jump. Also checks if
	 * Mazub is still jumping at that moment.
	 */
	@Test
	public void midAirVelocityZero(){
		IFacade facade = new Facade();
		Mazub alien = facade.createMazub(0, 0, spriteArrayForSize(2, 2));
		
		facade.startJump(alien);
			
		// velocity_y_new [m/s] = 8 [m/s] - 10 [m/s^2] * dt [s] = 0 [m/s]
		// solving for dt we find: dt = 0.8
		
		for (int i = 0; i < 10; i++){
			facade.advanceTime(alien, 0.08);
		}
		
		assertEquals(0, facade.getVelocity(alien)[0],0.00001);	
		assert alien.isJumping();
	}
	
	/**
	 * Checks if the time since the last move by Mazub was made, is tracked correctly.
	 */
	@Test
	public void correctTimeSinceLastMove() {
		IFacade facade = new Facade();
		Mazub alien = facade.createMazub(0, 0, spriteArrayForSize(2, 2));
		
		facade.startMoveLeft(alien);
		facade.advanceTime(alien, 0.15);
		facade.endMoveLeft(alien);
		for (int i = 0; i < 4; i++){
			facade.advanceTime(alien, 0.10);
		}
		
		assertEquals(0.4, alien.getTime().getSinceLastMove(), 0.00001);
	}
	
	}

