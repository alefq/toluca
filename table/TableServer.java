package py.edu.uca.fcyt.toluca.table;

import py.edu.uca.fcyt.toluca.table.*;
import py.edu.uca.fcyt.toluca.game.*;
import py.edu.uca.fcyt.toluca.event.*;
import py.edu.uca.fcyt.game.*;



/** Java class "RoomServer.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */
import java.util.*;
import java.util.Vector;
import java.util.Collections.*;

/**
 * <p>
 *
 * </p>
 */
public class TableServer implements ChatPanelContainer {
    
    ///////////////////////////////////////
    // attributes
       
    /**
     * <p>
     * Represents ...
     * </p>
     */
    private Vector vPlayers;
    
    private Vector tableListeners;
    
    private TrucoGame game;
    
    ///////////////////////////////////////
    // associations
    
    //private Map pendingConnections = new Collections.synchronizedMap( new HashMap() );
    
    ///////////////////////////////////////
    // operations
    
    /**
     * <p>
     * Does ...
     * </p>
     */
    public TableServer() {
        // your code here
        System.out.println("Soy un room server y voy a instanciar un connection manager.");
        vPlayers = new Vector();
        tableListeners = new Vector();
    } // end RoomServer
    
    
    
     // end createTable
    
     // end fireTableCreated
    
    /**
     * <p>
     * Does ...
     * </p><p>
     *
     * </p><p>
     *
     * @param table ...
     * </p>
     */
    public void fireTableJoined(Table table) {
        // your code here
    } // end fireTableJoined
    
    /**
     * <p>
     * Does ...
     * </p><p>
     *
     * </p><p>
     *
     * @param player ...
     * </p><p>
     * @param htmlMessage ...
     * </p>
     */
    public void fireChatSent(Player player, String htmlMessage) {
        // your code here
    } // end fireChatSent
    
    /**
     * <p>
     * Does ...
     * </p><p>
     *
     * </p><p>
     *
     * @param player ...
     * </p>
     */
    //    public void firePlayerJoined(Player player) {
    //        // your code here
    //    } // end firePlayerJoined
    
    /**
     * <p>
     * Does ...
     * </p><p>
     *
     * </p><p>
     *
     * @param player ...
     * </p>
     */
    public void firePlayerLeft(Player player) {
        // your code here
    } // end firePlayerLeft
    
    /**
     * <p>
     * Does ...
     * </p><p>
     *
     * </p><p>
     *
     * @param player ...
     * </p>
     */
    public void firePlayerKicked(Player player) {
        // your code here
    } // end firePlayerKicked
    
     // end login
    
    
     // end main
    
    /**
     * <p>
     * Does ...
     * </p><p>
     *
     * @return a Vector with ...
     * </p>
     */
    public Vector getVPlayers() {
        return vPlayers;
    } // end getVPlayers
    
    /**
     * <p>
     * Does ...
     * </p><p>
     *
     * </p><p>
     *
     * @param _vPlayers ...
     * </p>
     */
    public void setVPlayers(Vector _vPlayers) {
        vPlayers = _vPlayers;
    } // end setVPlayers
    
     // end getVTables
    
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
     */
    public void sendChatMessage(Player player, String htmlMessage) {
        fireChatMessageSent(player, htmlMessage);
    }
    
    public void showChatMessage(Player player, String htmlMessage) {
    }
    
    // end setVTables
    
    /**
     * <p>
     * Recorre el vector de listeners y ejecuta en cada uno de los objetos del
     * mismo, el metodo fireTableCreated.
     * </p>
     *
     */
    protected void firePlayerJoined(final Player jogador) {
        //la gran avestruz, deberia ser asi con RoomEvent que extiende de la inexistente SpaceEvent
        /*RoomEvent re = new RoomEvent();
        re.setType(RoomEvent.TYPE_PLAYER_JOINED);
        re.addPlayers(jogador);
        Iterator iter = roomListeners.listIterator();
        while(iter.hasNext()) {
            RoomListener ltmp = (py.edu.uca.fcyt.toluca.RoomListener)iter.next();
            ltmp.playerJoined(jogador);
            ltmp.loginCompleted(re);
        }*/
        /*Agrego el jugador a la lista de jugadores.*/
        addPlayer(jogador);
        System.out.println("Dentro de fire user joined (Room Server) , jugador = " + jogador.getName());
        RoomEvent re = new RoomEvent();
        re.setType(RoomEvent.TYPE_PLAYER_JOINED);
        if (jogador == null)
            System.out.println("jogador es null carajo");
        
        Vector v = new Vector();
        v.add(jogador);
        re.setPlayers(v);
        
        Iterator iter = tableListeners.listIterator();
        while(iter.hasNext()) {
            RoomListener ltmp = (RoomListener)iter.next();
            ltmp.playerJoined(jogador);
        }
        
        
    }
    
    public void addPlayer(Player player) {
        getVPlayers().add(player); //se carga al vector de jugadores
    }
    
    
    /**
     * Dispara el evento de chatMessageSent
     */
    protected void fireChatMessageSent(Player jogador, String htmlMessage) {
        Iterator iter = tableListeners.listIterator();
        while(iter.hasNext()) {
            RoomListener ltmp = (RoomListener)iter.next();
            ltmp.chatMessageSent(this, jogador, htmlMessage);
        }
    }
    
    public void chatMessageSent(ChatPanelContainer cpc, Player player, String htmlMessage) {
        
    }
    
    
} // end RoomServer



