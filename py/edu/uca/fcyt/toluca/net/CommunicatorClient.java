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
		((EventDispatcherClient)getEventDispatcher()).setCommClient(this);
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
	
	//REQUEST DEL ROOM
	public void loginRequested(RoomEvent event) {
		super.sendXmlPackage(event);
	}
	public void createTableRequested(RoomEvent event) {
		super.sendXmlPackage(event);
	}
	public void tableJoinRequested(RoomEvent event) {

		super.sendXmlPackage(event);
	}
	
	
	
	//REQUEST DE LA TABLA
	public void playerSitRequest(TableEvent event) {
		super.sendXmlPackage(event);
		
	}
	public void playerStandRequest(TableEvent event) {
		super.sendXmlPackage(event);
	}
	public void playerKickRequest(TableEvent event) {
		super.sendXmlPackage(event);
		
	}
	public void playerLeft(TableEvent event) {
		super.sendXmlPackage(event);
		
	}
	public void gameStartRequest(TableEvent event) {
		super.sendXmlPackage(event);
		
	}
	
	//METODOS CORRESPONDIENTES AL TRUCO_GAME
	
	public void play(TrucoEvent event) {
		//System.out.println("El trucoplayer de este comm es  "+getTrucoPlayer());
		//System.out.println("El play hizo "+event.getPlayer());
		//System.out.println("eS DE TIPO "+event.getType());
		if(event.getPlayer().getName().equals(getTrucoPlayer().getName()))
		{
			
		
	//	System.out.println(getClass().getName()+"se va a hacer un play al server");
		TrucoPlay trucoPlay= event.toTrucoPlay();
		
		logger.debug("SE resive un play de "+trucoPlay.getPlayer().getName());
		logger.debug("TAbla : "+trucoPlay.getTableNumber());
		logger.debug("type : "+trucoPlay.getType());
		if(trucoPlay.getCard()!=null)
			logger.debug("carta Palo: "+trucoPlay.getCard().getKind() +" val "+trucoPlay.getCard().getValue());
		logger.debug("value > "+trucoPlay.getValue());
		super.sendXmlPackage(trucoPlay);
		}
	}
	
	public void playResponse(TrucoEvent event) {
	
		logger.debug("playResponse");
	}
	
	public void turn(TrucoEvent event) {
	
		logger.debug("Turn");
	}
	
	public void endOfHand(TrucoEvent event) {
	
		
	}
	
	public void cardsDeal(TrucoEvent event) {
	
		
	}
	
	public void handStarted(TrucoEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	public void chatMessageSent(RoomEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	/*public static void main(String[] args) {
	DOMConfigurator.configure(System.getProperty("user.dir")
            + System.getProperty("file.separator") + "log.xml");
	CommunicatorClient comm=new CommunicatorClient();
	
	comm.sendXmlPackage(new JButton("Hello, world"));
	
	
	try {
		comm.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	while(true)
			;
}*/
}
