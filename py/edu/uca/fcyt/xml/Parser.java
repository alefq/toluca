/*
 * Parser.java
 *
 * Created on 12 de noviembre de 2002, 10:20
 */

package py.edu.uca.fcyt.xml;

import java.io.IOException;
import java.io.StringReader;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
/**
 *
 * @author  psanta
 */
public class Parser {
    
    public static Element getElement(String xml) throws JDOMException, IOException {
        Document doc = (new SAXBuilder()).build( new StringReader(xml));
        return doc.getRootElement();
    }
    
    public static byte[] Decode(String xml) {
        //StringReader sr = new StringReader(xml);
        byte[] elvec = xml.getBytes();
        byte[] losbytes = new byte[elvec.length];
        
        int i, j=0;
        String tmp = "";
        byte c = 0;
        for (i=0; i<elvec.length; i++) {
            c = elvec[i];
            if(c == '&') {      // Caracter especial
                tmp = "";
                i++;
                c = elvec[i];
                while (i<elvec.length && c!= ';') {
                    tmp += (char)c;
                    i++;
                    c = elvec[i];
                }
                losbytes[j++] = charDecode(tmp);
            } else {
                losbytes[j++] = c;
            }
        }
        
        byte[] ret = new byte[j];
        
        for (i=0; i<j; i++) ret[i] = losbytes[i];

        return ret;
    }
    
    public static byte charDecode(String str) {
        byte ret=0;
        if (str.compareTo("lt") == 0) {
            ret = '<';
        } else if (str.compareTo("gt") == 0) {
            ret = '>';
        } else if (str.compareTo("apos") == 0) {
            ret = '\'';
        } else if (str.compareTo("quot") == 0) {
            ret = '\"';
        } else if (str.toLowerCase().startsWith("#x")) {
            // Número en hexadecimal
            System.out.println("En hexa: " + str.substring(2, str.length()));
            ret = (byte)Integer.parseInt(str.substring(2, str.length()), 16);
            
        } else if (str.toLowerCase().startsWith("#")) {
            // Número en decimal
            ret = (byte)Integer.parseInt(str.substring(1, str.length()));            
        }
        
        return ret;
    }
    
}

