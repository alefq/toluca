/*
 * XmlPackagesSession.java
 *
 * Created on 3 de noviembre de 2002, 10:35
 */

package py.edu.uca.fcyt.net;

//import py.edu.uca.fcyt.toluca.XMLParser;

import java.net.Socket;

import java.io.*;
import org.jdom.*;

/**
 *
 * @author  psanta
 */
public abstract class XmlPackagesSession implements Runnable {
    /** Constants */
    public static final int XML_PACKAGE_DELIMITER = '{';
    public static final int XML_PACKAGE_SESSION_INIT_OK = 1;
    
    private static final long DEFAULT_SLEEP_TIME = 500;
    
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private boolean live;
    private boolean use_package_delimiter;
    
    /** Creates a new instance of XmlPackagesSession */
    public XmlPackagesSession() {
        socket = null;
        live = true;
        use_package_delimiter = true;
    }
    
    public XmlPackagesSession(boolean use_package_delimiter) {
        this();
        this.use_package_delimiter = use_package_delimiter;
    }
    
    public void run() {
        String rawPacket = "";
        // Loop until live = false
        try {
            while (live) {
                if (in.ready()) {
                    if (use_package_delimiter) {
                        int c = in.read();
                        //System.out.println("Debug: " + c);
                        if (c != XML_PACKAGE_DELIMITER) {
                            rawPacket += (char)c;
                        } else {
                            //System.out.println("Got an XML package. I am going to instantiate an XMLParser object...");
                            //XMLParser xp = new XMLParser(rawPacket.trim());
                            //System.out.println("I am going to parse the string passed to the XMLParser...");
                            try {
                                Element eReceived = py.edu.uca.fcyt.xml.Parser.getElement(rawPacket.trim());
                                //System.out.println("Before sending the parsed packed to receiveXmlPackage method: \n" + rawPacket);
                                receiveXmlPackage(eReceived);
                            } catch (JDOMException e) {
                                e.printStackTrace();
                                System.err.println("Coult not parse XML package, sending it to receiveXmlPackageWithParsingError method: " + e.getMessage());
                                receiveXmlPackageWithParsingError(rawPacket.trim());
                            }
                            rawPacket = "";
                        }
                    } else {
                        //System.out.println("Going to read something until in.ready() = false");
                        while (in.ready()) {
                            int c=in.read();
                            rawPacket+=(char)c;
                        }
                        //System.out.println("Read: " + rawPacket);
                        // Pre-parsing the XML
                        int ind;
                        do {
                            Element eReceived;
                            ind = rawPacket.indexOf("<?", 2);
                            if (ind == -1) {
                                try {
                                    eReceived = py.edu.uca.fcyt.xml.Parser.getElement(rawPacket.trim());
                                    receiveXmlPackage(eReceived);
                                } catch (JDOMException e) {
                                    System.err.println("Coult not parse XML package, sending it to receiveXmlPackageWithParsingError method");
                                    receiveXmlPackageWithParsingError(rawPacket.trim());
                                }
                            } else {
                                String subPacket = rawPacket.substring(0, ind).trim();
                                try {
                                    eReceived = py.edu.uca.fcyt.xml.Parser.getElement(subPacket);
                                    receiveXmlPackage(eReceived);
                                } catch (JDOMException e) {
                                    System.err.println("Coult not parse XML package, sending it to receiveXmlPackageWithParsingError method");
                                    receiveXmlPackageWithParsingError(subPacket);                                    
                                }
                                rawPacket = rawPacket.substring(ind);
                            }
                        } while (ind != -1);
                        rawPacket = "";
                    }
                } else {
                    try {
                        Thread.sleep(DEFAULT_SLEEP_TIME);
                    } catch (InterruptedException e) {
                        System.err.println("System interrupted. Cannot continue");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("I/O Exception while receiving package");
            e.printStackTrace();
        }
    }
    
    public void sendXmlPackage(Element xmlPackage) {
        System.out.println("Not yet implemented!");
    }
    
    public void sendXmlPackage(String xmlRaw) {
        //System.out.println("I am going to send to the socket: \n" + xmlRaw);
        out.print(xmlRaw);
        if (use_package_delimiter)
            out.write(XML_PACKAGE_DELIMITER);
        out.flush();
    }
    
    public abstract int init();
    public abstract String getInitErrorMessage(int errcode);
    
    
    public abstract void receiveXmlPackage(Element xmlPackage);
    public abstract void receiveXmlPackageWithParsingError(String rawXmlPackage);
    
    /** Getter for property socket.
     * @return Value of property socket.
     */
    public java.net.Socket getSocket() {
        return socket;
    }
    
    /** Setter for property socket.
     * @param socket New value of property socket.
     */
    public void setSocket(java.net.Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()));
        
    }
    
    public void close() throws IOException {
        socket.close();
    }
}

