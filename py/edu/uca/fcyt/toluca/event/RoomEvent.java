package py.edu.uca.fcyt.toluca.event;

/** Java class "RoomEvent.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */
import java.util.Collection;
import java.util.Vector;

import py.edu.uca.fcyt.game.ChatMessage;
import py.edu.uca.fcyt.toluca.game.TrucoPlayer;
import py.edu.uca.fcyt.toluca.table.Table;
import py.edu.uca.fcyt.toluca.table.TableServer;

 
/**
 * <p>
 * El RoomEvent es una clase que se utiliza para informar a los listeners
 * registrados de la clase Room de los eventos que ocurren en el mismo.
 * </p>
 * <p>
 * El atributo "type" indica el tipo de evento y puede ser cualquiera de
 * los eventos tipificados como variables publicas finales que empiezan con
 * el prefijo TYPE_.
 * </p>
 * <p>
 * Dependiendo del tipo de evento, se cargan las demas propiedades de la
 * clase cuando se quiere informar de un evento:
 * </p>
 * <p>
 * En el lado del cliente:
 * </p>
 * <p>
 * TYPE_CHAT_REQUESTED - se carga el atributo ChatMessage
 * </p>
 * <p>
 * TYPE_CREATE_TABLE_REQUESTED - no se carga nada
 * </p>
 * <p>
 * TYPE_TABLE_JOIN_REQUESTED - se carga el atributo tableNumber con el
 * numero de tabla a la que se quiere unir
 * </p>
 * <p>
 * Los demas tipos son del lado del servidor y por ahora podemos dejarlos
 * vacios puesto que seran utilizados por el RoomServer que no esta a
 * nuestro cargo.
 * </p>
 *
 */
public class RoomEvent {
    
    ///////////////////////////////////////
    // attributes
    
    
    /**
     * <p>
     * Representa el numero de tabla a la que el usuario se intenta unir en el
     * RoomClient
     * </p>
     *
     */
    private int tableNumber;
    private String username;
    private String password;
    
    /**
     * <p>
     * Representa el mensaje de chat que el usuario envia o recibe en el Room
     *
     *</p>
     *
     */
    //constructor con param int para el tipo
    // contructor con un chatmessage
    private ChatMessage chatMessage;
    
    /**
     * <p>
     * Representa el tipo de evento que ocurrio en el Room
     * </p>
     *
     */
    private int type;
    
    /**
     * <p>
     * Representa un vector que contiene objetos de tipo Table para los eventos
     * que involucran manejo de tablas
     * </p>
     *
     */
    private Collection tables; // of type Table
    
    /**
     * <p>
     * Representa un vector que contiene objetos de tipo Player para los
     * eventos que involucran manejo de jugadores
     * </p>
     *
     */
    private Collection Players; // of type Player
    
    /**
     * <p>
     * Representa una constante para los eventos en los que se quiere enviar un
     * mensaje de chat.
     * </p>
     *
     */
    public static final int TYPE_CHAT_REQUESTED=1;
    
    /**
     * <p>
     * Representa una constante para los eventos en los que se esta enviando un
     * mensaje de chat.
     * </p>
     *
     */
    public static final int TYPE_CHAT_SENT=2;
    
    /**
     * <p>
     * Representa una constante para los eventos en los que se intenta crear
     * una tabla.
     * </p>
     *
     */
    public static final int TYPE_CREATE_TABLE_REQUESTED=3;
    
    /**
     * <p>
     * Representa una constante para los eventos en los que se quiere confirma
     * la creacion de una tabla
     * </p>
     *
     */
    public static final int TYPE_TABLE_CREATED=4;
    
    /**
     * <p>
     * Representa una constante para los eventos en los que se intenta unir a
     * una tabla
     * </p>
     *
     */
    public static final int TYPE_TABLE_JOIN_REQUESTED=5;
    
    /**
     * <p>
     * Representa una constante para los eventos en los que se confirma la
     * union de un usuario a una tabla
     * </p>
     *
     */
    public static final int TYPE_TABLE_JOINED=6;
    
    /**
     * <p>
     * Representa una constante para los eventos en los que se intenta unir un
     * jugador a una tabla
     * </p>
     *
     */
    public static final int TYPE_PLAYER_JOINED=7;
    
    /**
     * <p>
     * Representa una constante para los eventos en los que un jugador abandona
     * el Room
     * </p>
     *
     */
    public static final int TYPE_PLAYER_LEFT=8;
    
    /**
     * <p>
     * Representa una constante para los eventos en los un jugador es echado de
     * un Room
     * </p>
     *
     */
    public static final int TYPE_PLAYER_KICKED=9;
    
    ///////////////////////////////////////
    // operations
/**
 * <p>
 * Represents ...
 * </p>
 */
    public static final int TYPE_LOGIN_REQUESTED=10; 

/**
 * <p>
 * Represents ...
 * </p>
 */
    public static final int TYPE_LOGIN_COMPLETED=11; 

/**
 * <p>
 * Represents ...
 * </p>
 */
    public static final int TYPE_LOGIN_FAILED=12; 
    
    public static final int TYPE_TABLE_CREATED_SERVER = 13;
    
    /** Holds value of property player. */
    private TrucoPlayer player;
    
    /** Holds value of property tableServer. */
    private TableServer tableServer;
    
