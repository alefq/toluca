package py.edu.uca.fcyt.toluca.net;

import org.apache.log4j.Logger;
import org.jdom.Element;




/**
 * @author CYT UCA
 *
 * 
 */
public class CommunicatorServer extends Communicator{
	static Logger logger = Logger.getLogger(CommunicatorServer.class);
	public CommunicatorServer()
	{
		super();
	}
	public void receiveXmlPackage(Element xmlPackage) {
		
		System.out.println("Se resive un packohiiiiiiiii");
	}
	public void connectionFailed()
	{
		System.out.println("Fallo la coneccion");
	}
}
