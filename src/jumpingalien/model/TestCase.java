package jumpingalien.model;

import static jumpingalien.tests.util.TestUtils.intArray;
import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import static org.junit.Assert.*;
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
	 * Checks the y-position when jumping.
	 */
	@Test
	public void jumpCorrectly() {
		IFacade facade = new Facade();

		Mazub alien = facade.createMazub(0, 0, spriteArrayForSize(2, 2));
		facade.startJump(alien);
		facade.advanceTime(alien, 0.1);

		// y_new [m] = 0 + 8 [m/s] * 0.1 [s] + 1/2 * (-10.0) [m/s^2] * (0.1 [s])^2 =
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
	 * Checks if Mazub's maximal horizontal speed while ducking is correct.
	 */
	@Test
	public void maxSpeedDuckingCorrectly() {
		IFacade facade = new Facade();
		
		Mazub alien = facade.createMazub(0, 0, spriteArrayForSize(2, 2));
		facade.startDuck;
		facade.startMoveRight(alien);
		for (int i = 0; i < 12; i++) {
			facade.advanceTime(alien, 0.1);
		}
		
		// Horizontal velocity after 12 time steps of 0.1 seconds.
		// velocity_x_new [m/s] = 0 [m/s] + 12 * 0.9 [m/s^2] * 0.1 [s] = 1.08 [m/s]
		// The new velocity should be limited to 1 [m/s] whilst ducking.

		assertArrayEquals(intArray(1, 0), facade.getVelocity(alien));
	}
	
}
