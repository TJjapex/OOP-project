package jumpingalien.program;

import java.util.List;
import java.util.Map;

import jumpingalien.model.Buzam;
import jumpingalien.model.GameObject;
import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.Shark;
import jumpingalien.model.Slime;
import jumpingalien.model.Tile;
import jumpingalien.model.World;
import jumpingalien.model.interfaces.IDuckable;
import jumpingalien.model.interfaces.IJumpable;
import jumpingalien.model.interfaces.IMovable;
import jumpingalien.program.expressions.*;
import jumpingalien.part3.programs.IProgramFactory;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.program.statements.Action;
import jumpingalien.program.statements.Assignment;
import jumpingalien.program.statements.Break;
import jumpingalien.program.statements.ForEachDo;
import jumpingalien.program.statements.IfThen;
import jumpingalien.program.statements.Print;
import jumpingalien.program.statements.Sequence;
import jumpingalien.program.statements.Statement;
import jumpingalien.program.statements.Wait;
import jumpingalien.program.statements.WhileDo;
import jumpingalien.program.types.*;
import jumpingalien.model.terrain.Terrain;

public class ProgramFactory<E,S,T,P> implements IProgramFactory<Expression<?>, Statement, Type, Program>{

	@Override
	public Expression<Type> createReadVariable(String variableName,
			Type variableType, SourceLocation sourceLocation) {
		return new ReadVariable<>( variableName , sourceLocation);
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
	public Expression<ObjectType> createNull(SourceLocation sourceLocation) {
		return new Constant<>(new ObjectType(null), sourceLocation);
	}

	@Override
	public Expression<ObjectType> createSelf(SourceLocation sourceLocation) {
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
		return new UnaryOperator<DoubleType, DoubleType>( Expression.cast(maxValue),
				x ->  new DoubleType( Math.random()*x.getValue()), sourceLocation);
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
		return new BinaryOperator<Type, BooleanType>( Expression.cast(left), Expression.cast(right), (l,  r) -> l.typeEquals(r), sourceLocation);	
	}

	@Override
	public Expression<BooleanType> createNotEquals(Expression<?> left, Expression<?> right,
			SourceLocation sourceLocation) {
		return new BinaryOperator<Type, BooleanType>( Expression.cast(left), Expression.cast(right), (l,  r) ->  (l.typeEquals(r)).not(), sourceLocation);	
	}

	@Override
	public Expression<DoubleType> createGetX(Expression<?> expr, SourceLocation sourceLocation) {
		return new UnaryOperator<ObjectType, DoubleType>(Expression.cast(expr), (x -> new DoubleType(x.getValue().getRoundedPositionX())), sourceLocation);
	}

	@Override
	public Expression<DoubleType> createGetY(Expression<?> expr, SourceLocation sourceLocation) {
		return new UnaryOperator<ObjectType, DoubleType>(Expression.cast(expr), (x -> new DoubleType(x.getValue().getRoundedPositionY())), sourceLocation);
	}

	@Override
	public Expression<DoubleType> createGetWidth(Expression<?> expr,
			SourceLocation sourceLocation) {
		return new UnaryOperator<ObjectType, DoubleType>(Expression.cast(expr), (x -> new DoubleType(x.getValue().getWidth())), sourceLocation);
	}

	@Override
	public Expression<DoubleType> createGetHeight(Expression<?> expr,
			SourceLocation sourceLocation) {
		return new UnaryOperator<ObjectType, DoubleType>(Expression.cast(expr), (x -> new DoubleType(x.getValue().getHeight())), sourceLocation);
	}

	@Override
	public Expression<DoubleType> createGetHitPoints(Expression<?> expr,
			SourceLocation sourceLocation) {
		return new UnaryOperator<ObjectType, DoubleType>(Expression.cast(expr), (x -> new DoubleType(((GameObject) x.getValue()).getNbHitPoints())), sourceLocation);
	}

	@Override
	public Expression<ObjectType> createGetTile(Expression<?> x, Expression<?> y,
			SourceLocation sourceLocation) {
		return new CoordinatesOperator<ObjectType>(Expression.cast(x), Expression.cast(y),
													 (xCoord, yCoord) -> ( program -> { World world = program.getGameObject().getWorld();
																			 			return new ObjectType(world.getTile(xCoord.intValue(),yCoord.intValue()));}),
												   sourceLocation);
	}

	@Override
	public Expression<ObjectType> createSearchObject(Expression<?> direction,
			SourceLocation sourceLocation) {
		return new SearchObject(Expression.cast(direction), sourceLocation);
	}

	@Override
	public Expression<BooleanType> createIsMazub(Expression<?> expr,
			SourceLocation sourceLocation) {
		return new Checker( Expression.cast(expr), (x, program)-> ( x.getClass().getName() == "jumpingalien.model.Mazub" ), sourceLocation);
	}

	@Override
	public Expression<BooleanType> createIsShark(Expression<?> expr,
			SourceLocation sourceLocation) {
		return new Checker( Expression.cast(expr), (x, program)-> (x instanceof Shark), sourceLocation);
	}

	@Override
	public Expression<BooleanType> createIsSlime(Expression<?> expr,
			SourceLocation sourceLocation) {
		return new Checker( Expression.cast(expr), (x, program)-> (x instanceof Slime), sourceLocation);
	}

	@Override
	public Expression<BooleanType> createIsPlant(Expression<?> expr,
			SourceLocation sourceLocation) {
		return new Checker( Expression.cast(expr), (x, program)-> (x instanceof Plant), sourceLocation);
	}

	@Override
	public Expression<BooleanType> createIsDead(Expression<?> expr,
			SourceLocation sourceLocation) {
		return new Checker( Expression.cast(expr), (x, program) -> (((GameObject) x).isKilled()), sourceLocation);
	}

	@Override
	public Expression<BooleanType> createIsTerrain(Expression<?> expr,
			SourceLocation sourceLocation) {
		return new Checker( Expression.cast(expr), (x, program) -> (x instanceof Tile), sourceLocation);
	}

	@Override
	public Expression<BooleanType> createIsPassable(Expression<?> expr,
			SourceLocation sourceLocation) {
		return new Checker( Expression.cast(expr), (x, program) -> ((Tile) x).getTerrainType() != Terrain.SOLID, sourceLocation);
	}

	@Override
	public Expression<BooleanType> createIsWater(Expression<?> expr,
			SourceLocation sourceLocation) {
		return new Checker( Expression.cast(expr), (x, program) -> ((Tile) x).getTerrainType() == Terrain.WATER, sourceLocation);
	}

	@Override
	public Expression<BooleanType> createIsMagma(Expression<?> expr,
			SourceLocation sourceLocation) {
		return new Checker( Expression.cast(expr), (x, program) -> ((Tile) x).getTerrainType() == Terrain.MAGMA, sourceLocation);
	}

	@Override
	public Expression<BooleanType> createIsAir(Expression<?> expr, SourceLocation sourceLocation) {
		return new Checker( Expression.cast(expr), (x, program) -> ((Tile) x).getTerrainType() == Terrain.AIR, sourceLocation);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Expression<BooleanType> createIsMoving(Expression<?> expr, Expression<?> direction,
			SourceLocation sourceLocation) {		
		return new Checker( Expression.cast(expr), (x, program)-> (((IMovable) x).isMoving(((Expression<DirectionType>) direction).execute(program).getValue())), sourceLocation);
	}

	@Override
	public Expression<BooleanType> createIsDucking(Expression<?> expr,
			SourceLocation sourceLocation) {
		return new Checker( Expression.cast(expr), (x, program)->((IDuckable) x).isDucking(), sourceLocation);
	}

	@Override
	public Expression<BooleanType> createIsJumping(Expression<?> expr,
			SourceLocation sourceLocation) {
		return new Checker( Expression.cast(expr), (x, program)->((IJumpable) x).isJumping(), sourceLocation);
	}

	@Override
	public Statement createAssignment(String variableName, Type variableType,
			Expression<?> value, SourceLocation sourceLocation) {
		return new Assignment(variableName, variableType, Expression.cast(value), sourceLocation);
		
	}

	@Override
	public Statement createWhile(Expression<?> condition, Statement body,
			SourceLocation sourceLocation) {
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
		return new ForEachDo(variableKind, variableName, Expression.cast(where), Expression.cast(sort), sortDirection, body, sourceLocation);
	}

	@Override
	public Statement createBreak(SourceLocation sourceLocation) {
		return new Break(sourceLocation);
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

	@SuppressWarnings("unchecked")
	@Override
	public Statement createStartRun(Expression<?> direction,
			SourceLocation sourceLocation) {
		try{
			return new Action( (x, program) -> ((IMovable) x).startMove( ((Expression<DirectionType>) direction).execute(program).getValue() ), createSelf(sourceLocation), sourceLocation); 
		}catch( ClassCastException exc){
			throw new IllegalArgumentException();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Statement createStopRun(Expression<?> direction,
			SourceLocation sourceLocation) {
		return new Action( (x, program) -> ((IMovable) x).endMove( ((Expression<DirectionType>) direction).execute(program).getValue() ), createSelf(sourceLocation), sourceLocation); 
	}

	@Override
	public Statement createStartJump(SourceLocation sourceLocation) {
		return new Action( (x, program) -> ((IJumpable) x).startJump(), createSelf(sourceLocation), sourceLocation); 
	}

	@Override
	public Statement createStopJump(SourceLocation sourceLocation) {
		return new Action( (x, program) -> ((IJumpable) x).endJump(), createSelf(sourceLocation), sourceLocation); 

	}

	@Override
	public Statement createStartDuck(SourceLocation sourceLocation) {
		return new Action( (x, program) -> ((IDuckable) x).startDuck(), createSelf(sourceLocation), sourceLocation); 

	}

	@Override
	public Statement createStopDuck(SourceLocation sourceLocation) {
		return new Action( (x, program) -> ((IDuckable) x).endDuck(), createSelf(sourceLocation), sourceLocation); 
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
		return new Sequence(statements, sourceLocation);
	}

	@Override
	public DoubleType getDoubleType() {
		return new DoubleType(0.0);
	}

	@Override
	public BooleanType getBoolType() {
		return new BooleanType(false);
	}

	@Override
	public ObjectType getGameObjectType() {
		return new ObjectType(null);
	}

	@Override
	public DirectionType getDirectionType() {
		return new DirectionType(Direction.RIGHT);
	}

	@Override
	public Program createProgram(Statement mainStatement,
			Map<String, Type> globalVariables) {
		return new Program(mainStatement, globalVariables);
	}

}
