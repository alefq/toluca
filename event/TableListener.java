package py.edu.uca.fcyt.toluca.event;


/** Java interface "TableListener.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */
import java.util.*;

import py.edu.uca.fcyt.toluca.*;
import py.edu.uca.fcyt.toluca.game.*;
import py.edu.uca.fcyt.toluca.table.*;
import py.edu.uca.fcyt.game.*;


/**
 * <p>
 *
 * @author Owner
 * </p>
 */
public interface TableListener extends SpaceListener {

	///////////////////////////////////////
	// operations


	public void gameStartRequest(TableEvent event);
	public void gameStarted(Game game);
	public void gameFinished(Game game);
	public void tableLocked(Table table);
	public void tableUnlocked (Table table);
	public void playerKicked(Player player);
    public void sitRequested(Player player, int chair);
//    public void standRequested(Player player, int chair);
    public void playerSit(Player player, int chair);
//    public void playerStand(Player player, int chair);

} // end TableListener





