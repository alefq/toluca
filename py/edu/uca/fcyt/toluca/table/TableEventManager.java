package py.edu.uca.fcyt.toluca.table;

import java.util.Iterator;
import java.util.LinkedList;
import java.awt.Cursor;

import py.edu.uca.fcyt.toluca.event.TableEvent;
import py.edu.uca.fcyt.toluca.event.TableListener;
import py.edu.uca.fcyt.toluca.game.TrucoPlayer;

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
        Util.verifParam(t != null, "Par�metro 't' nulo");
        tableListeners.add(t);
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
		table.setCursor(Cursor.WAIT_CURSOR);

        Iterator iter = tableListeners.iterator();
        while(iter.hasNext()) 
        {
            System.out.println("iterando...");
            ((TableListener)iter.next()).gameStartRequest
            (
            	new TableEvent
            	(
            		TableEvent.EVENT_gameStartRequest,
            		table, null, null,-1
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
		table.setCursor(Cursor.DEFAULT_CURSOR);
    	
        Iterator iter = tableListeners.iterator();
        while(iter.hasNext()) 
        {
            ((TableListener)iter.next()).gameStarted
            (
            	new TableEvent
            	(
            		TableEvent.EVENT_gameStarted,
            		table, null, null,-1
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
            		table, null, null,-1
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
		table.setCursor(Cursor.WAIT_CURSOR);
		
        Iterator iter = tableListeners.iterator();
        while(iter.hasNext()) 
        {
            ((TableListener)iter.next()).playerStandRequest
            (
            	new TableEvent
            	(
            		TableEvent.EVENT_playerStandRequest,
            		table, table.getPlayer(), null,chair
            	)
            );
        }
    }

    /**
     * Dispara el 'gameFinished' de todos los
     * TableListeners registrados
     */
    public void firePlayerStanded(TrucoPlayer player) 
    {
		table.setCursor(Cursor.DEFAULT_CURSOR);
    	
        Iterator iter = tableListeners.iterator();
        while(iter.hasNext()) 
        {
            ((TableListener)iter.next()).playerStanded
            (
            	new TableEvent
            	(
            		TableEvent.EVENT_playerStanded,
            		table, player, null,-1
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
            		table, player, null,-1
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
            		table, player, null,-1
            	)
            );
        }
    }

    /**
     * Dispara el 'playerLeft' de todos los
     * TableListeners registrados
     */
    public void firePlayerLeft() 
    {
        Iterator iter = tableListeners.iterator();
        while(iter.hasNext()) 
        {
            ((TableListener)iter.next()).playerLeft
            (
            	new TableEvent
            	(
            		TableEvent.EVENT_playerLeft,
            		table, null, null,-1
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
		table.setCursor(Cursor.WAIT_CURSOR);
    	
        Iterator iter = tableListeners.iterator();
        TableEvent event=new TableEvent();
        event.setEvent(TableEvent.EVENT_playerSitRequest);
        event.setValue(chair);
        event.setTableBeanRepresentation( table.getTableBeanRepresentation());
        event.setPlayer(new TrucoPlayer[]{table.getPlayer()});
        
        while(iter.hasNext()) {
            ((TableListener)iter.next()).playerSitRequest( event );
        }
    }

    /**
     * Dispara el 'playerSit' de todos los
     * TableListeners registrados
     */
    public void firePlayerSit() 
    {
		table.setCursor(Cursor.DEFAULT_CURSOR);

        Iterator iter = tableListeners.iterator();
        while(iter.hasNext()) {
            ((TableListener)iter.next()).playerSit
            (
            	new TableEvent
            	(
            		TableEvent.EVENT_playerSit,
            		table, null, null,-1
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
		table.setCursor(Cursor.WAIT_CURSOR);
        Iterator iter = tableListeners.iterator();
        while(iter.hasNext()) 
        {
            ((TableListener)iter.next()).signSendRequest
            (
            	new TableEvent
            	(
            		TableEvent.EVENT_signSendRequest,
            		table, table.getPlayer(), dest, sign
            	)
            );
        }
    }

    /**
     * Dispara el evento 'sendSign' de todos los
     * TableListeners registrados
     */
    public void fireSignSent(TrucoPlayer dest)
    {
		table.setCursor(Cursor.DEFAULT_CURSOR);
        Iterator iter = tableListeners.iterator();
        while(iter.hasNext()) 
        {
            ((TableListener)iter.next()).signSent
            (
            	new TableEvent
            	(
            		TableEvent.EVENT_signSent,
            		table, table.getPlayer(), dest, -1
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
            		table, null, null,chair
            	)
            );
        }
    }
}