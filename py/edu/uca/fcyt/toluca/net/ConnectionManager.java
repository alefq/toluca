/*
 * ChatServer.java
 *
 * Created on 26 de marzo de 2003, 08:05 PM
 */

package py.edu.uca.fcyt.toluca.net;

import java.util.Vector;

import py.edu.uca.fcyt.net.XmlPackagesServer;
import py.edu.uca.fcyt.net.XmlPackagesServerListener;
import py.edu.uca.fcyt.net.XmlPackagesSession;
import py.edu.uca.fcyt.toluca.RoomServer;

/**
 *
 * @author  PABLO JAVIER
 */
public class ConnectionManager implements XmlPackagesServerListener {
    
    XmlPackagesServer server;
    /** Puerto donde escucha el conn mannager */
    public static int SERVER_PORT = 6767;
    /** Fully qualified name de la clase*/
    public static final String SERVER_FQN = "py.edu.uca.fcyt.toluca.net.CommunicatorServer";
    
    /** Holds value of property roomserver. */
    private RoomServer roomserver;
    
    /** Holds value of property vecSesiones. */
    private java.util.Vector vecSesiones;
    
    /** Creates a new instance of ChatServer */
    public ConnectionManager(RoomServer rs) {
        try {
            System.err.println("Soy un Connection mannager y ya fui instanciado");
            setRoomserver(rs);            
            vecSesiones = new Vector();
            server = new XmlPackagesServer( ConnectionManager.SERVER_PORT , ConnectionManager.SERVER_FQN);
            server.addXmlPackagesServerListener(this);
            new Thread(server).start();
            
        } catch (java.io.IOException e) {
            System.out.println("I/O exception in server");
            e.printStackTrace();
        } catch (java.lang.ClassNotFoundException e) {
            System.out.println("Class not found in server: " + e.getMessage());
            e.printStackTrace();
        }
        
    }
    
    public static void main(String[] args) {
//        new ConnectionManager();
        // Application will loop forever here since it is a daemon
    }
    
    public void SessionStarted(XmlPackagesSession xps) {
        System.out.println("Se creo una sesión!");
        vecSesiones.add(xps);
        CommunicatorServer cts = (CommunicatorServer)xps;
        cts.setRoom(roomserver);
        cts.setTrucoServer(this);
    }
    
    /** Getter for property roomserver.
     * @return Value of property roomserver.
     */
    public RoomServer getRoomserver() {
        return this.roomserver;
    }
    
    /** Setter for property roomserver.
     * @param roomserver New value of property roomserver.
     */
    public void setRoomserver(RoomServer roomserver) {
        this.roomserver = roomserver;
    }
    
    /** Getter for property vecSesiones.
     * @return Value of property vecSesiones.
     */
    public java.util.Vector getVecSesiones() {
        return this.vecSesiones;
    }
    
    /** Setter for property vecSesiones.
     * @param vecSesiones New value of property vecSesiones.
     */
    public void setVecSesiones(java.util.Vector vecSesiones) {
        this.vecSesiones = vecSesiones;
    }
    
    /*public void chatMessageArrived(ChatSessionServer css, String usuario, String mensaje) {
        py.com.roshka.test.xmlpackages.ChatMessage cmess = new py.com.roshka.test.xmlpackages.ChatMessage(usuario, mensaje);
        Iterator iter = vecSesiones.listIterator();
        while(iter.hasNext()) {
            ChatSessionServer csstmp = (ChatSessionServer)iter.next();
            //if (csstmp != css)
                csstmp.sendXmlPackage(cmess.getTheXml());
        }
     
     
    }*/
}

