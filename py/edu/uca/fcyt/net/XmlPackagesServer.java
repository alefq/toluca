/*
 * XmlPackagesServer.java
 *
 * Created on 3 de noviembre de 2002, 8:23
 */

package py.edu.uca.fcyt.net;

import java.net.*;
import java.io.*;

import java.util.Vector;
import java.util.Iterator;

/**
 *
 * @author  psanta
 */
public class XmlPackagesServer extends ServerSocket implements Runnable {
    
    private int clientsServed = 0;
    
    java.lang.Class theClass;
    
    public XmlPackagesServer(String className) throws IOException, ClassNotFoundException {
        super();
        
        theClass = Class.forName(className);
        XmlPackagesServerListenerVector = new Vector();
        
    }
    
    public XmlPackagesServer(int port, String className) throws IOException, ClassNotFoundException {
        super(port);
        theClass = Class.forName(className);
        XmlPackagesServerListenerVector = new Vector();
    }
    
    public XmlPackagesServer(int port, int backlog, String className) throws IOException, ClassNotFoundException {
        super(port, backlog);
        theClass = Class.forName(className);
        XmlPackagesServerListenerVector = new Vector();
    }
    
    public void run() {
        Socket socket;
        System.out.println("Server started listening on port: " + getLocalPort());
        try {
            // Loops forever accepting connections
            while(true) {
                socket = accept();
                System.out.println("Connection received");
                XmlPackagesSession xps = (XmlPackagesSession)theClass.newInstance();                
                xps.setSocket(socket);
                
                int retinit = xps.init();
                if (retinit == XmlPackagesSession.XML_PACKAGE_SESSION_INIT_OK) {
                    new Thread(xps).start();
                    fireSessionStarted(xps);
                } else {
                    System.out.println("Cannot execute init method of " + theClass.getName() +  " successfully: " + xps.getInitErrorMessage(retinit));
                }
            }
        } catch (IOException e) {
            System.out.println("I/O Exception in server");
            e.printStackTrace();
        } catch (InstantiationException e) {
            System.out.println("Could not instantiate class: " + theClass.getName());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.out.println("Illegal access exception in server");
            e.printStackTrace();
        }
        
    }
    
    /** Getter for property clientsServer.
     * @return Value of property clientsServer.
     */
    public int getClientsServed() {
        return clientsServed;
    }
    
    /** Setter for property clientsServer.
     * @param clientsServer New value of property clientsServer.
     */
    public void setClientsServed(int clientsServed) {
        this.clientsServed = clientsServed;
    }
    
    Vector XmlPackagesServerListenerVector;
    
    public void addXmlPackagesServerListener(XmlPackagesServerListener l) {
        XmlPackagesServerListenerVector.add(l);
    }
    
    private void fireSessionStarted(XmlPackagesSession xps) {
        Iterator iter = XmlPackagesServerListenerVector.listIterator();
        while(iter.hasNext()) {
            XmlPackagesServerListener ltmp = (XmlPackagesServerListener)iter.next();
            ltmp.SessionStarted(xps);
        }
    }
}

