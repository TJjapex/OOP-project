package jumpingalien.model.program;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import jumpingalien.model.GameObject;
import jumpingalien.model.program.expressions.BinaryOperator;
import jumpingalien.model.program.expressions.Expression;
import jumpingalien.model.program.expressions.Variable;
import jumpingalien.model.program.statements.Assignment;
import jumpingalien.model.program.statements.Print;
import jumpingalien.model.program.statements.Statement;
import jumpingalien.part3.programs.IProgramFactory;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.model.program.types.*;


public class ProgramFactory<E,S,T,P> implements IProgramFactory<Expression<?>, Statement, Type, Program>{

	@Override
	public Expression<T> createReadVariable(String variableName,
			Type variableType, SourceLocation sourceLocation) {
		
		// TODO hier moet ge dus uit de variable hashmap die in Program zou moeten zijn het juiste Variable object teruggeven.
		return null;
	}

	@Override
	public Expression<DoubleType> createDoubleConstant(double value,
			SourceLocation sourceLocation) {
		return new Variable<DoubleType>(new DoubleType(value), sourceLocation);
	}

	@Override
	public Expression<BooleanType> createTrue(SourceLocation sourceLocation) {
		return new Variable<BooleanType>(new BooleanType(true), sourceLocation);
	}

	@Override
	public Expression<BooleanType> createFalse(SourceLocation sourceLocation) {
		return new Variable<BooleanType>(new BooleanType(false), sourceLocation);

	}

	@Override
	public Expression<ObjectType> createNull(SourceLocation sourceLocation) {
		return new Variable<ObjectType>(new ObjectType(null), sourceLocation);

	}

	@Override
	public Expression createSelf(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createDirectionConstant(
			jumpingalien.part3.programs.IProgramFactory.Direction value,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<Double> createAddition(Expression<?> left, Expression<?> right,
			SourceLocation sourceLocation) {
		try{
			return new BinaryOperator<Double>( (Expression<Double>) left, (Expression<Double>) right,
					(l, r) ->  l + r, sourceLocation);
			
		}catch( ClassCastException exc){ // TODO waarom pakt deze exception bovenstaande warnings niet?
			throw new IllegalArgumentException();
		}
		
	}

	@Override
	public Expression createSubtraction(Expression left, Expression right,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createMultiplication(Expression left, Expression right,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createDivision(Expression left, Expression right,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createSqrt(Expression expr, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createRandom(Expression maxValue,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createAnd(Expression left, Expression right,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createOr(Expression left, Expression right,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createNot(Expression expr, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createLessThan(Expression left, Expression right,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createLessThanOrEqualTo(Expression left,
			Expression right, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createGreaterThan(Expression left, Expression right,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createGreaterThanOrEqualTo(Expression left,
			Expression right, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createEquals(Expression left, Expression right,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createNotEquals(Expression left, Expression right,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createGetX(Expression expr, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createGetY(Expression expr, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createGetWidth(Expression expr,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createGetHeight(Expression expr,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createGetHitPoints(Expression expr,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createGetTile(Expression x, Expression y,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createSearchObject(Expression direction,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createIsMazub(Expression expr,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createIsShark(Expression expr,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createIsSlime(Expression expr,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createIsPlant(Expression expr,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createIsDead(Expression expr,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createIsTerrain(Expression expr,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createIsPassable(Expression expr,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createIsWater(Expression expr,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createIsMagma(Expression expr,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createIsAir(Expression expr, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createIsMoving(Expression expr, Expression direction,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createIsDucking(Expression expr,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createIsJumping(Expression<?> expr,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createAssignment(String variableName, Type variableType,
			Expression<?> value, SourceLocation sourceLocation) {
	//	VariableIdentifier id = new VariableIdentifier(variableName, variableType);
	//	Variable<? extends Type> var = new Variable<variableType>(variableType, variableName, value.getResult(), sourceLocation);
		
		return new Assignment(variableName, variableType, value, sourceLocation);
		
	}

	@Override
	public Statement createWhile(Expression condition, Statement body,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createForEach(
			String variableName,
			jumpingalien.part3.programs.IProgramFactory.Kind variableKind,
			Expression where,
			Expression sort,
			jumpingalien.part3.programs.IProgramFactory.SortDirection sortDirection,
			Statement body, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createBreak(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createIf(Expression condition, Statement ifBody,
			Statement elseBody, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createPrint(Expression<?> value, SourceLocation sourceLocation) {
		return new Print(value);
	}

	@Override
	public Statement createStartRun(Expression direction,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createStopRun(Expression direction,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createStartJump(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createStopJump(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createStartDuck(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createStopDuck(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createWait(Expression duration,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createSkip(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createSequence(List<Statement> statements,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DoubleType getDoubleType() {
		return null;
	}

	@Override
	public BooleanType getBoolType() {
		return new BooleanType();
	}

	@Override
	public ObjectType getGameObjectType() {
		return new ObjectType();
	}

	@Override
	public DirectionType getDirectionType() {
		return new DirectionType();
	}

	@Override
	public Program createProgram(Statement mainStatement,
			Map<String, Type> globalVariables) {
		
		// TODO
		// TODO Let op! Hier wordt nog geen waarde meegegeven!
		// Er zijn alleen maar globale variabelen, en die worden dus in de map in Program opgeslagen.
		return null;
	}

}
