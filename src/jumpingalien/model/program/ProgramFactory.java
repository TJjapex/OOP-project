package jumpingalien.model.program;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import javax.security.auth.x500.X500Principal;

import org.antlr.v4.tool.LeftRecursionCyclesMessage;
import org.hamcrest.SelfDescribing;

import jumpingalien.model.GameObject;
import jumpingalien.model.helper.Orientation;
import jumpingalien.model.program.expressions.*;
import jumpingalien.model.program.statements.Action;
import jumpingalien.model.program.statements.Assignment;
import jumpingalien.model.program.statements.IfThen;
import jumpingalien.model.program.statements.Print;
import jumpingalien.model.program.statements.Sequence;
import jumpingalien.model.program.statements.Statement;
import jumpingalien.model.program.statements.Wait;
import jumpingalien.model.program.statements.WhileDo;
import jumpingalien.part3.programs.IProgramFactory;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.model.program.types.*;


public class ProgramFactory<E,S,T,P> implements IProgramFactory<Expression<?>, Statement, Type, Program>{

	@Override
	public Expression<Type> createReadVariable(String variableName,
			Type variableType, SourceLocation sourceLocation) {
		return new ReadVariable<>( variableName , variableType, sourceLocation);
	}

	@Override
	public Expression<DoubleType> createDoubleConstant(double value,
			SourceLocation sourceLocation) {
		return new Constant<>(new DoubleType(value), sourceLocation);
	}

	@Override
	public Expression<BooleanType> createTrue(SourceLocation sourceLocation) {
		return new Constant<>(new BooleanType(true), sourceLocation);
	}

	@Override
	public Expression<BooleanType> createFalse(SourceLocation sourceLocation) {
		return new Constant<>(new BooleanType(false), sourceLocation);

	}

	@Override
	public Expression<GameObjectType> createNull(SourceLocation sourceLocation) {
		return new Constant<>(new GameObjectType(null), sourceLocation);
	}

	@Override
	public Expression<GameObjectType> createSelf(SourceLocation sourceLocation) {
		return new Self<>(sourceLocation);
	}

	@Override
	public Expression<DirectionType> createDirectionConstant(
			jumpingalien.part3.programs.IProgramFactory.Direction value,
			SourceLocation sourceLocation) {
		return new Constant<>(new DirectionType(value), sourceLocation);
	}

	@Override
	public Expression<DoubleType> createAddition(Expression<?> left, Expression<?> right,
			SourceLocation sourceLocation) {
		return new BinaryOperator<DoubleType, DoubleType>( Expression.cast(left), Expression.cast(right),
					(l, r) ->  l.add(r), sourceLocation);
	}

	@Override
	public Expression<DoubleType> createSubtraction(Expression<?> left, Expression<?> right,
			SourceLocation sourceLocation) {
		return new BinaryOperator<DoubleType, DoubleType>( Expression.cast(left), Expression.cast(right),
				(l, r) ->  l.subtract(r), sourceLocation);
	}

	@Override
	public Expression<DoubleType> createMultiplication(Expression<?> left, Expression<?> right,
			SourceLocation sourceLocation) {
		return new BinaryOperator<DoubleType, DoubleType>( Expression.cast(left), Expression.cast(right),
				(l, r) ->  l.multiply(r), sourceLocation);
	}

	@Override
	public Expression<DoubleType> createDivision(Expression<?> left, Expression<?> right,
			SourceLocation sourceLocation) {
		return new BinaryOperator<DoubleType, DoubleType>( Expression.cast(left), Expression.cast(right),
				(l, r) ->  l.divide(r), sourceLocation);
	}

	@Override
	public Expression<DoubleType> createSqrt(Expression<?> expr, SourceLocation sourceLocation) {
		return new UnaryOperator<DoubleType, DoubleType>( Expression.cast(expr),
				x ->  x.sqrt(), sourceLocation);
	}

