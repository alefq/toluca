/* TolucaConstants.java
 * Created on Sep 22, 2004
 *
 * Last modified: $Date: 2004/10/22 19:58:54 $
 * @version $Revision: 1.2 $ 
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
    
}
