package py.edu.uca.fcyt.toluca.event;

import java.util.*;

import py.edu.uca.fcyt.toluca.*;
import py.edu.uca.fcyt.toluca.game.*;
import py.edu.uca.fcyt.toluca.table.*;
import py.edu.uca.fcyt.game.*;


/**
 * Listener de eveos de mesa
 * @author Grupo Interfaz de Juego
 */
public interface TableListener extends SpaceListener 
{
	public void gameStartRequest(TableEvent event);
	public void gameStarted(TableEvent event);
	public void gameFinished(TableEvent event);
//	public void tableLocked(TableEvent event);
//	public void tableUnlocked (TableEvent event);
	public void playerStandRequest(TableEvent event);
	public void playerStanded(TableEvent event);
	public void playerKickRequest(TableEvent event);
	public void playerKicked(TableEvent event);
    public void playerSitRequest(TableEvent event);
    public void playerSit(TableEvent event);
    public void signSendRequest(TableEvent event);
    public void signSent(TableEvent event);
    public void showPlayed(TableEvent event);
} 





