
package py.edu.uca.fcyt.toluca.net;

import py.edu.uca.fcyt.toluca.*;
import py.edu.uca.fcyt.toluca.table.*;
import py.edu.uca.fcyt.toluca.game.*;
import py.edu.uca.fcyt.toluca.event.*;
import py.edu.uca.fcyt.game.*;

import org.jdom.output.XMLOutputter;
import py.edu.uca.fcyt.net.*;

import java.util.*;
import java.io.*;
import org.jdom.*;

/**
 *
 * @author  PABLO JAVIER
 */
public class CommunicatorServer
extends Communicator {
       public void showPlayed(TableEvent te) {
           
       }
       public void signSent(TableEvent te) {
           
       }
       public void playerSit(TableEvent te) {
           
       }
       public void playerSitRequest(TableEvent te) {
           
       }
       public void playerKicked(TableEvent te) {
           
       }
       public void playerKickRequest(TableEvent te) {
           
       }
       public void playerStanded(TableEvent te) {
           
       }
       public void playerStandRequest(TableEvent te) {
           
       }
       public void gameFinished(TableEvent te) {
           
       }
       
       public void signSendRequest(TableEvent te) {
           
       }
       public void gameStarted(TableEvent te) {
           
       }
    /** Creates a new instance of ChatSessionServer */
    /*public CommunicatorServer(RoomSer pieza) {
        super(pieza);
    }*/
    
    
    public CommunicatorServer() {
        super();
    }
    public String getInitErrorMessage(int errcode) {
        return "Sin errores";
    }
    
    public int init() {
        return XmlPackagesSession.XML_PACKAGE_SESSION_INIT_OK;
    }
    private int i = 0;
    
    public void receiveXmlPackage(Element xmlPackage) {
        System.out.println("Vino un paquete: " + xmlPackage.getName());
        Document doc=new Document(xmlPackage);
        
        //		doc.addContent(xmlPackage);
       try {
            
            XMLOutputter serializer = new XMLOutputter("  ", true);
            
            serializer.output(doc, System.out);
            
        }
        catch (IOException e) {
            System.err.println(e);
        }
        System.err.println("//////////////////////////////////////////");
        cabecera(doc);
        //loginComplete();
    }
    
    public void receiveXmlPackageWithParsingError(String rawXmlPackage) {
        System.out.println("Error en el parsing del XML");
    }
    
    ConnectionManager trucoServer;
    
    private RoomServer pieza;
    
    public void setTrucoServer(ConnectionManager trucoServer) {
        this.trucoServer = trucoServer;
    }
    public void loginFailed(RoomEvent te){
        
    }
    /**
     *  Se dispara cuando el usuario de une a un room.
     *  Se guarda la referencia al TrucoPlayer
     */
    public void playerJoined(TrucoPlayer player) {
        System.out.println("COMMSRV -> Player Joined DE: " + player.getName());
        this.player = player;
        Document doc=super.xmlCreateUserJoined(player);
        super.sendXmlPackage(doc);
    }
    public void loginCompleted(RoomEvent re){
        //RoomEvent te;
        
/*
        RoomEvent te=new RoomEvent();
        Vector jugadores=new Vector();
        Vector mesas=new Vector();
        Player jug1=new Player("Daniel",10);
        Player jug2=new Player("José",20);
 
        Table mesa1=new Table();
        Table mesa2=new Table();
        jugadores.add(jug1);
        jugadores.add(jug2);
        mesa1.setTableNumber(2);
 
        mesa1.addPlayer(jug1);
        mesa1.addPlayer(jug2);
        mesa2.setTableNumber(10);
        mesa2.addPlayer(jug1);
        mesa2.addPlayer(jug2);
        mesas.add(mesa1);
        mesas.add(mesa2);
        te.setTabless(mesas);
        te.setPlayerss(jugadores);
 
        te.setUser("AAdaniAA");
        te.setPassword("AAcriccoAAA");
 *
 **/
        
        Document doc;
        //re.setPlayerss(pieza.getPlayerss());
        re.setPlayerss(pieza.getVPlayers());
        re.setTabless(pieza.getVTables());
        
        doc = super.xmlCreateLoginOk(re);
        super.sendXmlPackage(doc);
    }
    
    
    public  void xmlReadChatMsg(Object o) {
        String aux;
        if (o instanceof Element) {
            Element element = (Element) o;
            aux=element.getName();
            if(aux.compareTo("Player")==0) {
                //System.out.println("PLAYER:"+element.getText());
                user=element.getAttributeValue("name");
            }
            if(aux.compareTo("Msg")==0) {
                //System.out.println("MESSAGE:"+element.getText());
                message=element.getText();
            }
            List children = element.getContent();
            Iterator iterator = children.iterator();
            while (iterator.hasNext()) {
                Object child = iterator.next();
                xmlReadChatMsg(child);
            }
            if(aux.compareTo("ChatMsg")==0) {
                //pieza.fireChatSent(user,message);
                System.out.println("Player: "+user);
                System.out.println("Mensaje: "+message);
                System.out.println("Enviando Respuesta");
                //                chatMessageSent(new Player(user,0),message);
                // Aca vemos para quién puta es el mensaje
                String origen = element.getAttributeValue("origin");
                System.out.println("El origen es: " + origen);
                if (origen.equalsIgnoreCase("room")) {
                    pieza.sendChatMessage(new TrucoPlayer(user, 0), message);
                } else {
                    TableServer t = (TableServer)getTables().get(origen);
                    if (t != null) {
                        t.sendChatMessage(new TrucoPlayer(user,0), message);
                    } else {
                        System.out.println("NO PUDIMOS ENTREGAR EL CHAT!!! !@@!#$#@@Q");
                    }
                }
            }
        }
    }
    public  void cabecera(Document doc) {//saca la cabeza del paquete y envia el paquete al lector correspondiente
        
        List children = doc.getContent();
        Iterator iterator = children.iterator();
        Object child = iterator.next();
        Element element = (Element) child;
        String aux=element.getName();
        //System.out.println(aux);
        
        
        if(aux.compareTo("Login")==0) {
            xmlReadLogin(child);
        }
        if(aux.compareTo("ChatMsg")==0) {
            xmlReadChatMsg(child);
        }
        if(aux.compareTo("SendCards")==0) {
            super.xmlreadSendCards(child);
        }
        if(aux.compareTo("Canto")==0) {
            super.xmlReadCanto(child);
        }
        if(aux.compareTo("Cardsend")==0) {
            super.xmlReadCard(child);
        }
        if(aux.compareTo("CantarTanto")==0) {
            super.xmlReadCantarTanto(child);
        }
        if(aux.compareTo("Turno")==0) {
            super.xmlReadTurno(child);
        }
        if(aux.compareTo("TerminalMessage")==0) {
            
            //super.xmlReadTerminalMessage(child);
        }
        if(aux.compareTo("CreateTable")==0) {
            xmlReadCreateTable(child);
        }
		 if(aux.compareTo("TableJoinRequest")==0) {
			 System.out.println("Dentro de cabecera para ejecutar el metodo\n");
            xmlReadTableJoinRequest(child);
        }
        
    }
/*    public void chatMessageSent(TrucoPlayer jug,String message) {
        System.out.println("Enviando msg de chat del jug: " + jug.getName());
        Document doc;
        doc=super.xmlCreateChatMsg(jug,message);
        super.sendXmlPackage(doc);
        //        pieza.sendChatMessage(jug, message);
    }
*/    
    public void setRoom(RoomServer pieza) {
        this.pieza = pieza;
        pieza.addRoomListener(this);
    }
    
    
    public RoomServer getRoom() {
        return this.pieza ;
    }
    
    public void xmlReadLogin(Object o) {
        String aux;
        if (o instanceof Element) {
            Element element = (Element) o;
            aux=element.getName();
            if(aux.compareTo("Player")==0) {
                //System.out.println("PLAYER:"+element.getText());
                user=element.getText();
            }
            if(aux.compareTo("Password")==0) {
                //System.out.println("PASSWORD:"+element.getText());
                password=element.getText();
            }
            List children = element.getContent();
            Iterator iterator = children.iterator();
            while (iterator.hasNext()) {
                Object child = iterator.next();
                xmlReadLogin(child);
            }
            if(aux.compareTo("Login")==0) {
                //Hace la autenticacion contra la base de datos
                //DbOoperation.authenticatePlayer(user,password);
                System.err.println("Dentro del Communicator Client, recibi un login request de: ");
                System.out.println("Username: "+user);
                System.out.println("Password: "+password);
                
                try {
                    pieza.login(user, password, this);
                } catch (py.edu.uca.fcyt.toluca.LoginFailedException lfe){
                    System.err.println("hubo un error en el login");
                    System.err.println("enviar uno paquete indicando el error");
                    //No se pudo logear
                }
            }
        }
    }
    
    /** <p>
     * Does ...
     * </p><p>
     *
     * @param chatPanelContainer ...
     * </p><p>
     * @param player ...
     * </p><p>
     * @param htmlMessage ...
     * </p><p>
     *
     * </p>
     *
     */
    public void chatMessageRequested(ChatPanelContainer chatPanelContainer, TrucoPlayer player, String htmlMessage) {
    }
    
    //   public void createTableRequested(RoomEvent ev) { }
    
    /** <p>
     * Does ...
     * </p><p>
     *
     * </p>
     *
     */
    public void gameStartRequested() {
    }
    
    public void chatMessageSent(ChatPanelContainer cpc, TrucoPlayer player, String htmlMessage) {
        System.out.println("Enviando msg de chat del jug: " + player.getName());
        Document doc;
        doc=super.xmlCreateChatMsg(cpc, player,htmlMessage);
        super.sendXmlPackage(doc);
    }
    
    /** <p>
     * Does ...
     * </p><p>
     *
     * @param ev ...
     * </p><p>
     *
     * </p>
     *
     */
    public void createTableRequested(RoomEvent ev) {
    }
    
    public void xmlReadCreateTable(Object o) {
        String aux;
        if (o instanceof Element) {
            Element element = (Element) o;
            aux=element.getName();
            if(aux.compareTo("Player")==0) {
                //System.out.println("PLAYER:"+element.getText());
                user=element.getAttributeValue("id");
            }
            List children = element.getContent();
            Iterator iterator = children.iterator();
            while (iterator.hasNext()) {
                Object child = iterator.next();
                xmlReadCreateTable(child);
            }
            if(aux.compareTo("CreateTable")==0) {
                System.out.println("Player: "+user);
                //pieza.createTable(new TrucoPlayer(user));
                pieza.createTable(player);
            }
        }
    }
    
    /** <p>
     * Does ...
     * </p><p>
     *
     * </p><p>
     *
     * @param ev ...
     * </p>
     */
    public void tableCreated(RoomEvent ev) {
        System.out.println("No hay nada: " + ev.getTabless().toArray().length);
        TableServer ts = (TableServer)((ev.getTabless().toArray())[0]);
        ts.addTableServerListener(this);
        // Agregamos al Hash de Tablas
        System.out.println("getTables nulo: " + (getTables() == null));
        getTables().put(String.valueOf(ev.getTableNumber()), ts);
        System.out.println("Se creo una tabla. soy: " + getClass().getName());
        Document doc;
        doc = xmlCreateTableCreated(ev);
        System.out.println("Antes de mandar el XML en el CREATE TABLE DEL SERVA");
        super.sendXmlPackage(doc);
    }
    
    public void xmlReadTableJoinRequest(Object o)
	{
		String aux;
		System.out.println("Leyendo el paquete");
        if (o instanceof Element) {
            Element element = (Element) o;
            aux=element.getName();
            
            if(aux.compareTo("Table")==0) {
                //System.out.println("MESSAGE:"+element.getText());
                String tableid=element.getAttributeValue("id");
				System.out.println("El table id es"+tableid);
			}
            List children = element.getContent();
            Iterator iterator = children.iterator();
            while (iterator.hasNext()) {
                Object child = iterator.next();
                xmlReadChatMsg(child);
            }
            if(aux.compareTo("TableJoinRequest")==0) {
                //Chatpanel.showChatMessage(user,message);
				
			 }
        }
	}
    public Document xmlCreateTableCreated(RoomEvent te) {
        System.out.println("Cricco quiere ver su pakochi");
        Element ROOT= new Element("TableCreated");
        
        Element TABLE = new Element("Table");
        TABLE.setAttribute("id",String.valueOf(te.getTableNumber()));
        
        ROOT.addContent(TABLE);
        
        Document doc = new Document(ROOT);
        return doc;
    }
}
