/*
 * TableServer.java
 *
 * Created on 4 de junio de 2003, 07:22 PM
 */

package py.edu.uca.fcyt.toluca.table;

import py.edu.uca.fcyt.toluca.event.*;
import py.edu.uca.fcyt.game.*;
import py.edu.uca.fcyt.toluca.game.*;

import java.util.*;

/**
 *
 * @author  PABLO JAVIER
 */
public class TableServer
implements TrucoListener, ChatPanelContainer {
    
    /** Holds value of property host. */
    private TrucoPlayer host;
    
    protected Vector tableListeners; // of type Vector
    protected Vector players;
    
    protected PlayerManager pManager;
    
    /** Holds value of property tableNumber. */
    private int tableNumber;
    
    private static int nextTableNumber = 0;
    
    /** Creates a new instance of TableServer */
    public TableServer(TrucoPlayer host) {
        this.host= host;
        tableListeners = new Vector();
        players = new Vector();
        pManager = new PlayerManager(6);
        addPlayer(host);
        setTableNumber(nextTableNumber++);
        System.out.println("EL TABLE NUMBER SETEADO ES: " + getTableNumber());
    }
    
    public void addTableServerListener(TableListener tableListener) {        /**
     * lock-end */
        tableListeners.add(tableListener);
    } // end addRoomListener        /** lock-begin */
    
    public void sitPlayer(TrucoPlayer player, int chair) {
        pManager.sitPlayer(player, chair);
        firePlayerSat(player, chair);
        
        if (player == getHost())
            pManager.setActualPlayer(getHost());
                        
        System.out.println(player.getName() + " sitted in server chair " + chair + " in table of " + getHost().getName());
    }
    
    public void startGame() {
        
    }
    
    public void cardsDeal(TrucoEvent event) {
    }
    
    public void endOfGame(TrucoEvent event) {
    }
    
    public void endOfHand(TrucoEvent event) {
    }
    
    public void gameStarted(TrucoEvent event) {
    }
    
    public TrucoPlayer getAssociatedPlayer() {
        return host;
    }
    
    public void handStarted(TrucoEvent event) {
        // IGNORAR en el lado del servidor
    }
    
    public void play(TrucoEvent event) {
        // IGNORAR en el lado del servidor
    }
    
    public void turn(TrucoEvent event) {
        // IGNORAR en el lado del servidor
    }
    
    /** <p>
     * Does ...
     * </p><p>
     *
     * @param player ...
     * </p><p>
     * @param htmlMessage ...
     * </p><p>
     *
     * </p>
     *
     */
    public void sendChatMessage(TrucoPlayer player, String htmlMessage) {
        fireChatMessageSent(player, htmlMessage);
    }
    
    /**
     * Dispara el evento de chatMessageSent
     */
    protected void fireChatMessageSent(TrucoPlayer jogador, String htmlMessage) {
        Iterator iter = tableListeners.listIterator();
        int i =0;
        while(iter.hasNext()) {
            TableListener ltmp = (TableListener)iter.next();
            System.out.println(jogador.getName() + " enviando message sent al listener #" + (i++) + " clase:" + ltmp.getClass().getName());
            ltmp.chatMessageSent(this, jogador, htmlMessage);
        }
    }

    
    protected void firePlayerSat(TrucoPlayer jogador, int chair ) {
        Iterator iter = tableListeners.listIterator();
        int i =0;
        while(iter.hasNext()) {
            TableListener ltmp = (TableListener)iter.next();
            System.out.println(jogador.getName() + " enviando message sent al listener #" + (i++) + " clase:" + ltmp.getClass().getName());
            TableEvent te= new TableEvent(TableEvent.EVENT_playerSit,this, jogador, chair);
            ltmp.playerSit(te);
        }
    }
        
    public void showChatMessage(TrucoPlayer player, String htmlMessage) {
    }
    
    /** Getter for property host.
     * @return Value of property host.
     *
     */
    public TrucoPlayer getHost() {
        return this.host;
    }
    
    /** Setter for property host.
     * @param host New value of property host.
     *
     */
    public void setHost(TrucoPlayer host) {
        this.host = host;
    }
    
    public void addPlayer(TrucoPlayer player) {
        players.add(player);
    }
    
    /** Getter for property players.
     * @return Value of property players.
     *
     */
    public java.util.Vector getPlayers() {
        return players;
    }
    
    /** Setter for property players.
     * @param players New value of property players.
     *
     */
    public void setPlayers(java.util.Vector players) {
        this.players = players;
    }
    
    /** Getter for property tableNumber.
     * @return Value of property tableNumber.
     *
     */
    public int getTableNumber() {
        return this.tableNumber;
    }
    
    /** Setter for property tableNumber.
     * @param tableNumber New value of property tableNumber.
     *
     */
    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }
    
    public String getOrigin() {
        return String.valueOf(getTableNumber());
    }
    
}
