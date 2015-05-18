package jumpingalien.model.exceptions;

public class ProgramRuntimeException extends RuntimeException{
	public ProgramRuntimeException(String msg){
		this.msg = msg;
	}
	
	private final String msg;
	
	@Override
	public String getMessage() {
		return "ProgramRuntimeException: " + this.msg;
	}
	
	private static final long serialVersionUID = -1996095432521249082L;

}
