package jumpingalien.part3.tests;

import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import static org.junit.Assert.*;

import java.util.Optional;

import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.Shark;
import jumpingalien.model.World;
import jumpingalien.model.program.Program;
import jumpingalien.model.program.ProgramFactory;
import jumpingalien.model.program.statements.Statement;
import jumpingalien.model.program.types.*;
import jumpingalien.model.program.expressions.Expression;
import jumpingalien.model.program.expressions.Variable;
import jumpingalien.part3.facade.Facade;
import jumpingalien.part3.programs.IProgramFactory;
import jumpingalien.part3.programs.ParseOutcome;
import jumpingalien.part3.programs.ProgramParser;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.util.Sprite;
import jumpingalien.util.Util;

import org.junit.Test;

public class TestProgramFactory {

	@Test
	public void testAddition() {
//		ProgramFactory<Expression<?>, Statement, Type, Program> programFactory = new ProgramFactory<>();
//		Program 
//		BooleanType fak = new BooleanType(true);
//		
//		Variable<Double> left = new Variable<Double>(new DoubleType(), 1.5, new SourceLocation(0, 0));
//		assertEquals(1.5, left.execute(), Util.DEFAULT_EPSILON);
//		programFactory.createPrint(left, new SourceLocation(0, 0));
//		
//		Variable<Double> right = new Variable<Double>(2.2, new SourceLocation(0, 0));
//		assertEquals(2.2, right.execute(), Util.DEFAULT_EPSILON);
//		
//		Expression<Double> sumExpression = programFactory.createAddition(left, right, new SourceLocation(0, 0));
//		assertEquals(3.7, sumExpression.execute(), Util.DEFAULT_EPSILON);
		
		//ProgramFactory<Expression<?>, Statement, Type, Program> programFactory = new ProgramFactory<>();
		//Program program = programFactory.createProgram(mainStatement, globalVariables);
		
		IProgramFactory<Expression<?>, Statement, Type, Program> factory = new ProgramFactory<>();
		
		Facade facade = new Facade();

		facade = new Facade();
		Sprite[] sprites = spriteArrayForSize(3, 3);
		Sprite[] plantSprites = spriteArrayForSize(3, 3, 2);
		
		Mazub alien = facade.createMazub(100, 50, sprites);
		World world = facade.createWorld(50, 60, 15, 200, 150, 4, 1);
		
		for(int i = 0; i < 60; i++){
			facade.setGeologicalFeature(world, i, 0, 0); // create solid terrain
		}
		
		facade.setMazub(world, alien);
		
		
		ParseOutcome<?> parseOutcome = facade.parse(
				  "double a; "
				//+ "double b; "
				+ "while true do "
				+ "if (5 == 5) then "
				+ "a := 5;"
				+ "a := 6; "
				//+ "while true do "
				//+ "b := 4; "
				//+ "done "  -> een while loop vlak op het einde in de body van een andere while loop werkt nog niet correct,
				//				de buitenste while loop wordt te vroeg gereset
				+ "fi "
				+ "done ");
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		Plant plant = facade.createPlantWithProgram(80, 80, plantSprites, program);
		facade.addPlant(world, plant);
		
		
		facade.advanceTime(world, 0.2);
	}
}
