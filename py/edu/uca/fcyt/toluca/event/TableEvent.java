package py.edu.uca.fcyt.toluca.event;

import py.edu.uca.fcyt.toluca.game.*;
import py.edu.uca.fcyt.game.*;
import py.edu.uca.fcyt.toluca.table.*;
import java.security.*;

import org.jdom.*;

/**
 * Contiene los datos necesarios para manejar un evento de mesa
 * @author Grupo Interfaz de Juego
 */
public class TableEvent {
    public static int EVENT_gameStartRequest = 0;
    public static int EVENT_gameStarted = 1;
    public static int EVENT_gameFinished = 2;
    public static int EVENT_tableLocked = 3;
    public static int EVENT_tableUnlocked = 4;
    public static int EVENT_playerStandRequest = 5;
    public static int EVENT_playerStanded = 6;
    public static int EVENT_playerKickRequest = 7;
    public static int EVENT_playerKicked = 8;
    public static int EVENT_playerSitRequest = 9;
    public static int EVENT_playerSit = 10;
    public static int EVENT_signSendRequest = 11;
    public static int EVENT_signSent = 12;
    public static int EVENT_showPlayed = 13;
    private static int PLAYER_MASK = 0x4000;
    private static int VALUE_MASK = 0x8000;
    
    private int event;
    private Table table;
    private TableServer tableServer;
    private TrucoPlayer player;
    private int value;
    
    /**
     * Crea una nueva instancia de TableEvent para el
     * envío de una seña.
     * @param table		mesa asociada
     * @param player	jugador asociado
     * @param value		valor asociado
     */
    public TableEvent
    (
    int event, Table table, TrucoPlayer player, int value
    ) {
        // verificaciones ("comentarizado" para el ejemplo)
        Util.verifParam(table != null, "Parámetro 'table' nulo");
        
        this.event = event;
        this.table = table;
        this.value = value;
        this.player = player;
    }
    public TableEvent
    (
    int event, TableServer table, TrucoPlayer player, int value
    ) {
        // verificaciones ("comentarizado" para el ejemplo)
        Util.verifParam(table != null, "Parámetro 'table' nulo");
        
        this.event = event;
        this.tableServer = table;
        this.value = value;
        this.player = player;
    }
    
    /** Retorna el jugador asociado */
    public TrucoPlayer getPlayer() { return player; }
    
    /** Retorna el valor asociado */
    public int getValue() { return value; }
    
    /** Retorna la mesa asociada */
    public Table getTable() { return table; }
    
    /**
     * Retorna una identificación que contiene información sobre
     * los parámetros activos de este TableEvent y el evento
     * que se generó.
     */
    public int getID() {
        int p, v;
        
        p = player == null ? 0 : PLAYER_MASK;
        v = value == -1 ? 0 : VALUE_MASK;
        
        return p | v | event;
    }
    
    /**
     * Retorna verdadero el ID pasado indica que el campo
     * <code>player</code> está especificado.
     * @param id		ID de un TableEvent
     */
    public static boolean isValueSpecified(int id) {
        return (id & VALUE_MASK) != 0;
    }
    
    /**
     * Retorna verdadero si el campo ID pasado indica que el
     * campo <code>value</code> está especificado.
     * @param id		ID de un TableEvent
     */
    public static boolean isPlayerSpecified(int id) {
        return (id & PLAYER_MASK) != 0;
    }
    
