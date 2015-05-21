/**
 * Test file containing tests of directly executed expressions
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

public class TestExpressions {
	
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
	
	@Before
	public void setUp() throws Exception {
		programFactory = new ProgramFactory<>();
		program = programFactory.createProgram(new Wait(new Constant<DoubleType>(new DoubleType(1), null), null), new HashMap<String, Type>());
		facade = new Facade();

	}

	@After
	public void tearDown() throws Exception {
		
	}
	
	/* General Tests */
	
	@Test 
	public void testBinaryExpression(){
		Expression<DoubleType> left = new Constant<DoubleType>(new DoubleType(5.0), null);
		assertEquals(5.0, left.execute(program).getValue(), Util.DEFAULT_EPSILON);
		Expression<DoubleType> right = new Constant<DoubleType>(new DoubleType(3.5), null);
		assertEquals(3.5, right.execute(program).getValue(), Util.DEFAULT_EPSILON);
		
		BinaryOperator<DoubleType, DoubleType> expr = new BinaryOperator<DoubleType, DoubleType>(left, right, (l, r) -> l.add(r), null);
		DoubleType exprResult = expr.execute(program);
		
		assertEquals(8.5, exprResult.getValue(), Util.DEFAULT_EPSILON);
	}
	
	
	
	@Test 
	public void testUnaryExpression(){
		Expression<DoubleType> val = new Constant<DoubleType>(new DoubleType(9.0), null);
		assertEquals(9.0, val.execute(program).getValue(), Util.DEFAULT_EPSILON);
		
		UnaryOperator<DoubleType, DoubleType> expr = new UnaryOperator<DoubleType, DoubleType>(val, x ->  x.sqrt(), null);
		DoubleType exprResult = expr.execute(program);
		
		assertEquals(3.0, exprResult.getValue(), Util.DEFAULT_EPSILON);
	}

}