	@Override
	public Expression<DoubleType> createRandom(Expression<?> maxValue,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<BooleanType> createAnd(Expression<?> left, Expression<?> right,
			SourceLocation sourceLocation) {
		return new BinaryOperator<BooleanType, BooleanType>( Expression.cast(left), Expression.cast(right),
				(l, r) ->  l.conjunct(r), sourceLocation);
	}

	@Override
	public Expression<BooleanType> createOr(Expression<?> left, Expression<?> right,
			SourceLocation sourceLocation) {
		return new BinaryOperator<BooleanType, BooleanType>( Expression.cast(left), Expression.cast(right),
				(l, r) ->  l.disjunct(r), sourceLocation);
	}

	@Override
	public Expression<BooleanType> createNot(Expression<?> expr, SourceLocation sourceLocation) {
		return new UnaryOperator<BooleanType, BooleanType>( Expression.cast(expr),
				x ->  x.not(), sourceLocation);
	}

	@Override
	public Expression<BooleanType> createLessThan(Expression<?> left, Expression<?> right,
			SourceLocation sourceLocation) {
		return new BinaryOperator<DoubleType, BooleanType>( Expression.cast(left), Expression.cast(right),
				(l, r) ->  l.lessThan(r), sourceLocation);
	}

	@Override
	public Expression<BooleanType> createLessThanOrEqualTo(Expression<?>left,
			Expression<?> right, SourceLocation sourceLocation) {
		return new BinaryOperator<DoubleType, BooleanType>( Expression.cast(left), Expression.cast(right),
				(l, r) ->  l.lessThanOrEqualTo(r), sourceLocation);
	}

	@Override
	public Expression<BooleanType> createGreaterThan(Expression<?> left, Expression<?> right,
			SourceLocation sourceLocation) {
		return new BinaryOperator<DoubleType, BooleanType>( Expression.cast(left), Expression.cast(right),
				(l, r) ->  l.greaterThan(r), sourceLocation);
	}

	@Override
	public Expression<BooleanType> createGreaterThanOrEqualTo(Expression<?> left,
			Expression<?> right, SourceLocation sourceLocation) {
		return new BinaryOperator<DoubleType, BooleanType>( Expression.cast(left), Expression.cast(right),
				(l, r) ->  l.greaterThanOrEqualTo(r), sourceLocation);
	}

	@Override
	public Expression<BooleanType> createEquals(Expression<?> left, Expression<?> right,
			SourceLocation sourceLocation) {
		return new BinaryOperator<Type, BooleanType>( Expression.cast(left), Expression.cast(right), (l,  r) -> l.equals(r), sourceLocation);	
	}

	@Override
	public Expression<BooleanType> createNotEquals(Expression<?> left, Expression<?> right,
			SourceLocation sourceLocation) {
		return new BinaryOperator<Type, BooleanType>( Expression.cast(left), Expression.cast(right), (l,  r) ->  (l.equals(r)).not(), sourceLocation);	
	}

	@Override
	public Expression<DoubleType> createGetX(Expression<?> expr, SourceLocation sourceLocation) {
		return new UnaryOperator<GameObjectType, DoubleType>(Expression.cast(expr), (x-> new DoubleType(((GameObject) x.getValue()).getRoundedPositionX())), sourceLocation);
	}

	@Override
	public Expression<DoubleType> createGetY(Expression<?> expr, SourceLocation sourceLocation) {
		return new UnaryOperator<GameObjectType, DoubleType>(Expression.cast(expr), (x-> new DoubleType(((GameObject) x.getValue()).getRoundedPositionY())), sourceLocation);
	}

	@Override
	public Expression<DoubleType> createGetWidth(Expression<?> expr,
			SourceLocation sourceLocation) {
		return new UnaryOperator<GameObjectType, DoubleType>(Expression.cast(expr), (x-> new DoubleType(((GameObject) x.getValue()).getWidth())), sourceLocation);
	}

	@Override
	public Expression<DoubleType> createGetHeight(Expression<?> expr,
			SourceLocation sourceLocation) {
		return new UnaryOperator<GameObjectType, DoubleType>(Expression.cast(expr), (x-> new DoubleType(((GameObject) x.getValue()).getHeight())), sourceLocation);
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
	public Expression<BooleanType> createIsAir(Expression<?> expr, SourceLocation sourceLocation) {
		//return new Checker( expr, x->x.isMoving(), sourceLocation);
		return null;
	}

	@Override
	public Expression<BooleanType> createIsMoving(Expression<?> expr, Expression<?> direction,
			SourceLocation sourceLocation) {
		// TODO ik denk niet dat dit gaat met die Checker (of UnaryOperator) omdat ge die direction niet kunt evalueren bij de lambda functie hier...
		
		//return new Checker( Expression.cast(expr), x-> (x.isMoving(... ? )), sourceLocation);
		return null;

	}

	@Override
	public Expression<BooleanType> createIsDucking(Expression<?> expr,
			SourceLocation sourceLocation) {
		return new Checker( Expression.cast(expr), x->x.isDucking(), sourceLocation);
	
		//kan ook zo met UnaryOperator maar is omslachtiger
		//return new UnaryOperator<ObjectType, BooleanType>( ( Expression<ObjectType>) expr, (x-> new BooleanType(((GameObject) x.getValue()).isDucking())), sourceLocation);
	}

	@Override
	public Expression<BooleanType> createIsJumping(Expression<?> expr,
			SourceLocation sourceLocation) {
		return new Checker( Expression.cast(expr), x->!x.isOnGround(), sourceLocation);
		//return new UnaryOperator<ObjectType, BooleanType>( ( Expression<ObjectType>) expr, (x-> new BooleanType(!((GameObject) x.getValue()).isOnGround())), sourceLocation);
	}

	@Override
	public Statement createAssignment(String variableName, Type variableType,
			Expression<?> value, SourceLocation sourceLocation) {
		return new Assignment(variableName, variableType, Expression.cast(value), sourceLocation);
		
	}

	@Override
	public Statement createWhile(Expression<?> condition, Statement body,
			SourceLocation sourceLocation) {
		System.out.println("ProgramFactory: createWhile()");
		return new WhileDo(condition, body, sourceLocation);
	}

	@Override
	public Statement createForEach(
			String variableName,
			jumpingalien.part3.programs.IProgramFactory.Kind variableKind,
			Expression<?> where,
			Expression<?> sort,
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
	public Statement createIf(Expression<?> condition, Statement ifBody,
			Statement elseBody, SourceLocation sourceLocation) {
		return new IfThen(Expression.cast(condition), ifBody, elseBody, sourceLocation);
	}

	@Override
	public Statement createPrint(Expression<?> value, SourceLocation sourceLocation) {
		return new Print(value, sourceLocation);
	}

	@Override
	public Statement createStartRun(Expression<?> direction,
			SourceLocation sourceLocation) {
		try{
			return new Action( (x, program) -> x.startMove( ((Expression<DirectionType>) direction).execute(program).getValue() ), createSelf(sourceLocation), sourceLocation); 
		}catch( ClassCastException exc){
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Statement createStopRun(Expression<?> direction,
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
	public Statement createWait(Expression<?> duration,
			SourceLocation sourceLocation) {
		return new Wait(Expression.cast(duration), sourceLocation);
	}

	@Override
	public Statement createSkip(SourceLocation sourceLocation) {
		return new Wait(new Constant<DoubleType>(new DoubleType(0.001), sourceLocation), sourceLocation);
	}

	@Override
	public Statement createSequence(List<Statement> statements,
			SourceLocation sourceLocation) {
		//System.out.println("ProgramFactory, createSequence: statements "+statements);
		return new Sequence(statements, sourceLocation);
	}

	@Override
	public DoubleType getDoubleType() {
		return new DoubleType();
	}

	@Override
	public BooleanType getBoolType() {
		return new BooleanType();
	}

	@Override
	public GameObjectType getGameObjectType() {
		return new GameObjectType();
	}

	@Override
	public DirectionType getDirectionType() {
		return new DirectionType();
	}

	@Override
	public Program createProgram(Statement mainStatement,
			Map<String, Type> globalVariables) {
		System.out.println("ProgramFactory, createProgram with mainStatement: "+mainStatement);
		return new Program(mainStatement, globalVariables);
	}

}
