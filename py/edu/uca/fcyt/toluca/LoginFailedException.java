package py.edu.uca.fcyt.toluca;


/** Java class "LoginFailedException.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */

/**
 * <p>
 * 
 * </p>
 */
public class LoginFailedException extends Exception {

  ///////////////////////////////////////
  // attributes


    public LoginFailedException()
    {
    }
    
    public LoginFailedException(String msg)
    {
        super(msg);        
    }
    
/**
 * <p>
 * Represents ...
 * </p>
 */
    private int type; 

/**
 * <p>
 * Represents ...
 * </p>
 */
    public static final int USER_DOES_NOT_EXISTS = 1; 

/**
 * <p>
 * Represents ...
 * </p>
 */
    public static final int PASSWORD_DOES_NOT_MATCH = 2; 

  ///////////////////////////////////////
  // operations


/**
 * <p>
 * Does ...
 * </p><p>
 * 
 * @return a int with ...
 * </p>
 */
    public int getType() {        
        return type;
    } // end getType        

/**
 * <p>
 * Does ...
 * </p><p>
 * 
 * </p><p>
 * 
 * @param _type ...
 * </p>
 */
    public void setType(int _type) {        
        type = _type;
    } // end setType        

} // end LoginFailedException



