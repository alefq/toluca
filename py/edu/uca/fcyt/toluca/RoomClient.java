package py.edu.uca.fcyt.toluca;

/** Java class "RoomClient.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import py.edu.uca.fcyt.game.ChatMessage;
import py.edu.uca.fcyt.game.ChatPanel;
import py.edu.uca.fcyt.game.ChatPanelContainer;
import py.edu.uca.fcyt.toluca.event.RoomEvent;
import py.edu.uca.fcyt.toluca.event.RoomListener;
import py.edu.uca.fcyt.toluca.game.TrucoPlayer;
import py.edu.uca.fcyt.toluca.net.CommunicatorClient;
import py.edu.uca.fcyt.toluca.table.Table;


/**
 *
 * @author  Interfaz de Inicio
 */
public class RoomClient extends Room
                        implements ChatPanelContainer {
    
    private ChatPanel chatPanel;
    private JButtonTable mainTable;
    private RankingTable rankTable;
    private RoomUI rui;
    private CommunicatorClient cc;
    private TrucoPlayer roomPlayer;
    
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
    
    public void setRoomPlayer(TrucoPlayer player){
        roomPlayer = player;
    }
    
    public TrucoPlayer getRoomPlayer(){
        return roomPlayer;
    }
    
    public void setMainTable(JButtonTable mainTable) {
        this.mainTable = mainTable;
    }
    
    public void setChatPanel(ChatPanel chatPanel) {
        this.chatPanel = chatPanel;
    }

    public void setRankingTable(RankingTable rankTable) {
        System.out.println("rank");
        this.rankTable = rankTable;
    }

    public void addTable(Table table) {        /** lock-end */
        super.addTable(table);
        System.out.println("desde el roomClient se inserta mesa.");        
         mainTable.insertarFila(table); /* Agregamos una fila a la tabla */
        
    } // end addTable        /** lock-begin */
    
    public void removeTable(Table table){
        mainTable.eliminarFila(table.getTableNumber());
    }
    
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
    private void fireChatMessageRequested(TrucoPlayer player, String htmlMessage) {        /** lock-end */
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
    
    
    /*
     * Este metodo se ejecuta cuando se presiona el Join
     */
    public void joinTableRequest(int tableNumber){
        fireTableJoinRequested(tableNumber);
    }
    
    /**
     * <p>
     * Informa a todos los <i>listeners</i> registrados que se esta intentando
     * ingresar a una tabla.
     *
     * @param tableNumber El numero de tabla a la que queremos unirnos
     * </p>
     */
    private void fireTableJoinRequested(int tableNumber) {        /** lock-end */
        System.out.println("Voy a disparar el tableJoinRequest sobre la tabela: " + tableNumber);
        RoomEvent re = new RoomEvent();
        re.setType(RoomEvent.TYPE_TABLE_JOIN_REQUESTED);
        re.setTableNumber(tableNumber);
        re.setPlayer(getRoomPlayer());
        Iterator iter = roomListeners.listIterator();
        while(iter.hasNext()) {
            System.out.println("A lo meor no tiene listeners asociados carajo");
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
     * crear una tabla nueva en el Room.
     */
    private void fireTableCreateRequested() {        /** lock-end */
        RoomEvent re = new RoomEvent();
        re.setUser(roomPlayer.getName());
        Iterator iter = roomListeners.listIterator();
        while(iter.hasNext()) {
            System.out.println("A lo meor no tiene listeners asociados carajo");
            RoomListener ltmp = (RoomListener)iter.next();
            ltmp.createTableRequested(re);
        }
    } // end fireTableCreateRequested        /** lock-begin */
    
    public void sendChatMessage(TrucoPlayer player, String htmlMessage) {
        fireChatMessageRequested(player, htmlMessage);
    }
    
    public void showChatMessage(TrucoPlayer player, String htmlMessage) {
        /** lock-end */
        chatPanel.showChatMessage(player, htmlMessage);
    } // end showChatMessage        /** lock-begin */
    
    public void addPlayer(TrucoPlayer player) {
        super.addPlayer(player);
        System.out.println("Gooool!! Carajo");
        if (rankTable == null) System.out.println("Nulooooo!!!");
        rankTable.addPlayer(player);
    }
    
    public void removePlayer(TrucoPlayer player) {        /** lock-end */
        super.removePlayer(player);
        rankTable.removeplayer( player);
    } // end removePlayer        /** lock-begin */
    
    public void modifyPlayer(TrucoPlayer player) {        /** lock-end */
        super.modifyPlayer(player);  
        rankTable.modifyplayer(player);
    }
    
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


    public void loginCompleted(TrucoPlayer player) {        /** lock-end */
        System.out.println("Login completed???");
        chatPanel = new ChatPanel(this, player);
        rui.addChatPanel(chatPanel);
        setRoomPlayer(player);
    } // end loginCompleted        /** lock-begin */

    public void joinTable(RoomEvent re) {
        Vector col = new Vector();
        col = (Vector) re.getPlayerss();
        int tableNumber = re.getTableNumber();
        
        mainTable.addPlayer( (TrucoPlayer) col.elementAt(0),tableNumber);
    }


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

} // end RoomClient




