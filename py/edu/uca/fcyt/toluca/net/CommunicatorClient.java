/*
 * XmlPackageSessionTest.java
 *
 * Created on 25 de marzo de 2003, 07:03 PM
 */

package py.edu.uca.fcyt.toluca.net;

import py.edu.uca.fcyt.toluca.*;
import py.edu.uca.fcyt.toluca.table.*;
import py.edu.uca.fcyt.toluca.game.*;
import py.edu.uca.fcyt.game.*;

import py.edu.uca.fcyt.toluca.event.*;

import py.edu.uca.fcyt.net.*;

import java.util.*;
import org.jdom.*;
import java.net.*;
import java.io.*;
import org.jdom.output.XMLOutputter;
/**
 *
 * @author  PABLO JAVIER
 */
public class CommunicatorClient extends Communicator {
    
    RoomClient pieza;
    TrucoPlayer mi_jugador=null;    // Este es el que hay que usar como referencia al jugador representado!!!
    /** Creates a new instance of XmlPackageSessionTest */
    ChatPanelContainer Chatpanel;
    
    /** Holds value of property player. */
    private Player player;
    
    public CommunicatorClient(RoomClient pieza) {
        this.pieza = pieza;
        int retinit = init();
        System.out.println(getInitErrorMessage(retinit));
        pieza.addRoomListener(this);
    }
    
    public CommunicatorClient() {
        super();
        int retinit = init();
        System.out.println(getInitErrorMessage(retinit));
    }
    public String getInitErrorMessage(int errcode) {
        return "Sin errores";
    }
    
    public int init() {
        int ret = -1;
        try {
            //setSocket(new Socket("interno.roshka.com.py", 6767));
            setSocket(new Socket("localhost", 6767));
            ret = XmlPackagesSession.XML_PACKAGE_SESSION_INIT_OK;
        } catch (UnknownHostException e) {
            ret = -5;
        } catch (IOException e) {
            ret = -4;
        }
        return ret;
    }
    
    public void receiveXmlPackage(Element xmlPackage) {
        System.out.println("Vino un paquete: " + xmlPackage.getName());
        Document doc=new Document(xmlPackage);
        cabecera(doc);
        //chat.agregar(xmlPackage.getChild("Usuario").getText() + ": " + xmlPackage.getChild("Mensaje").getText());
        
    }
    
    public void sendXmlPackage(String option,RoomEvent te) {
        
    }
    
    public void receiveXmlPackageWithParsingError(String rawXmlPackage) {
        System.out.println("El XML tiene errores");
    }
    
