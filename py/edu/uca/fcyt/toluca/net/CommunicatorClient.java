package py.edu.uca.fcyt.toluca.net;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;



import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import py.edu.uca.fcyt.net.XmlPackagesSession;
import py.edu.uca.fcyt.toluca.RoomClient;
import py.edu.uca.fcyt.toluca.event.RoomEvent;
import py.edu.uca.fcyt.toluca.event.TableEvent;
import py.edu.uca.fcyt.toluca.event.TrucoEvent;
import py.edu.uca.fcyt.toluca.game.TrucoPlay;




/**
 * @author CYT UCA
 *
 * 
 */
public class CommunicatorClient extends Communicator{

	/**
	 * @param client
	 */
	public CommunicatorClient(RoomClient client) {
		
		this();
		eventDispatcher.setRoom(client);
	}
	static Logger logger = Logger.getLogger(CommunicatorClient.class);
	public CommunicatorClient()
	{
		super(new EventDispatcherClient());
		int retinit = init();
	}
	public int init()
	{
		int ret = -1;
		try
		{
			
			setSocket(new Socket("localhost", 6767));
			
			ret = XmlPackagesSession.XML_PACKAGE_SESSION_INIT_OK;
			logger.info("Se establecio la coneccion con el servidor");
		} catch (UnknownHostException e)
		{
			logger.error("No se puede identificar el host");
			ret = -5;
		} catch (IOException e)
		{
			logger.error("Fallo la coneccion IO Exception");
			ret = -4;
		}
		return ret;
	}
	public void loginRequested(RoomEvent ev) {
		
		System.out.println("Se va a enviar un roomEvent loginRequested");
		super.sendXmlPackage(ev);
		
		
	}
	
	public static void main(String[] args) {
		DOMConfigurator.configure(System.getProperty("user.dir")
                + System.getProperty("file.separator") + "log.xml");
		CommunicatorClient comm=new CommunicatorClient();
		
		comm.sendXmlPackage(new JButton("Hello, world"));
		
		while(true)
				;
	}
	
}
