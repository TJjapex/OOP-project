package jumpingalien.model.program.expressions;

import jumpingalien.part3.programs.SourceLocation;

public class Variabele<T> extends Expression<T>{

	protected Variabele(Object value, SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public T getResult() {
		return null;
	}

}