    public void loginRequested(RoomEvent te){
        //peticion de loginc
        Document doc;
        doc = super.xmlCreateLogin(te);
        super.sendXmlPackage(doc);
    }
    public void chatMessageRequested(ChatPanelContainer chatPanelContainer, Player jug, String htmlMessage){
        Document doc;
        //this.ChatPanel=panel;
        doc=super.xmlCreateChatMsg(jug,htmlMessage);
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
                //Chatpanel.showChatMessage(user,message);
                System.out.println("Player: "+user);
                System.out.println("Mensaje: "+message);
                pieza.showChatMessage(new Player(user, 0), message);
            }
        }
    }
    
    public void tableCreated(RoomEvent re) {
        // Problemas de diseno a resolver en TolucaV2
    }
    
    private void cabecera(Document doc) {//saca la cabeza del paquete y envia el paquete al lector correspondiente
        
        List children = doc.getContent();
        Iterator iterator = children.iterator();
        Object child = iterator.next();
        Element element = (Element) child;
        String aux=element.getName();
        //System.out.println(aux);
        
        
        if(aux.compareTo("ChatMsg")==0) {
            System.out.println("Llego un mensaje de chat, soy " + player.getName());
            xmlReadChatMsg(child);
        }
        
        if(aux.compareTo("LoginOk")==0) {
            System.out.println("LLego un mensaje de LoginOk");
            xmlReadLoginOk(child);
        }
        if(aux.compareTo("UserJoined")==0){
            System.out.println("Llego un mensaje de UserJoined");
            xmlReadUserJoined(child);
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
        
        if(aux.compareTo("TableCreated")==0) {
            xmlReadTableCreated(child);
        }
    }
    
    public void xmlReadUserJoined(Object o){
        String aux;
        
        if (o instanceof Element) {
            Element element = (Element) o;
            aux=element.getName();
            
            if(aux.compareTo("Player")==0) {
                String jugname=element.getAttributeValue("name");
                int rating=Integer.parseInt(element.getAttributeValue("rating"));
                if(mi_jugador== null) {
                    mi_jugador= new TrucoPlayer(jugname,rating);
                    System.out.println("Jugador nuevo name="+mi_jugador.getName()+"rating"+mi_jugador.getRating());
                    pieza.loginCompleted(mi_jugador);
                    setPlayer(mi_jugador);
                }
                else {
                    pieza.addPlayer(new Player(jugname, rating));
                }
                
            }
            List children = element.getContent();
            Iterator iterator = children.iterator();
            while (iterator.hasNext()) {
                Object child = iterator.next();
                xmlReadUserJoined(child);
            }
        }
    }
    
    public void xmlReadTableCreated(Object o){
        String aux;
        
        if (o instanceof Element) {
            Element element = (Element) o;
            aux=element.getName();
            
            if(aux.equals("Table")) {
                String tableid=element.getAttributeValue("id");
                Table t = new Table(getPlayer(), false);
                t.setTableNumber(Integer.parseInt(tableid));
                t.addTableListener(this);
                getTables().put(tableid, t);
                
                /*try {
                    Table tabela = (Table)getTables().get(tableid);
                    tabela.yapiro();
                } catch (java.lang.NullPointerException e) {
                 asdfasdf   
                } */
            }
            List children = element.getContent();
            Iterator iterator = children.iterator();
            while (iterator.hasNext()) {
                Object child = iterator.next();
                xmlReadTableCreated(child);
            }
        }
    }
    public void xmlReadLoginOk(Object o) {
        Players.removeAllElements();
        Mesas.removeAllElements();
        current=0;
        xmlReadLoginAlg(o);
    }
    
    
    
    
    private  void xmlReadLoginAlg(Object o) {//
        String aux;
        
        if (o instanceof Element) {
            Element element = (Element) o;
            aux=element.getName();
            System.out.println("Estamos dentro del xmlReadLoginAlg");
            if(aux.compareTo("Player")==0) {
                String jugname=element.getAttributeValue("name");
                int rating=Integer.parseInt(element.getAttributeValue("rating"));
                Player jug1=new Player(jugname,rating);
                Players.add(jug1);
                System.out.println("Player"+ jugname);
            }
            if(aux.compareTo("Playert")==0) {
                String jugname=element.getAttributeValue("name");
                //System.out.println("Playert:  "+jugname);
                Player jugaux=new Player(jugname,0);
                ((Table)(Mesas.get(current))).addPlayer(jugaux);
                //mesa.addPlayer(jugaux);
            }
            if(aux.compareTo("Table")==0) {
/*                Table mesa=new Table();
                int number=Integer.parseInt(element.getAttributeValue("number"));
                mesa.setTableNumber(number);
                current=Mesas.size();
                Mesas.add(mesa); */
            }
            List children = element.getContent();
            Iterator iterator = children.iterator();
            while (iterator.hasNext()) {
                Object child = iterator.next();
                xmlReadLoginAlg(child);
            }
            if(aux.compareTo("LoginOk")==0) {
                
                Player jug;
                Table mesa1;
                for (Enumeration e = Players.elements() ; e.hasMoreElements() ;) {
                    jug=(Player)e.nextElement();
                    System.out.println("================= RECIBI Player: "+jug.getName()+" Rating: "+jug.getRating()+"\n");
                    
                    pieza.addPlayer(jug);
                }
                for (Enumeration e = Mesas.elements() ; e.hasMoreElements() ;) {
                    mesa1=(Table)e.nextElement();
                    System.out.println("==================  RECIBI Table number: "+mesa1.getTableNumber()+"\n");
                    Vector jugadores=(Vector)mesa1.getPlayers();
                    /*for(Enumeration e2=jugadores.elements();e2.hasMoreElements();) {
                        jug=(Player)e2.nextElement();
                        System.out.println("======================RECIBI Player: "+jug.getName()+" Rating: "+jug.getRating()+"\n");
                    }*/
                    
                    pieza.addTable(mesa1);
                    
                }
            }//el if cuando termina de leer el paquete
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
        
    }
    
    /** Getter for property player.
     * @return Value of property player.
     *
     */
    public TrucoPlayer getPlayer() {
        return this.mi_jugador;
    }
    
    /** Setter for property player.
     * @param player New value of property player.
     *
     */
    public void setPlayer(TrucoPlayer player) {
        this.player = player;
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
        //peticion de createTable
        Document doc;
        doc = super.xmlCreateTableRequested(ev);
        super.sendXmlPackage(doc);
    }
    
    public void gameFinished(TableEvent event) {
    }
    
    public void gameStarted(TableEvent event) {
    }
    
    public void playerKickRequest(TableEvent event) {
    }
    
    public void playerKicked(TableEvent event) {
    }
    
    public void playerSit(TableEvent event) {
    }
    
    public void playerSitRequest(TableEvent event) {
    }
    
    public void playerStandRequest(TableEvent event) {
    }
    
    public void playerStanded(TableEvent event) {
    }
    
    public void showPlayed(TableEvent event) {
    }
    
    public void signSendRequest(TableEvent event) {
    }
    
    public void signSent(TableEvent event) {
    }
    
        /*public static void main(String[] args)
        {
                TrucoCard []cards=new TrucoCard[3];
                cards[0]=new TrucoCard(1,1);
                cards[1]=new TrucoCard(1,4);
                cards[2]=new TrucoCard(1,7);
         
                TrucoEvent dani = new TrucoEvent(new TrucoGame(2),5,new TrucoPlayer("Cricco"),TrucoEvent.CANTO_ENVIDO,28);
         
                Document doc=dani.toXml();
                CommunicatorClient cc=new CommunicatorClient();
         
                try {
         
                XMLOutputter serializer = new XMLOutputter("  ", true);
         
                serializer.output(doc, System.out);
         
                        }
                catch (IOException e) {
                System.err.println(e);
                }
                        cc.cabecera(doc);
        }*/
    
}
