
package py.edu.uca.fcyt.toluca.net;

import py.edu.uca.fcyt.toluca.*;
import py.edu.uca.fcyt.toluca.table.*;
import py.edu.uca.fcyt.toluca.game.*;
import py.edu.uca.fcyt.toluca.event.*;
import py.edu.uca.fcyt.game.*;


import py.edu.uca.fcyt.net.*;

import java.util.*;
import org.jdom.*;

/**
 *
 * @author  PABLO JAVIER
 */
public class CommunicatorServer 
extends Communicator {
    
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
    public void playerJoined(Player player) {
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
                pieza.sendChatMessage(new Player(user,0),message);
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
		if(aux.compareTo("SendCards")==0)
		{
			super.xmlreadSendCards(child);
		}
		if(aux.compareTo("Canto")==0)
		{	
			super.xmlReadCanto(child);
		}
		if(aux.compareTo("Cardsend")==0)
		{
			super.xmlReadCard(child);
		}
		if(aux.compareTo("CantarTanto")==0)
		{
			super.xmlReadCantarTanto(child);
		}
		if(aux.compareTo("Turno")==0)
		{
			super.xmlReadTurno(child);
		}
		if(aux.compareTo("TerminalMessage")==0)
		{
			
			super.xmlReadTerminalMessage(child);
		}
        
    }
    public void chatMessageSent(Player jug,String message) {
        System.out.println("Enviando msg de chat del jug: " + jug.getName());
        Document doc;
        doc=super.xmlCreateChatMsg(jug,message);
        super.sendXmlPackage(doc);
//        pieza.sendChatMessage(jug, message);
    }
    
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
    public void chatMessageRequested(ChatPanelContainer chatPanelContainer, Player player, String htmlMessage) {
    }

    public void createTableRequested(RoomEvent ev) {
    }
    
    /** <p>
     * Does ...
     * </p><p>
     *
     * </p>
     *
     */
    public void gameStartRequested() {
    }

    public void chatMessageSent(ChatPanelContainer cpc, Player player, String htmlMessage) {
        System.out.println("Enviando msg de chat del jug: " + player.getName());
        Document doc;
        doc=super.xmlCreateChatMsg(player,htmlMessage);
        super.sendXmlPackage(doc);
    }

    
    
}

