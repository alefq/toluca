package py.edu.uca.fcyt.game;

import py.edu.uca.fcyt.toluca.game.*;
import py.edu.uca.fcyt.game.*;

/** Java interface "ChatPanelContainer.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */
import java.util.*;

/**
 * <p>
 * 
 * @author Zuni
 * </p>
 */
public interface ChatPanelContainer {

  ///////////////////////////////////////
  // operations

/**
 * <p>
 * Does ...
 * </p><p>
 * 
 * @param player ...
 * </p><p>
 * @param htmlMessage ...
 * </p><p>
 * 
 * </p>
 */
    public void sendChatMessage(Player player, String htmlMessage);
    public void showChatMessage(Player player, String htmlMessage);

} // end ChatPanelContainer