    /**
     * Obtiene el evento especificado en un ID de un TableEvent
     * @param id	ID de un TableEvent
     */
    public static int getEventFromID(int id) {
        return id & ~(PLAYER_MASK | VALUE_MASK);
    }
    private Document xmlCreatePlayerSitRequest() {//del cliente al servidor para
        System.err.println("//////////////////////////////////////////Dentro de xmlCreatePlayerSitRequest name del player es nulo " + (player==null) );
        
        Element ROOT=new Element("PlayerSitRequest");
        Element TABLE=new Element("Table");
        TABLE.setAttribute("id",String.valueOf((getTable()).getTableNumber()));
        Element POS=new Element("Pos");
        POS.setAttribute("pos",String.valueOf(getValue()));
        
        Element PLAYER=new Element("Player");
        PLAYER.setAttribute("name",player.getName());
        
        ROOT.addContent(POS);
        ROOT.addContent(PLAYER);
        ROOT.addContent(TABLE);
        Document doc=new Document(ROOT);
        return doc;
    }
    public TableServer getTableServer() {
        return tableServer;
    }
    private Document xmlCreatePlayerSit() {//Cuando el el player ya se sento
        Element ROOT=new Element("PlayerSit");
        Element TABLE=new Element("Table");
        TABLE.setAttribute("id",String.valueOf((getTableServer()).getTableNumber()));
        Element POS=new Element("Pos");
        POS.setAttribute("pos",String.valueOf(getValue()));
        
        Element PLAYER=new Element("Player");
        PLAYER.setAttribute("name",player.getName());
        
        ROOT.addContent(POS);
        ROOT.addContent(PLAYER);
        ROOT.addContent(TABLE);
        Document doc=new Document(ROOT);
        return doc;
    }
    private Document xmlCreateGameStartRequest() {
        Element ROOT=new Element("GameStartRequest");
        Element TABLE=new Element("Table");
        TABLE.setAttribute("id",String.valueOf((getTable()).getTableNumber()));
        ROOT.addContent(TABLE);
        Document doc=new Document(ROOT);
        
        return doc;
    }
    public Document xmlCreateGameStarted(String team1, String team2) {// PAULO
        Element ROOT=new Element("GameStarted");
        
        Element NOMBRE1=new Element("Equipo1");
        NOMBRE1.setAttribute("equipo1",team1);
        
        Element NOMBRE2=new Element("Equipo2");
        NOMBRE2.setAttribute("equipo2",team2);
        
        Element TABLE=new Element("Table");
        if (getTable() == null) {
            TABLE.setAttribute("id",String.valueOf((getTableServer()).getTableNumber()));
        } else {
            TABLE.setAttribute("id",String.valueOf((getTable()).getTableNumber()));
        }
        ROOT.addContent(TABLE);
        ROOT.addContent(NOMBRE1);
        ROOT.addContent(NOMBRE2);
        
        Document doc=new Document(ROOT);
        
        return doc;
    }
    public  Document toXml() {
        Document doc=null;
        if (event ==EVENT_playerSitRequest) {
            doc=xmlCreatePlayerSitRequest();
        }
        if(event == EVENT_playerSit) {
            doc=xmlCreatePlayerSit();
        }
        if(event ==EVENT_gameStartRequest) {
            doc=xmlCreateGameStartRequest();
        }
            return doc;
        }
        
        /**
         * Ejemplo de uso. La máscara juega el rol de tipo de
         * evento y las 'isXSpecified' el rol de determinadores
         * de existencia o inexistencia de un cierto parámetro
         * para un cierto tipo (máscara).
         * El parámetro Table siempre está especificado.
         */
  /*  public static void main(String[] args) {
        TableEvent[] tEvents;
        int id;
   
        // crea 4 TableEvents con todas las
        // combinaciones de parámetros activos
        tEvents = new TableEvent[] {
            new TableEvent(TableEvent.EVENT_gameStarted, null, null, -1),
            new TableEvent(TableEvent.EVENT_gameFinished, null, null, 1),
            new TableEvent(TableEvent.EVENT_playerSit, null, null, -1),
            new TableEvent(TableEvent.EVENT_tableLocked, null, null, 2)
        };
   
        // imprime la existencia o no de los
        // parámetros de cada TableEvent
        for (int i = 0; i < 4; i++) {
            // obtiene la másca del 'i'-ésimo TableEvent
            id = tEvents[i].getID();
   
            // imprime la existencia o inexistencia
            // de c/u de los parámetros
            System.out.println("TableEvent numero " + i + ":");
            System.out.println("- Evento: " + TableEvent.getEventFromID(id));
            System.out.println("- Especifica 'player': " + TableEvent.isPlayerSpecified(id));
            System.out.println("- Especifica 'value': " + TableEvent.isValueSpecified(id));
        }
    }*/
    }