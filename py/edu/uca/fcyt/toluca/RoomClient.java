package py.edu.uca.fcyt.toluca;

/** Java class "RoomClient.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.JOptionPane;
import java.awt.Event;
import java.awt.BorderLayout;

import py.edu.uca.fcyt.toluca.table.*;
import py.edu.uca.fcyt.toluca.net.*;
import py.edu.uca.fcyt.toluca.event.*;

import py.edu.uca.fcyt.game.*;


/**
 * <p>
 *
 * </p>
 */
public class RoomClient extends Room
                        implements ChatPanelContainer {
    
    private ChatPanel chatPanel;
    private JButtonTable mainTable;
    private RankingTable rankTable;
    private RoomUI rui;
    private CommunicatorClient cc;
    
    ///////////////////////////////////////
    // operations
    
    RoomClient(RoomUI rui, String username, String password){
        super();
        cc = new CommunicatorClient(this);
        new Thread(cc).start();
        fireLoginRequested(username, password);
        this.rui = rui;
   //init();
    }
    
    
    
    public void setMainTable(JButtonTable mainTable) {
        this.mainTable = mainTable;
    }
    
    public void setChatPanel(ChatPanel chatPanel) {
        this.chatPanel = chatPanel;
    }

    public void setRankingTable(RankingTable rankTable) {
        this.rankTable = rankTable;
    }

    /**
     * <p>
     * Does ...
     * </p><p>
     *
     * @param table ...
     * </p>
     *
     */
   
    public void addTable(Table table) {        /** lock-end */
         // mainTable.insertarFila(); /* Agregamos una fila a la tabla */
        
    } // end addTable        /** lock-begin */
    
    /**
     * <p>
     * Informa a todos los <i>listeners</i> registrados que se esta intentando
     * enviar un mensaje de chat
     * </p>
     * <p>
     * crear un objeto chatMessage,
     * roomEvent de tipo ChatResquested
     * </p>
     * <p>
     *
     * @param player El jugador que esta intentando enviar el mensaje
     * </p>
     * <p>
     * @param htmlMessage El mensaje que se esta enviando
     * </p>
     */
    private void fireChatMessageRequested(Player player, String htmlMessage) {        /** lock-end */
        ChatMessage chatmsg = new ChatMessage(player, htmlMessage);
        RoomEvent re = new RoomEvent();
        re.setType(RoomEvent.TYPE_CHAT_REQUESTED);
        re.setChatMessage(chatmsg);
        Iterator iter = roomListeners.listIterator();
        while(iter.hasNext()) {
            RoomListener ltmp = (RoomListener)iter.next();
            ltmp.chatMessageRequested(this, player, htmlMessage);
        }
    } // end fireChatMessageRequested        /** lock-begin */
    
    /**
     * <p>
     * Informa a todos los <i>listeners</i> registrados que se esta intentando
     * ingresar a una tabla
     * </p>
     * <p>
     *
     * </p>
     * <p>
     *
     * @param tableNumber El numero de tabla a la que queremos unirnos
     * </p>
     */
    private void fireTableJoinRequested(int tableNumber) {        /** lock-end */
        RoomEvent re = new RoomEvent();
        re.setType(RoomEvent.TYPE_TABLE_JOIN_REQUESTED);
        re.setTableNumber(tableNumber);
        
        Iterator iter = roomListeners.listIterator();
        while(iter.hasNext()) {
            RoomListener ltmp = (RoomListener)iter.next();
            ltmp.tableJoinRequested(re);
        }
    } // end fireTableJoinRequested        /** lock-begin */
    
    public void createTableRequest() {
        fireTableCreateRequested();
    }
    
    /**
     * <p>
     * Informa a todos los <i>listeners</i> registrados que se esta intentando
     * crear una tabla nueva en el Room
     * </p>
     * <p>
     *
     * </p>
     *
     */
    private void fireTableCreateRequested() {        /** lock-end */
        RoomEvent re = new RoomEvent();
        re.setUser("Fernando");
        Iterator iter = roomListeners.listIterator();
        while(iter.hasNext()) {
            RoomListener ltmp = (RoomListener)iter.next();
            ltmp.createTableRequested(re);
        }
    } // end fireTableCreateRequested        /** lock-begin */
    
    
    /** <p>
     * En este metodo no sabemos que se hace?
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
    
    public void sendChatMessage(Player player, String htmlMessage) {
        fireChatMessageRequested(player, htmlMessage);
    }
    
     /**
 * <p>
 * Does ...
 * </p><p>
 * 
 * </p><p>
 * 
 * @param username ...
 * </p><p>
 * @param password ...
 * </p>
 */ 
    public void showChatMessage(Player player, String htmlMessage) {
        /** lock-end */
        chatPanel.showChatMessage(player, htmlMessage);
    } // end showChatMessage        /** lock-begin */
    
      /**
 * <p>
 * Does ...
 * </p><p>
 * 
 * </p><p>
 * 
 * @param username ...
 * </p><p>
 * @param password ...
 * </p>
 */
    public void addPlayer(Player player) {
        super.addPlayer(player);
        System.out.println("Gol de Cerro!!");
        if (rankTable == null) System.out.println("Nulooooo!!!");
        rankTable.addPlayer(player);
    }
    
     /**
 * <p>
 * Does ...
 * </p><p>
 * 
 * </p><p>
 * 
 * @param username ...
 * </p><p>
 * @param password ...
 * </p>
 */ 
    public void removePlayer(Player player) {        /** lock-end */
        super.removePlayer(player);
        rankTable.removeplayer( player.getName() );
    } // end removePlayer        /** lock-begin */
    
      /**
 * <p>
 * Does ...
 * </p><p>
 * 
 * </p><p>
 * 
 * @param username ...
 * </p><p>
 * @param password ...
 * </p>
 */
    public void modifyPlayer(Player player) {        /** lock-end */
        super.modifyPlayer(player);  
        rankTable.modifyplayer(player.getName(), player.getRating() );
    }
    
    /**
 * <p>
 * Does ...
 * </p><p>
 * 
 * </p><p>
 * 
 * @param username ...
 * </p><p>
 * @param password ...
 * </p>
 */
    private void fireLoginRequested(String username, String password) {        /** lock-end */
        RoomEvent re = new RoomEvent();
        re.setType(RoomEvent.TYPE_LOGIN_REQUESTED);
        re.setUser(username);
        re.setPassword(password);
        Iterator iter = roomListeners.listIterator();
        while(iter.hasNext()) {
            RoomListener ltmp = (RoomListener)iter.next();
            ltmp.loginRequested(re);
        }
    } // end fireLoginRequested        /** lock-begin */

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
    public void loginCompleted(Player player) {        /** lock-end */
        System.out.println("Login completed???");
        chatPanel = new ChatPanel(this, player);
        rui.addChatPanel(chatPanel);
        addPlayer(player);
    } // end loginCompleted        /** lock-begin */

/**
 * <p>
 * Does ...
 * </p><p>
 * 
 * </p>
 */
    public void loginFailed() {        /** lock-end */
        JOptionPane.showMessageDialog(new JButton() , new JLabel() + ": Login Failed!");
    }
    
    /** Getter for property rankTable.
     * @return Value of property rankTable.
     *
     */
    public py.edu.uca.fcyt.toluca.RankingTable getRankTable() {
        return rankTable;
    }
    
    /** Setter for property rankTable.
     * @param rankTable New value of property rankTable.
     *
     */
    public void setRankTable(py.edu.uca.fcyt.toluca.RankingTable rankTable) {
        if (rankTable == null)
            System.out.println("Se settea el rank table a null");
        else
            System.out.println("Se settea el rank table a no null");
        this.rankTable = rankTable;
    }
    
 // end loginFailed        /** lock-begin */

} // end RoomClient




