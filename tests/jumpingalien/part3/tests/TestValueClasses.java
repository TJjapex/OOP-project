/**
 * Test file containing given tests
 */

package jumpingalien.part3.tests;

import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import static org.junit.Assert.*;
import static org.junit.Assume.*;
import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.helper.Orientation;
import jumpingalien.part3.facade.Facade;
import jumpingalien.part3.facade.IFacadePart3;
import jumpingalien.part3.programs.IProgramFactory.Direction;
import jumpingalien.part3.programs.ParseOutcome;
import jumpingalien.program.Program;
import jumpingalien.program.types.BooleanType;
import jumpingalien.program.types.DirectionType;
import jumpingalien.program.types.DoubleType;
import jumpingalien.program.types.ObjectType;
import jumpingalien.util.Util;

import org.junit.Test;

public class TestValueClasses {

	/* Boolean */
	@Test
	public void testBoolean() {
		BooleanType bool = new BooleanType(true);
		assertTrue(bool.getValue());
		BooleanType bool2 = new BooleanType(false);
		assertFalse(bool2.getValue());
	}
	
	@Test
	public void testBooleanEqualsTrue() {
		BooleanType bool = new BooleanType(true);
		BooleanType bool2 = new BooleanType(true);
		assertTrue(bool.equals(bool2));
	}
	
	@Test
	public void testBooleanEqualsFalse() {
		BooleanType bool = new BooleanType(true);
		BooleanType bool2 = new BooleanType(false);
		assertFalse(bool.equals(bool2));
	}
	
	@Test
	public void testBooleanEqualsStringFalse() {
		BooleanType bool = new BooleanType(true);
		assertFalse(bool.equals("test"));
	}
	
	@Test
	public void testBooleanEqualsNullFalse() {
		BooleanType bool = new BooleanType(true);
		assertFalse(bool.equals(null));
	}
	
	/* DoubleType */
	@Test
	public void testDouble() {
		DoubleType val = new DoubleType(0.7);
		assertEquals(0.7, val.getValue(), Util.DEFAULT_EPSILON);
		DoubleType val2 = new DoubleType(0.9);
		assertNotEquals(0.7, val2.getValue(), Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void testDoubleEqualsTrue() {
		DoubleType val = new DoubleType(0.7);
		DoubleType val2 = new DoubleType(0.7);
		assertTrue(val.equals(val2));
	}
	
	@Test
	public void testDoubleEqualsFalse() {
		DoubleType val = new DoubleType(0.7);
		DoubleType val2 = new DoubleType(0.9);
		assertFalse(val.equals(val2));
	}
	
	@Test
	public void testDoubleEqualsStringFalse() {
		DoubleType val = new DoubleType(0.7);
		assertFalse(val.equals("test"));
	}
	
	/* DirectionType */
	@Test
	public void testDirection() {
		DirectionType val = new DirectionType(Orientation.RIGHT);
		assertEquals(Orientation.RIGHT, val.getValue());
		DirectionType val2 = new DirectionType(Orientation.LEFT);
		assertNotEquals(Orientation.RIGHT, val2.getValue());
		DirectionType val3 = new DirectionType(Orientation.TOP);
		assertEquals(Orientation.TOP, val3.getValue());
		DirectionType val4 = new DirectionType(Orientation.BOTTOM);
		assertEquals(Orientation.BOTTOM, val4.getValue());
		
		DirectionType val5 = new DirectionType(Direction.RIGHT);
		assertEquals(Orientation.RIGHT, val5.getValue());
		DirectionType val6 = new DirectionType(Direction.LEFT);
		assertEquals(Orientation.LEFT, val6.getValue());
		DirectionType val7 = new DirectionType(Direction.UP);
		assertEquals(Orientation.TOP, val7.getValue());
		DirectionType val8 = new DirectionType(Direction.DOWN);
		assertEquals(Orientation.BOTTOM, val8.getValue());
	}
	
	@Test
	public void testDirectionEqualsTrue() {
		DirectionType val = new DirectionType(Orientation.RIGHT);
		DirectionType val2 = new DirectionType(Orientation.RIGHT);
		assertTrue(val.equals(val2));
	}
	
	@Test
	public void testDirectionEqualsOrientationTrue() {
		DirectionType val = new DirectionType(Orientation.RIGHT);
		DirectionType val2 = new DirectionType(Direction.RIGHT);
		assertTrue(val.equals(val2));
	}
	
	@Test
	public void testDirectionEqualsFalse() {
		DirectionType val = new DirectionType(Orientation.RIGHT);
		DirectionType val2 = new DirectionType(Orientation.LEFT);
		assertFalse(val.equals(val2));
	}
	
	@Test
	public void testDirectionEqualsStringFalse() {
		DirectionType val = new DirectionType(Orientation.RIGHT);
		assertFalse(val.equals("right"));
	}
	
	/* ObjectType */
	@Test
	public void testObject() {
		Mazub alien = new Mazub(0, 0, spriteArrayForSize(5, 4));
		Plant plant = new Plant(0, 0, spriteArrayForSize(4, 3, 2));
		ObjectType object = new ObjectType(alien);
		assertEquals(alien, object.getValue());
		ObjectType object2 = new ObjectType(plant);
		assertNotEquals(alien, object2.getValue());
	}
	
	@Test
	public void testObjectEqualsTrue() {
		Mazub alien = new Mazub(0, 0, spriteArrayForSize(5, 4));
		ObjectType object = new ObjectType(alien);
		ObjectType object2 = new ObjectType(alien);
		assertTrue(object.equals(object2));
	}
	
	@Test
	public void testObjectEqualsFalse() {
		Mazub alien = new Mazub(0, 0, spriteArrayForSize(5, 4));
		Plant plant = new Plant(0, 0, spriteArrayForSize(4, 3, 2));
		ObjectType object = new ObjectType(alien);
		ObjectType object2 = new ObjectType(plant);
		assertFalse(object.equals(object2));
	}
	
	@Test
	public void testObjectEqualsStringFalse() {
		Mazub alien = new Mazub(0, 0, spriteArrayForSize(5, 4));
		Plant plant = new Plant(0, 0, spriteArrayForSize(4, 3, 2));
		ObjectType object = new ObjectType(alien);
		ObjectType object2 = new ObjectType(plant);
		assertFalse(object.equals("test"));
	}
}
