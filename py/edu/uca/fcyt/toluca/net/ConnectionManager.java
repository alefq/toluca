package py.edu.uca.fcyt.toluca.net;

import java.util.ArrayList;


import org.apache.log4j.Logger;

import py.edu.uca.fcyt.net.XmlPackagesServer;
import py.edu.uca.fcyt.net.XmlPackagesServerListener;
import py.edu.uca.fcyt.net.XmlPackagesSession;
import py.edu.uca.fcyt.toluca.RoomServer;

/**
 * @author CYT UCA
 *
 * 
 */
public class ConnectionManager implements XmlPackagesServerListener {
	static Logger logger = Logger.getLogger(ConnectionManager.class);
    XmlPackagesServer server;
    /** Puerto donde escucha el conn mannager */
    public static int SERVER_PORT = 6767;
    /** Fully qualified name de la clase*/
    public static final String SERVER_FQN = "py.edu.uca.fcyt.toluca.net.CommunicatorServer";
    
    /** Holds value of property roomserver. */
    private RoomServer roomServer;
    
    /**Las sesesiones de XmlPackageSession*/
    private ArrayList vecSesiones;
    
    
    public ConnectionManager(RoomServer rs) {
        try {
            logger.debug("Soy un Connection mannager y ya fui instanciado");
            roomServer=rs;
            vecSesiones = new ArrayList();
            server = new XmlPackagesServer( ConnectionManager.SERVER_PORT , ConnectionManager.SERVER_FQN);
            server.addXmlPackagesServerListener(this);
            new Thread(server).start();
            
        } catch (java.io.IOException e) {
            logger.error("I/O exception in server");
            e.printStackTrace(System.out);
        } catch (java.lang.ClassNotFoundException e) {
            logger.error("Class not found in server: " + e.getMessage());
            e.printStackTrace(System.out);
        }
        
    }
    
    public static void main(String[] args) {

    }
    
    public void SessionStarted(XmlPackagesSession xps) {
        logger.info("Se creo una sesi�on");
        vecSesiones.add(xps);
        CommunicatorServer cts = (CommunicatorServer)xps;
        cts.setRoomServer(roomServer);
    }
    
    
   
    public ArrayList getVecSesiones() {
        return this.vecSesiones;
    }
    
     public void setVecSesiones(ArrayList vecSesiones) {
        this.vecSesiones = vecSesiones;
    }
    

}

