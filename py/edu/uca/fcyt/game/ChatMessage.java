package py.edu.uca.fcyt.game;

/** Java class "ChatMessage.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */
import java.util.*;
import py.edu.uca.fcyt.toluca.game.*;
import py.edu.uca.fcyt.game.*;

/**
 * <p>
 * 
 * </p>
 */
public class ChatMessage {
   public ChatMessage(TrucoPlayer player, String htmlMessage) {
       setPlayer(player);
       setHtmlMessage(htmlMessage);
       
   }
  ///////////////////////////////////////
  // attributes


/**
 * <p>
 * Represents ...
 * </p>
 */
    private TrucoPlayer owner; 

/**
 * <p>
 * Represents ...
 * </p>
 */
    private String htmlMessage; 

  ///////////////////////////////////////
  // operations


/**
 * <p>
 * Does ...
 * </p><p>
 * 
 * @return a String with ...
 * </p>
 */
    public String getHtmlMessage() {        /** lock-end */
        return htmlMessage;
    } // end getHtmlMessage        /** lock-begin */

/**
 * <p>
 * Does ...
 * </p><p>
 * 
 * </p><p>
 * 
 * @param _htmlMessage ...
 * </p>
 */
    public void setHtmlMessage(String _htmlMessage) {        /** lock-end */
        htmlMessage = _htmlMessage;
    }
    
    /** Getter for property owner.
     * @return Value of property owner.
     *
     */
    public TrucoPlayer getPlayer() {
        return owner;
    }
    
    /** Setter for property owner.
     * @param owner New value of property owner.
     *
     */
    public void setPlayer(TrucoPlayer owner) {
        this.owner = owner;
    }
    
 // end setHtmlMessage        /** lock-begin */

} // end ChatMessage





