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
import jumpingalien.model.terrain.Terrain;
import jumpingalien.part3.facade.Facade;
import jumpingalien.part3.programs.ParseOutcome;
import jumpingalien.program.Program;
import jumpingalien.program.ProgramFactory;
import jumpingalien.program.expressions.BinaryOperator;
import jumpingalien.program.expressions.Constant;
import jumpingalien.program.expressions.Expression;
import jumpingalien.program.expressions.UnaryOperator;
import jumpingalien.program.statements.Statement;
import jumpingalien.program.statements.Wait;
import jumpingalien.program.types.BooleanType;
import jumpingalien.program.types.DoubleType;
import jumpingalien.program.types.Type;
import jumpingalien.util.Sprite;
import jumpingalien.util.Util;

import org.antlr.v4.runtime.atn.EpsilonTransition;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestProgramStatements {
	
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
	
	/* Statement tests */
	
	/* Foreach */
	
	@Test 
	public void testForeach(){
		String test = 
				"double x; double a; a:= 0;"
				+"foreach(plant, x) do a := a + 1 ; done ";
		
		ParseOutcome<?> parseOutcome = facade.parse(test);
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		Plant plant = facade.createPlantWithProgram(180, 50, plantSprites, program);
		facade.addPlant(world, plant);
		Plant plant2 = facade.createPlant(200, 50, plantSprites);
		facade.addPlant(world, plant2);
		world.advanceTime(0.001);
		assertEquals(0, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.001); // Evaluate foreach
		assertEquals(0, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.001); // First loop iteration
		assertEquals(1, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.001); // Second loop iteration
		assertEquals(2, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.001); // For loop is ended now, starting again at assignment a := 0
		assertEquals(0, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.001); // Evaluate foreach
		assertEquals(0, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.001);
		assertEquals(1, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.001);
		assertEquals(2, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
	}	
	
	@Test 
	public void testForeachBreak(){
		String test = 
				"double x; double a; a:= 0;"
				+"foreach(plant, x) do "
				+ " if a == 1 then "
				+ " break; "
				+ " fi "
				+ " a := a + 1 ;"
				+ " done ";
		
		ParseOutcome<?> parseOutcome = facade.parse(test);
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		Plant plant = facade.createPlantWithProgram(180, 50, plantSprites, program);
		facade.addPlant(world, plant);
		Plant plant2 = facade.createPlant(200, 50, plantSprites);
		facade.addPlant(world, plant2);
		world.advanceTime(0.001);
		assertEquals(0, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.001); // Evaluate foreach
		assertEquals(0, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.001); // Evaluate if
		assertEquals(0, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.001); // If not true, so assignment a := 1
		assertEquals(1, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.001); // Second iteration, evaluating if
		assertEquals(1, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.001); // If is true so break
		assertEquals(1, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.001); // restart program at assigment a := 0
		assertEquals(0, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.001); // Evaluate foreach
		assertEquals(0, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.001); // Evaluate if
		assertEquals(0, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.001); // If not true, so assignment a := 1
		assertEquals(1, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.001); // Second iteration, evaluating if
		assertEquals(1, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.001); // If is true so break
		assertEquals(1, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		
	}	
	
	/* While */
	
	@Test 
	public void testWhile(){
		String test = 
				" double a; a:= 0;"
				+ "while a < 3 do "
				+ " a := a + 1 ;"
				+ " done "
				+ " a := 85;";
		
		ParseOutcome<?> parseOutcome = facade.parse(test);
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		Plant plant = facade.createPlantWithProgram(180, 50, plantSprites, program);
		facade.addPlant(world, plant);
		Plant plant2 = facade.createPlant(200, 50, plantSprites);
		facade.addPlant(world, plant2);
		world.advanceTime(0.001); // Initial a assignment
		assertEquals(0, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.001); // Evaluate while
		assertEquals(0, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.001); // Assignment of a
		assertEquals(1, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.001); // Evaluate while
		assertEquals(1, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.001); // Assignment of a
		assertEquals(2, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.001); // Evaluate while
		assertEquals(2, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.001); // Assignment of a
		assertEquals(3, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.001); // Evaluate while (false now)
		assertEquals(3, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.001); // Assignment of a := 85
		assertEquals(85, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.001); // Restart program
		assertEquals(0, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		
	}	
	
	@Test 
	public void testWhileBreak(){
		String test = 
				" double a; a:= 0;"
				+"while a < 3 do "
				+ " if a == 2 then "
				+ " break; "
				+ " fi "
				+ " a := a + 1 ;"
				+ " done "
				+ " a := 85;";
		
		ParseOutcome<?> parseOutcome = facade.parse(test);
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		Plant plant = facade.createPlantWithProgram(180, 50, plantSprites, program);
		facade.addPlant(world, plant);
		Plant plant2 = facade.createPlant(200, 50, plantSprites);
		facade.addPlant(world, plant2);
		world.advanceTime(0.001); // Initial a assignment
		assertEquals(0, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.001); // Evaluate while
		assertEquals(0, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.001); // Evaluate if (false)
		assertEquals(0, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.001); // Assignment of a
		assertEquals(1, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.001); // Evaluate while
		assertEquals(1, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.001); // Evaluate if (false)
		assertEquals(1, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.001); // Assignment of a
		assertEquals(2, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.001); // Evaluate while
		assertEquals(2, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.001); // Evaluate if (true)
		assertEquals(2, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.001); // Evaluate break
		assertEquals(2, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.001); // Assignment of a := 85
		assertEquals(85, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.001); // Restart program
		assertEquals(0, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		
	}	
	
	
}
