package py.edu.uca.fcyt.toluca;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;


public class Reader{
    
   /* public static void main(String[] args) {
        
        System.out.println("Imprimiendo");
        Document doc;
        TrucoEvent te=new TrucoEvent();
        doc=Communicator.xmlCreateLogin(te);
        
        cabecera(doc);
        
        
        
                Object child = cabecera(doc);
        xmlReadTableCreated(child);
         
    } */
    
    
    
    
    public static void cabecera(Document doc) {//saca la cabeza del paquete y envia el paquete al lector correspondiente
        
        List children = doc.getContent();
        Iterator iterator = children.iterator();
        Object child = iterator.next();
        Element element = (Element) child;
        String aux=element.getName();
        //System.out.println(aux);
        
        if(aux.compareTo("UserJoinedRoom")==0) {
            xmlReadUserJoinedRoom(child);
        }
        if(aux.compareTo("UserExitRoom")==0) {
            xmlReadExitRoom(child);
        }
        if(aux.compareTo("CreateTable")==0) {
            xmlReadCreateTable(child);
        }
        if(aux.compareTo("TableClosed")==0) {
            xmlReadTableClosed(child);
        }
        if(aux.compareTo("PlayerLeftRoom")==0) {
            xmlReadPlayerLeftRoom(child);
        }
        if(aux.compareTo("Login")==0) {
            xmlReadLogin(child);
        }
        if(aux.compareTo("ChatMsg")==0) {
            xmlReadChatMsg(child);
        }
        if(aux.compareTo("Error")==0) {
            xmlReadError(child);
        }
        if(aux.compareTo("TableCreated")==0) {
            xmlReadError(child);
        }
        
    }
    public static void xmlReadUserJoinedRoom(Object o){
        int playerid,roomid;
        String aux;
        if (o instanceof Element) {
            Element element = (Element) o;
            aux=element.getName();
            
            if(aux.compareTo("Player")==0) {
                System.out.println("PLAYER ID:"+element.getAttributeValue("id"));
            }
            if(aux.compareTo("Table")==0) {
                System.out.println("Table ID:"+element.getAttributeValue("id"));
            }
            
            List children = element.getContent();
            Iterator iterator = children.iterator();
            while (iterator.hasNext()) {
                Object child = iterator.next();
                xmlReadUserJoinedRoom(child);
            }
        }
    }
    
    public static void xmlReadExitRoom(Object o) {
        int playerid,roomid;
        String aux;
        if (o instanceof Element) {
            Element element = (Element) o;
            aux=element.getName();
            
            if(aux.compareTo("Player")==0) {
                System.out.println("PLAYER ID:"+element.getAttributeValue("id"));
            }
            if(aux.compareTo("Room")==0) {
                System.out.println("ROOM ID:"+element.getAttributeValue("id"));
            }
            
            List children = element.getContent();
            Iterator iterator = children.iterator();
            while (iterator.hasNext()) {
                Object child = iterator.next();
                xmlReadExitRoom(child);
            }
        }
        
    }
    public static void xmlReadCreateTable(Object o) {
        
        int playerid,roomid,players;
        String name;
        String aux;
        if (o instanceof Element) {
            Element element = (Element) o;
            aux=element.getName();
            if(aux.compareTo("Player")==0) {
                System.out.println("PLAYER ID:"+element.getAttributeValue("id"));
            }
            
            if(aux.compareTo("Players")==0) {
                System.out.println("CUANTOS PLAYERS:"+element.getText());
            }
            if(aux.compareTo("Name")==0) {
                System.out.println("NAME:"+element.getText());
            }
            
            List children = element.getContent();
            Iterator iterator = children.iterator();
            while (iterator.hasNext()) {
                Object child = iterator.next();
                xmlReadCreateTable(child);
            }
        }
    }
    
    public static void xmlReadTableClosed(Object o) {
        
        
        int tableid;
        String aux;
        if (o instanceof Element) {
            Element element = (Element) o;
            aux=element.getName();
            
            if(aux.compareTo("Table")==0) {
                System.out.println("ROOM ID:"+element.getAttributeValue("id"));
            }
            
            List children = element.getContent();
            Iterator iterator = children.iterator();
            while (iterator.hasNext()) {
                Object child = iterator.next();
                xmlReadTableClosed(child);
            }
        }
        
    }
    
    public static void xmlReadPlayerLeftRoom(Object o) {
        
        String aux;
        if (o instanceof Element) {
            Element element = (Element) o;
            aux=element.getName();
            
            if(aux.compareTo("Player")==0) {
                System.out.println("PLAYER ID:"+element.getAttributeValue("id"));
            }
            if(aux.compareTo("Room")==0) {
                System.out.println("ROOM ID:"+element.getAttributeValue("id"));
            }
            
            List children = element.getContent();
            Iterator iterator = children.iterator();
            while (iterator.hasNext()) {
                Object child = iterator.next();
                xmlReadPlayerLeftRoom(child);
            }
        }
        
    }
    
    public static void xmlReadLogin(Object o) {
        String aux;
        if (o instanceof Element) {
            Element element = (Element) o;
            aux=element.getName();
            if(aux.compareTo("Player")==0) {
                System.out.println("PLAYER"+element.getText());
            }
            if(aux.compareTo("Password")==0) {
                System.out.println("PASSWORD:"+element.getText());
            }
            List children = element.getContent();
            Iterator iterator = children.iterator();
            while (iterator.hasNext()) {
                Object child = iterator.next();
                xmlReadLogin(child);
            }
        }
    }
    
    public static void xmlReadChatMsg(Object o) {
        String aux;
        if (o instanceof Element) {
            Element element = (Element) o;
            aux=element.getName();
            if(aux.compareTo("PlayerRt")==0) {
                System.out.println("PLAYER_RT ID:"+element.getAttributeValue("id"));
            }
            if(aux.compareTo("Msg")==0) {
                System.out.println("Message:"+element.getText());
            }
            List children = element.getContent();
            Iterator iterator = children.iterator();
            while (iterator.hasNext()) {
                Object child = iterator.next();
                xmlReadChatMsg(child);
            }
        }
        
    }
    
    public static void xmlReadError(Object o) {
        
        int id,severity;
        String msg;
        String aux;
        if (o instanceof Element) {
            Element element = (Element) o;
            aux=element.getName();
            if(aux.compareTo("Tipo")==0) {
                System.out.println("Tipo ID:"+element.getAttributeValue("id"));
            }
            if(aux.compareTo("severity")==0) {
                System.out.println("Severity:"+element.getText());
            }
            if(aux.compareTo("MSG")==0) {
                System.out.println("MENSAGE:"+element.getText());
            }
            
            List children = element.getContent();
            Iterator iterator = children.iterator();
            while (iterator.hasNext()) {
                Object child = iterator.next();
                xmlReadError(child);
            }
        }
    }
    
    
    public static void xmlReadTableCreated(Object o) {
        
        int id,severity;
        String msg;
        String aux;
        if (o instanceof Element) {
            Element element = (Element) o;
            aux=element.getName();
            if(aux.compareTo("Owner")==0) {
                System.out.println("Owner ID:"+element.getAttributeValue("id"));
            }
            if(aux.compareTo("Tablename")==0) {
                System.out.println("Nombre:"+element.getText());
            }
            if(aux.compareTo("Players")==0) {
                System.out.println("CUANTOS PLAYERS:"+element.getText());
            }
            
            
            List children = element.getContent();
            Iterator iterator = children.iterator();
            while (iterator.hasNext()) {
                Object child = iterator.next();
                xmlReadTableCreated(child);
            }
        }
    }
    
    
    
}//fin

