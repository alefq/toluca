package py.edu.uca.fcyt.toluca;

import java.util.*;
import java.util.Vector;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Collection;
import javax.swing.*;

import py.edu.uca.fcyt.toluca.game.*;
import py.edu.uca.fcyt.toluca.event.*;
import py.edu.uca.fcyt.toluca.table.Table;
import py.edu.uca.fcyt.game.*;

/**
 *
 * @author  Interfaz de Inicio
 */
public abstract class Room implements ChatPanelContainer {
    
    
    protected String name;
    protected Vector players; // of type Player??
    protected Vector roomListeners; // of type Vector
    protected Vector table; // of type Table
    
    public Room() {
        players = new Vector();
        roomListeners = new Vector();
        table = new Vector();
    }
    
    ///////////////////////////////////////
    // operations
    
    
    /**
     * <p>
     * Este metodo es llamado, cuando se pulsa el boton de Crear tabla;
     * en la pieza. Lo que hace es ejecutar el metodo fireTableCreated().
     *
     * En el servidor llama al constructor de un table y
     * despues llama a addTable.
     *
     */
    public void createTableRequest() {
        fireTableCreated();
    }
    
    
    /**
     * <p>
     * Recorre el vector de listeners y ejecuta en cada uno de los objetos del
     * mismo, el metodo tableCreated.
     * </p>
     *
     */
    private void fireTableCreated() {
        RoomEvent re = new RoomEvent();
        re.setType(RoomEvent.TYPE_TABLE_CREATED);
        Iterator iter = roomListeners.listIterator();
        while(iter.hasNext()) {
            RoomListener ltmp = (RoomListener)iter.next();
            ltmp.tableCreated(re);
        }
    }
    
    /**
     *
     * Recorre el vector de listeners y ejecuta en cada uno de los
     * objetos del mismo, el metodo chatMessageRequested
     *
     * @param player El jugador que intenta enviar el mensaje
     *
     * @param htmlMessage El mensaje que se intenta enviar
     *
     */
    private void fireChatMessageSent(TrucoPlayer player, String htmlMessage) {
        /** lock-end */
        
        Iterator iter = roomListeners.listIterator();
        while(iter.hasNext()) {
            RoomListener ltmp = (RoomListener)iter.next();
            ltmp.chatMessageRequested(this, player, htmlMessage);
        }
    } // end fireChatMessageSent        /** lock-begin */
    
    /**
     * <p>
     * Llamar al metodo showChatMessage del componente del Chat
     * del room para que este muestre los mensajes.
     * </p>
     * <p>
     *
     * @param player El jugador que envi&#243; el mensaje de chat
     * </p>
     * <p>
     * @param htmlMessage El mensaje de chat
     * </p>
     */
    public abstract void showChatMessage(TrucoPlayer player, String htmlMessage);
    
    /**
     * <p>
     * Agrega un listener al vector de listeners en el Room.
     * Cuando se ejecuta este metodo, lo unico que hay que hacer es:
     * </p>
     * <p>
     * roomListeners.add(parametro);
     * </p>
     * <p>
     *
     * @param roomListener es el listener que se va a agregar al vector.
     * </p>
     */
    public void addRoomListener(RoomListener roomListener) {        /**
     * lock-end */
        roomListeners.add(roomListener);
    } // end addRoomListener        /** lock-begin */
    
    /**
     * <p>
     * Se ejecuta cuando un jugador trata de unirse a la tabla.
     * Informa de esto a los listeners registrados
     * a traves de la ejecucion de fireTableJoined.
     * </p>
     * <p>
     *
     * @param tableNumber ...
     * </p>
     */
    public void joinTable(int tableNumber) {        /** lock-end */
        fireTableJoined();
    } // end joinTable        /** lock-begin */
    
    /**
     * <p>
     * Recorre el vector de listeners y ejecuta en cada uno
     * de los objetos del mismo, el metodo tableJoined.
     * </p>
     *
     */
    private void fireTableJoined() {        /** lock-end */
        
        RoomEvent re = new RoomEvent();
        re.setType(RoomEvent.TYPE_TABLE_JOINED);
        Iterator iter = roomListeners.listIterator();
        while(iter.hasNext()) {
            RoomListener ltmp = (RoomListener)iter.next();
            ltmp.tableJoined(re);
        }
    } // end fireTableJoined
    
    /**
     * <p>
     * Cuando se ejecuta, agregar al jugador al Room. Actualizar la
     * tabla en la que se muestran los jugadores asi como el vector
     * subyacente.
     * </p>
     * <p>
     *
     * @param player El jugador que esta siendo agregado
     * </p>
     */
    public void addPlayer(TrucoPlayer player) {
        players.add(player); //se carga al vector de jugadores
    }
    
    /**
     * <p>
     * Cuando se ejecuta, sacar al jugador del Room.
     * Actualizar la tabla en la que se muestra el jugador
     * asi como el vector subyacente.
     * </p>
     * <p>
     *
     * @param player El jugador que hay que sacar
     * </p>
     */
    public void removePlayer(TrucoPlayer player) {        /** lock-end */
        players.remove(player);
    } // end removePlayer        /** lock-begin */
    
    /**
     * <p>
     * Modifcar los datos del jugador que esta en el Room
     * </p>
     * <p>
     *
     * @param player El jugador cuyos datos hay que modificar.
     * </p>
     */
    public void modifyPlayer(TrucoPlayer player) {        /** lock-end */
        for(int i=0; i<players.size(); i++){
            if( players.get(i) == player){
                players.setElementAt(player, i);
                i = players.size()+10;
            }
        }
    } // end modifyPlayer        /** lock-begin */

    public String getOrigin() {
        return "room";
    }
    
} // end Room




