/* TolucaConstants.java
 * Created on Sep 22, 2004
 *
 * Last modified: $Date: 2005/02/11 21:06:08 $
 * @version $Revision: 1.4 $ 
 * @author afeltes
 */
package py.edu.uca.fcyt.toluca;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.util.logging.Level;

/**
 * 
 * @author afeltes
 *
 */
public class TolucaConstants {

    public static final Level CLIENT_DEBUG_LOG_LEVEL = Level.WARNING;

    /**
     * @param result
     */
    public static void dumpBeanToXML(Object result) {
        System.out.println("/****************************************************");
        XMLEncoder e=	new XMLEncoder(		new BufferedOutputStream(System.out));
		e.writeObject(result);
		System.out.println("****************************************************/");		       
    }

    /**
     * @param value
     * @param string
     * @param string2
     * @return
     */
    public static String replaceString(String value, String string, String string2) {
        StringBuffer ret = new StringBuffer();
        String tmp = value;
        int i = -1;
        int nOfReplaces = 0;
        int charNotReplaced = 0;
        
        if((i = tmp.indexOf(string)) != -1)
        {
            while (tmp.trim().length() > 0 && i >= 0) {
                for (int j = 0; j < i; j++) {
                    ret.append(value.toCharArray()[charNotReplaced + nOfReplaces*string.length()]);
                    charNotReplaced++;                    
                }
                nOfReplaces++;
                ret.append(string2);
                tmp = value.substring(charNotReplaced + nOfReplaces*string.length());
                i = tmp.indexOf(string);
            }
            ret.append(tmp);
        } else
            ret.append(string);
        return ret.toString();
    }
    
    public static void main(String[] args) {
        String ori = "aloooo \\n \\n pepe";       
        String replaced = TolucaConstants.replaceString(ori, "p", "POP");
        System.out.println(replaced);
    }

    /**
     * @return
     */
    public static boolean isWindowFamily() {
        return System.getProperty("os.name").toLowerCase().indexOf("windows") != -1;
    }
}
