package jumpingalien.model.exceptions;

/**
 * A class for signaling an illegal invocation of the Program execution of a Game object.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 */
public class ProgramRuntimeException extends RuntimeException{
	
	/**
	 * Initialize this new Program run time exception.
	 * 
	 * @param	msg
	 * 				A String that contains further information about the exception.
	 * @post	| new.getMessage() == "ProgramRuntimeException: " + msg
	 */
	public ProgramRuntimeException(String msg){
		this.msg = msg;
	}
	
	/**
	 * A variable registering further information about the exception.
	 */
	private final String msg;
	
	/**
	 * Return the message of this exception.
	 * 
	 * @return	| result == ( "ProgramRuntimeException: " + this.msg )
	 */
	@Override
	public String getMessage() {
		return "ProgramRuntimeException: " + this.msg;
	}
	
	/**
	 * The Java API strongly recommends to explicitly define a version
	 * number for classes that implement the interface Serializable.
	 */
	private static final long serialVersionUID = -1996095432521249082L;

}