    public RoomEvent(){
        //codigo agregado por el CIT MASTER
       tables = new Vector();
       this.Players = new Vector();
    }
    
    /**
     * <p>
     * Does ...
     * </p><p>
     *
     * @return a int with ...
     * </p>
     */
    public int getType() {        /** lock-end */
        return type;
    } // end getType        /** lock-begin */
    
    /**
     * <p>
     * Does ...
     * </p><p>
     *
     * </p><p>
     *
     * @param _type ...
     * </p>
     */
    public void setType(int _type) {        /** lock-end */
        type = _type;
    } // end setType        /** lock-begin */
    
    /**
     * <p>
     * Does ...
     * </p><p>
     *
     * @return a Collection with ...
     * </p>
     */
    public void setPassword(String password) {
        this.password=new String(password);
    }
    public String getPassword() {
        return password;
    }
    public void setUser(String user) {
        this.username=user;
    }
    public String getUser() {
        return username;
    }
    public Collection getPlayerss() {        /** lock-end */
        return Players;
    } // end getPlayerss        /** lock-begin */
    
    
    /**
     * <p>
     * Does ...
     * </p><p>
     *
     * </p><p>
     *
     * @param _Players ...
     * </p>
     */
    public void setPlayerss(Collection _Players) {        /** lock-end */
        setPlayers(_Players);
    } // end setPlayerss        /** lock-begin */

    public void setPlayers(Collection _Players) {        /** lock-end */
        Players = _Players;
    } // end setPlayerss        /** lock-begin */
    
    
    /**
     * <p>
     * Does ...
     * </p><p>
     *
     * </p><p>
     *
     * @param _Players ...
     * </p>
     */
    public void addPlayers(TrucoPlayer player) {        /** lock-end */
        
        try {
            if (! Players.contains(player))
                Players.add(player);
            
        } catch (java.lang.NullPointerException npe) {
            System.out.println("El collection de players no esta instaciado");
            throw npe;
        }
    } // end addPlayers        /** lock-begin */
    
    /**
     * <p>
     * Does ...
     * </p><p>
     *
     * </p><p>
     *
     * @param _Players ...
     * </p>
     */
    public void removePlayers(TrucoPlayer _Players) {        /** lock-end */
        Players.remove(_Players);
    } // end removePlayers        /** lock-begin */
    
    /**
     * <p>
     * Does ...
     * </p><p>
     *
     * @return a ChatMessage with ...
     * </p>
     */
    public ChatMessage getChatMessage() {        /** lock-end */
        return chatMessage;
    } // end getChatMessage        /** lock-begin */
    
    /**
     * <p>
     * Does ...
     * </p><p>
     *
     * </p><p>
     *
     * @param _chatMessage ...
     * </p>
     */
    public void setChatMessage(ChatMessage _chatMessage) {        /** lock-end */
        chatMessage = _chatMessage;
    } // end setChatMessage        /** lock-begin */
    
    /**
     * <p>
     * Does ...
     * </p><p>
     *
     * @return a Collection with ...
     * </p>
     */
    public Collection getTabless() {        /** lock-end */
        return tables;
    } // end getTabless        /** lock-begin */
    
    /**
     * <p>
     * Does ...
     * </p><p>
     *
     * </p><p>
     *
     * @param _tables ...
     * </p>
     */
    public void setTabless(Collection _tables) {        /** lock-end */
        tables = _tables;
    } // end setTabless        /** lock-begin */
    
    /**
     * <p>
     * Does ...
     * </p><p>
     *
     * </p><p>
     *
     * @param _tables ...
     * </p>
     */
    public void addTables(Table _tables) {        /** lock-end */
        if (! tables.contains(_tables))
            tables.add(_tables);
    } // end addTables        /** lock-begin */

    
    public void addTables(TableServer _tables) { 
        
        /** lock-end */
        try {
        if (! tables.contains(_tables))
            tables.add(_tables);
        
        } catch (Exception e ) {
            System.out.println("Error en el ROOM EVENT.  PQNTSC.  table es nulo al parecer");
            e.printStackTrace(System.out);
           
        }
        
    } // end addTables        /** lock-begin */

    /**
     * <p>
     * Does ...
     * </p><p>
     *
     * </p><p>
     *
     * @param _tables ...
     * </p>
     */
    public void removeTables(Table _tables) {        /** lock-end */
        tables.remove(_tables);
    }
    
    /** Getter for property tableNumber.
     * @return Value of property tableNumber.
     *
     */
    public int getTableNumber() {
        return tableNumber;
    }
    
    /** Setter for property tableNumber.
     * @param tableNumber New value of property tableNumber.
     *
     */
    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }
    
    /** Getter for property player.
     * @return Value of property player.
     *
     */
    public TrucoPlayer getPlayer() {
        return this.player;
    }
    
    /** Setter for property player.
     * @param player New value of property player.
     *
     */
    public void setPlayer(TrucoPlayer player) {
        this.player = player;
    }
    
    /** Getter for property tableServer.
     * @return Value of property tableServer.
     *
     */
    public TableServer getTableServer() {
        return this.tableServer;
    }
    
    /** Setter for property tableServer.
     * @param tableServer New value of property tableServer.
     *
     */
    public void setTableServer(TableServer tableServer) {
        this.tableServer = tableServer;
    }
    
    // end removeTables        /** lock-begin */
    
} // end RoomEvent



