package py.edu.uca.fcyt.toluca.table;

import py.edu.uca.fcyt.toluca.*;
import py.edu.uca.fcyt.toluca.game.*;
import py.edu.uca.fcyt.toluca.event.*;
import py.edu.uca.fcyt.game.*;

import java.util.*;

/**
 * Escucha los eventos del juego y hace un broadcasting
 * del mismo a todos los TrucoListeners registrados
 */
class TableEventManager {
    protected Table table;				 // objeto Table
    protected LinkedList tableListeners; // listeners de eventos
    
    /** Construye un TableEventManager con Table 'table' */
    public TableEventManager(Table table) {
        this.table = table;
        tableListeners = new LinkedList();
    }
    
    /** Registra un listener de eventos TableListener */
    public void addListener(TableListener t) {
        Util.verif(t != null, "TableListener nulo");
        tableListeners.add(t);
        if (tableListeners.size() == 2) {
            throw new RuntimeException("This is the part where you ask why 2 TableListeners");
        }
    }
    
    /**
     * Dispara el 'gameStartRequest' de todos los
     * TableListeners registrados
     */
    public void fireGameStartRequest(TableEvent event) {
        Iterator iter = tableListeners.iterator();
        while(iter.hasNext()) {
            ((TableListener)iter.next()).gameStartRequest(event);
        }
    }
    /**
     * Dispara el chatMessageRequested de todos los
     * TrucoListeners registrados
     */
    public void fireChatMessageRequested(Player player, String htmlMessage) {
        Iterator iter = tableListeners.iterator();
        while(iter.hasNext()) {
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
    public void fireChatMessageSent(Player player, String htmlMessage) {
        Iterator iter = tableListeners.iterator();
        while(iter.hasNext()) {            
            ((TableListener) iter.next()).chatMessageSent
              (
                table, player, htmlMessage
              );
        }
    }
    
    /**
     * Dispara el 'gameStarted' de todos los
     * TableListeners registrados
     */
    public void fireGameStarted(Game game) {
        Iterator iter = tableListeners.iterator();
        while(iter.hasNext()) {
            ((TableListener)iter.next()).gameStarted(game);
        }
    }
    
    /**
     * Daispara el 'gameFinished' de todos los
     * TableListeners registrados
     */
    public void fireGameFinished(Game game) {
        Iterator iter = tableListeners.iterator();
        while(iter.hasNext()) {
            ((TableListener)iter.next()).gameFinished(game);
        }
    }
    
    
    /**
     * Dispara el 'playerJoined' de todos los
     * TableListeners registrados
     */
    public void firePlayerJoined(Player player) {
        Iterator iter = tableListeners.iterator();
        while(iter.hasNext()) {
            ((TableListener)iter.next()).playerJoined(player);
        }
    }
    
    /**
     * Dispara el 'playerKicked' de todos los
     * TableListeners registrados
     */
    public void firePlayerKicked(Player player) {
        Iterator iter = tableListeners.iterator();
        while(iter.hasNext()) {
            ((TableListener)iter.next()).playerKicked(player);
        }
    }
    
    /**
     * Dispara el 'playerLeft' de todos los
     * TableListeners registrados
     */
    public void firePlayerLeft(Player player) {
        Iterator iter = tableListeners.iterator();
        while(iter.hasNext()) {
            ((TableListener)iter.next()).playerLeft(player);
        }
    }
    
    /**
     * Dispara el 'tableLocked' de todos los
     * TableListeners registrados
     */
    public void fireTableLocked(Table jtTable) {
        Iterator iter = tableListeners.iterator();
        while(iter.hasNext()) {
            ((TableListener)iter.next()).tableLocked(jtTable);
        }
    }
    
    /**
     * Dispara el 'tableUnlocked' de todos los
     * TableListeners registrados
     */
    public void fireTableUnlocked(Table jtTable) {
        Iterator iter = tableListeners.iterator();
        while(iter.hasNext()) {
            ((TableListener)iter.next()).tableUnlocked(jtTable);
        }
    }
    
    /**
     * Dispara el 'sitRequested' de todos los
     * TableListeners registrados
     */
    public void fireSitRequested(Player player, int chair) {
        Iterator iter = tableListeners.iterator();
        while(iter.hasNext()) {
            ((TableListener)iter.next()).sitRequested(player, chair);
        }
    }
    
    /**
     * Dispara el 'playerSit' de todos los
     * TableListeners registrados
     */
    public void firePlayerSit(Player player, int chair) {
        Iterator iter = tableListeners.iterator();
        while(iter.hasNext()) {
            ((TableListener)iter.next()).playerSit(player, chair);
        }
    }
    
    
    
}