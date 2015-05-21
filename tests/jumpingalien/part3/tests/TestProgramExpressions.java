/**
 * Test file containing tests of expressions executed by a program.
 */

package jumpingalien.part3.tests;

import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import static org.junit.Assert.*;

import java.util.HashMap;

import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.World;
import jumpingalien.model.program.Program;
import jumpingalien.model.program.ProgramFactory;
import jumpingalien.model.program.expressions.BinaryOperator;
import jumpingalien.model.program.expressions.Constant;
import jumpingalien.model.program.expressions.Expression;
import jumpingalien.model.program.expressions.UnaryOperator;
import jumpingalien.model.program.statements.Statement;
import jumpingalien.model.program.statements.Wait;
import jumpingalien.model.program.types.BooleanType;
import jumpingalien.model.program.types.DoubleType;
import jumpingalien.model.program.types.Type;
import jumpingalien.model.terrain.Terrain;
import jumpingalien.part3.facade.Facade;
import jumpingalien.part3.programs.ParseOutcome;
import jumpingalien.util.Sprite;
import jumpingalien.util.Util;

import org.antlr.v4.runtime.atn.EpsilonTransition;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestProgramExpressions {
	
	/* Setup */
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	
	ProgramFactory<Expression<Type>, Statement, Type, Program> programFactory;
	Program program;
	Facade facade;
	Sprite[] sprites;
	Sprite[] plantSprites;
	Mazub alien;
	World world;
	String test;
	
	@Before
	public void setUp() throws Exception {
		programFactory = new ProgramFactory<>();
		facade = new Facade();

		sprites = spriteArrayForSize(5, 4);
		plantSprites = spriteArrayForSize(3, 2, 2);
		
		alien = facade.createMazub(250, 50, sprites);
		world = facade.createWorld(50, 60, 15, 200, 150, 4, 1);
		
		facade.setMazub(world, alien);
		
		for(int i = 0; i < 60; i++){
			facade.setGeologicalFeature(world, i, 0, Terrain.SOLID.getId()); // create solid terrain
		}
	}

	@After
	public void tearDown() throws Exception {
		
	}
	
	/* Operator tests */
	
	@Test 
	public void testAddition(){
		String test = 
		"double a; "
		+ "a := 3.0 + 5.5;";		
		
		ParseOutcome<?> parseOutcome = facade.parse(test);
		
		Program program = (Program) parseOutcome.getResult();
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		program = (Program) parseOutcome.getResult();
		program.executeNext();
		assertEquals(8.5, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
	}
	
	public void testAdditionParseFail(){
		String test = 
		"double a; "
		+ "a := 3.0 + ;";		
		
		ParseOutcome<?> parseOutcome = facade.parse(test);
		
		assertFalse(parseOutcome.isSuccess());
	}

	@Test 
	public void testSubstraction(){
		String test = 
		"double a; "
		+ "a := 3.0 - 5.5;";		
		
		ParseOutcome<?> parseOutcome = facade.parse(test);
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		
		program = (Program) parseOutcome.getResult();
		program.executeNext();
		assertEquals(-2.5, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
	}
	
	@Test 
	public void testMultiplication(){
		String test = 
		"double a; "
		+ "a := 3.0 * 5.5;";		
		
		ParseOutcome<?> parseOutcome = facade.parse(test);
		
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		
		program = (Program) parseOutcome.getResult();
		program.executeNext();
		assertEquals(16.5, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
	}
	
	@Test 
	public void testDivision(){
		String test = 
		"double a; "
		+ "a := 7.0 / 3.5;";		
		
		ParseOutcome<?> parseOutcome = facade.parse(test);
		
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		
		program = (Program) parseOutcome.getResult();
		program.executeNext();
		assertEquals(2.0, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
	}
	
	@Test 
	public void testSqrt(){
		String test = 
		"double a; "
		+ "a := sqrt(9.0);";		
		
		ParseOutcome<?> parseOutcome = facade.parse(test);
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		
		program = (Program) parseOutcome.getResult();
		program.executeNext();
		assertEquals(3.0, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
	}
	
	@Test 
	public void testRandom(){
		for(int i = 0; i<20; i++){
			String test = 
			"double a; "
			+ "a := random(4.95);";		
			
			ParseOutcome<?> parseOutcome = facade.parse(test);
			
			if(!parseOutcome.isSuccess()){
				throw new IllegalArgumentException("Program parsing failed");
			}
			
			Program program = (Program) parseOutcome.getResult();

			
			program = (Program) parseOutcome.getResult();
			program.executeNext();
			assertTrue(((DoubleType) program.getVariable("a")).getValue() >= 0);
			assertTrue(((DoubleType) program.getVariable("a")).getValue() < 4.95);
		}		
	}
	
	/* Bool tests */
	
	@Test 
	public void testAndFalse(){
		String test = 
		"double a; "
		+ "a := true && false;";		
		
		ParseOutcome<?> parseOutcome = facade.parse(test);
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();

		program = (Program) parseOutcome.getResult();
		program.executeNext();
		assertFalse(((BooleanType) program.getVariable("a")).getValue());
	}
	
	@Test 
	public void testOrFalse(){
		String test = 
		"double a; "
		+ "a := false || false;";		
		
		ParseOutcome<?> parseOutcome = facade.parse(test);
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		
		program = (Program) parseOutcome.getResult();
		program.executeNext();
		assertFalse(((BooleanType) program.getVariable("a")).getValue());
	}
		
	@Test 
	public void testOrTrue(){
		String test = 
		"double a; "
		+ "a := true || false;";		
		
		ParseOutcome<?> parseOutcome = facade.parse(test);
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		
		program = (Program) parseOutcome.getResult();
		program.executeNext();
		assertTrue(((BooleanType) program.getVariable("a")).getValue());
	}
	
	@Test 
	public void testOrParseFail(){
		String test = 
		"double a; "
		+ "a := true || ;";		
		
		ParseOutcome<?> parseOutcome = facade.parse(test);
		
		assertFalse(parseOutcome.isSuccess());
	}
	
	@Test 
	public void testNot(){
		String test = 
		"double a; "
		+ "a := ! true;";		
		
		ParseOutcome<?> parseOutcome = facade.parse(test);
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		
		program = (Program) parseOutcome.getResult();
		program.executeNext();
		assertFalse(((BooleanType) program.getVariable("a")).getValue());
	}
	
	@Test 
	public void testLessThan(){
		String test = 
		"double a; "
		+ "a := 0.5 < 0.7;";		
		
		ParseOutcome<?> parseOutcome = facade.parse(test);
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		
		program = (Program) parseOutcome.getResult();
		program.executeNext();
		assertTrue(((BooleanType) program.getVariable("a")).getValue());
	}
	
	@Test 
	public void testLessThanFalse(){
		String test = 
		"double a; "
		+ "a := 0.5 > 0.7;";		
		
		ParseOutcome<?> parseOutcome = facade.parse(test);
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		
		program = (Program) parseOutcome.getResult();
		program.executeNext();
		assertFalse(((BooleanType) program.getVariable("a")).getValue());
	}
	
	@Test 
	public void testLessThanFalseBecauseEqual(){
		String test = 
		"double a; "
		+ "a := 0.7 < 0.7;";		
		
		ParseOutcome<?> parseOutcome = facade.parse(test);
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		
		program = (Program) parseOutcome.getResult();
		program.executeNext();
		assertFalse(((BooleanType) program.getVariable("a")).getValue());
	}
	
	@Test 
	public void testGreaterThan(){
		String test = 
		"double a; "
		+ "a := 0.7 > 0.5;";		
		
		ParseOutcome<?> parseOutcome = facade.parse(test);
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		
		program = (Program) parseOutcome.getResult();
		program.executeNext();
		assertTrue(((BooleanType) program.getVariable("a")).getValue());
	}
	
	@Test 
	public void testEqualsTrue(){
		String test = 
		"double a; "
		+ "a := 0.7 == 0.7;";		
		
		ParseOutcome<?> parseOutcome = facade.parse(test);
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		
		program = (Program) parseOutcome.getResult();
		program.executeNext();
		assertTrue(((BooleanType) program.getVariable("a")).getValue());
	}
	
	@Test 
	public void testEqualsFalse(){
		String test = 
		"double a; "
		+ "a := 0.5 == 0.7;";		
		
		ParseOutcome<?> parseOutcome = facade.parse(test);
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		
		program = (Program) parseOutcome.getResult();
		program.executeNext();
		assertFalse(((BooleanType) program.getVariable("a")).getValue());
	}
	
	/* Getters */
	
	@Test 
	public void testGetX(){
		test = "double a;"
				+"a := getx self;";

		
		ParseOutcome<?> parseOutcome = facade.parse(test);
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		Plant plant = facade.createPlantWithProgram(180, 50, plantSprites, program);
		facade.addPlant(world, plant);
		world.advanceTime(0.001);
		assertEquals(180.0, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
	}
	@Test 
	public void testGetY(){
		String test = "double a;"
				+"a := gety self;";
		
		ParseOutcome<?> parseOutcome = facade.parse(test);
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		Plant plant = facade.createPlantWithProgram(180, 50, plantSprites, program);
		facade.addPlant(world, plant);
		world.advanceTime(0.001);
		assertEquals(50.0, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
	}
	
	@Test 
	public void testWidth(){
		String test = "double a;"
				+"a := getwidth self;";
		
		ParseOutcome<?> parseOutcome = facade.parse(test);
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		Plant plant = facade.createPlantWithProgram(180, 50, plantSprites, program);
		facade.addPlant(world, plant);
		world.advanceTime(0.001);
		assertEquals(3.0, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
	}
	
	@Test 
	public void testHeight(){
		String test = "double a;"
				+"a := getheight self;";
		
		ParseOutcome<?> parseOutcome = facade.parse(test);
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		Plant plant = facade.createPlantWithProgram(180, 50, plantSprites, program);
		facade.addPlant(world, plant);
		world.advanceTime(0.001);
		assertEquals(2.0, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
	}
	
	@Test 
	public void testHitpoints(){
		String test = "double a;"
				+"a := gethp self;";
		
		ParseOutcome<?> parseOutcome = facade.parse(test);
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		Plant plant = facade.createPlantWithProgram(180, 50, plantSprites, program);
		facade.addPlant(world, plant);
		world.advanceTime(0.001);
		assertEquals(1.0, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
	}		
	
}
