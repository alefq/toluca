package py.edu.uca.fcyt.toluca.table;

import py.edu.uca.fcyt.toluca.*;
import py.edu.uca.fcyt.toluca.game.*;
import py.edu.uca.fcyt.toluca.event.*;
import py.edu.uca.fcyt.game.*;

import java.util.*;

/**
 * Dispara eventos de TableListeners
 */
class TableEventManager 
{
    protected Table table;				 // objeto Table
    protected LinkedList tableListeners; // listeners de eventos
    
    /** Construye un TableEventManager con Table 'table' */
    public TableEventManager(Table table) 
    {
        this.table = table;
        tableListeners = new LinkedList();
    }
    
    /** Registra un listener de eventos TableListener */
    public void addListener(TableListener t) 
    {
        Util.verifParam(t != null, "Parámetro 't' nulo");
        tableListeners.add(t);
        Util.verif(tableListeners.size() != 2, "This is the part where you ask why 2 TableListeners");
    }

    /**
     * Dispara el TableListener#playerJoined' de todos los
     * TableListeners registrados
     */
    public void firePlayerJoined(TrucoPlayer player) 
    {
        Iterator iter = tableListeners.iterator();
        while(iter.hasNext()) 
        {
            ((TableListener)iter.next()).playerJoined(player);
        }
    }

    /**
     * Dispara el 'playerLeft' de todos los
     * TableListeners registrados
     */
    public void firePlayerLeft(TrucoPlayer player) 
    {
        Iterator iter = tableListeners.iterator();
        while(iter.hasNext()) 
        {
            ((TableListener)iter.next()).playerLeft(player);
        }
    }

    /**
     * Dispara el chatMessageRequested' de todos los
     * TrucoListeners registrados
     */
    public void fireChatMessageRequested(TrucoPlayer player, String htmlMessage) 
    {
        Iterator iter = tableListeners.iterator();
        while(iter.hasNext()) 
        {
            ((TableListener) iter.next()).chatMessageRequested
            (
	            table, player, htmlMessage
            );
        }
    }

    /**
     * Dispara el chatMessageSent de todos los
     * TableListeners registrados
     */
    public void fireChatMessageSent(TrucoPlayer player, String htmlMessage) 
    {
        Iterator iter = tableListeners.iterator();
        while(iter.hasNext()) {            
            ((TableListener) iter.next()).chatMessageSent
              (
                table, player, htmlMessage
              );
        }
    }

    /**
     * Dispara el 'gameStartRequest' de todos los
     * TableListeners registrados
     */
    public void fireGameStartRequest() 
    {
        Iterator iter = tableListeners.iterator();
        while(iter.hasNext()) 
        {
            ((TableListener)iter.next()).gameStartRequest
            (
            	new TableEvent
            	(
            		TableEvent.EVENT_gameStartRequest,
            		table, null, 0
            	)
            );
        }
    }

    /**
     * Dispara el 'gameStartRequest' de todos los
     * TableListeners registrados
     */
    public void fireGameStarted() 
    {
        Iterator iter = tableListeners.iterator();
        while(iter.hasNext()) 
        {
            ((TableListener)iter.next()).gameStarted
            (
            	new TableEvent
            	(
            		TableEvent.EVENT_gameStarted,
            		table, null, 0
            	)
            );
        }
    }

    /**
     * Daispara el 'gameFinished' de todos los
     * TableListeners registrados
     */
    public void fireGameFinished() 
    {
        Iterator iter = tableListeners.iterator();
        while(iter.hasNext()) 
        {
            ((TableListener)iter.next()).gameFinished
            (
            	new TableEvent
            	(
            		TableEvent.EVENT_gameFinished,
            		table, null, 0
            	)
            );
        }
    }

    /**
     * Dispara el 'gameFinished' de todos los
     * TableListeners registrados
     */
    public void firePlayerStandRequest(int chair) 
    {
        Iterator iter = tableListeners.iterator();
        while(iter.hasNext()) 
        {
            ((TableListener)iter.next()).playerStandRequest
            (
            	new TableEvent
            	(
            		TableEvent.EVENT_playerStandRequest,
            		table, null, chair
            	)
            );
        }
    }

    /**
     * Dispara el 'gameFinished' de todos los
     * TableListeners registrados
     */
    public void firePlayerStanded() 
    {
        Iterator iter = tableListeners.iterator();
        while(iter.hasNext()) 
        {
            ((TableListener)iter.next()).playerStanded
            (
            	new TableEvent
            	(
            		TableEvent.EVENT_playerStanded,
            		table, null, 0
            	)
            );
        }
    }

    /**
     * Dispara el 'playerKicked' de todos los
     * TableListeners registrados
     */
    public void firePlayerKickRequest(TrucoPlayer player) 
    {
        Iterator iter = tableListeners.iterator();
        while(iter.hasNext()) 
        {
            ((TableListener)iter.next()).playerKickRequest
            (
            	new TableEvent
            	(
            		TableEvent.EVENT_playerKickRequest,
            		table, player, 0
            	)
            );
        }
    }

    /**
     * Dispara el 'playerKicked' de todos los
     * TableListeners registrados
     */
    public void firePlayerKicked(TrucoPlayer player) 
    {
        Iterator iter = tableListeners.iterator();
        while(iter.hasNext()) 
        {
            ((TableListener)iter.next()).playerKicked
            (
            	new TableEvent
            	(
            		TableEvent.EVENT_playerKicked,
            		table, player, 0
            	)
            );
        }
    }

    /**
     * Dispara el 'sitRequested' de todos los
     * TableListeners registrados
     */
    public void firePlayerSitRequest(int chair) 
    {
        Iterator iter = tableListeners.iterator();
        while(iter.hasNext()) {
            ((TableListener)iter.next()).playerSitRequest
            (
            	new TableEvent
            	(
            		TableEvent.EVENT_playerSitRequest,
            		table, null, chair
            	)
            );
        }
    }

    /**
     * Dispara el 'playerSit' de todos los
     * TableListeners registrados
     */
    public void firePlayerSit(int chair) 
    {
        Iterator iter = tableListeners.iterator();
        while(iter.hasNext()) {
            ((TableListener)iter.next()).playerSit
            (
            	new TableEvent
            	(
            		TableEvent.EVENT_playerSit,
            		table, null, chair
            	)
            );
        }
    }

    /**
     * Dispara el evento 'sendSign' de todos los
     * TableListeners registrados
     */
    public void fireSignSendRequest(TrucoPlayer dest, int sign)
    {
        Iterator iter = tableListeners.iterator();
        while(iter.hasNext()) 
        {
            ((TableListener)iter.next()).signSendRequest
            (
            	new TableEvent
            	(
            		TableEvent.EVENT_signSendRequest,
            		table, dest, sign
            	)
            );
        }
    }

    /**
     * Dispara el evento 'sendSign' de todos los
     * TableListeners registrados
     */
    public void fireSignSent(int sign)
    {
        Iterator iter = tableListeners.iterator();
        while(iter.hasNext()) 
        {
            ((TableListener)iter.next()).signSent
            (
            	new TableEvent
            	(
            		TableEvent.EVENT_signSent,
            		table, null, sign
            	)
            );
        }
    }

    /**
     * Dispara el evento 'showCards' de todos los
     * TableListeners registrados
     */
    public void fireShowPlayed(int chair)
    {
        Iterator iter = tableListeners.iterator();
        while(iter.hasNext()) 
        {
            ((TableListener)iter.next()).showPlayed
            (
            	new TableEvent
            	(
            		TableEvent.EVENT_showPlayed,
            		table, null, chair
            	)
            );
        }
    }
}