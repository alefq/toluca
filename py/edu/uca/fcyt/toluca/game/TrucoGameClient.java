/*
 * TrucoClient.java
 *
 * Created on 5 de junio de 2003, 02:47 PM
 */

package py.edu.uca.fcyt.toluca.game;
import py.edu.uca.fcyt.toluca.event.TrucoEvent;
import py.edu.uca.fcyt.toluca.event.TrucoListener;

/**
 *
 * @author  Julio Rey
 */
public class TrucoGameClient extends TrucoGame
/*implements TrucoListener */ {
	/** Creates a new instance of TrucoClient */
	protected TrucoHandClient trucoHandCli;
	
	
	public TrucoGameClient(TrucoTeam t1, TrucoTeam t2)
	{
		super(t1, t2);
		System.err.println("Se crea un nuevo truco Game Client");
		
	}
	public void startGameClient()
	{
		numberOfPlayers = teams[0].getNumberOfPlayers()*2;
		playersPreparados = new boolean[numberOfPlayers];
		newGame();
		//fireGameStarted(); //Ahora ya no hace falta avisar?
		//porque Table inicia el juego y se crea en el serva el TrucoGame (no estoy seguro. Ale)
		for (int i=0; i<numberOfPlayers; i++)
			playersPreparados[i] = true;
		//TODO Aca comentamos el startHandConfirmatedClient para probar porque no se deben disparar los eventos automáticamente
		//startHandConfirmatedClient();//fletes
	}
	/** Enviar mensaje a todos los oyentes sobre el final del juego.
	 *
	 */
	public void recibirCartas(TrucoPlayer tp,TrucoCard cards[])
	{
		trucoHandCli.recibirCartas(tp,cards);
		dealtCards(tp,cards);
		if (trucoHandCli.isRunHand())
			trucoHandCli.startHand();
	}
	public boolean esPosibleJugar(TrucoPlay tp)
	{
		return trucoHandCli.esPosibleJugar(tp);
	}
	public void play(TrucoEvent event)
	{
		switch(event.getTypeEvent())
		{
			case TrucoEvent.ENVIAR_CARTAS:
				recibirCartas(event.getPlayer(),event.getCards());
				//TODO aca parece que se tiene que llamar a recibir cartas
				cardsDeal(event);
				break;
			case TrucoEvent.PLAYER_CONFIRMADO:
				startHand(event.getPlayer());
				break;
			case TrucoEvent.INICIO_DE_JUEGO:
				//startHandConfirmatedClient();
				break;
			case TrucoEvent.INICIO_DE_MANO:
				startHandConfirmatedClient();
				break;
			case TrucoEvent.JUGAR_CARTA:
				break;
			default:
			  /*  TrucoPlay tp = event.toTrucoPlay();
				System.out.println("Se recibio un TrucoEvent para ser TrucoPlay"+ tp.getType());
				if (tp.getType()>=0){
					play(tp);
					playResponse(tp);
				}*/
				break;
		}
	}
	
	public void turn(TrucoEvent event)
	{
		//new Exception("Nada implementado aun :-(   ").printStackTrace();
		//TODO ACA TOCO PARA ASIGNAR EL TURNO, VERIFICAR ESTO.
		//trucoHandCli.startHand();
		
		fireTurnEvent(event.getPlayer(), TrucoEvent.JUGAR_CARTA);
	}
	public void endOfHand(TrucoEvent event)
	{
		new Exception("Nada implementado aun :-(   ").printStackTrace(System.out);
	}
	public void cardsDeal(TrucoEvent event)
	{
		System.err.println("event.getTypeEvent(): " + event.getTypeEvent());
		new Exception("En cardsDeal. Acá se tendrían que recibir las cartas").printStackTrace(System.err);
	}
	public void handStarted(TrucoEvent event)
	{
		new Exception("Nada implementado aun :-(   ").printStackTrace(System.out);
	}
	public void gameStarted(TrucoEvent event)
	{
		new Exception("Nada implementado aun :-(   ").printStackTrace(System.out);
	}
	public void endOfGame(TrucoEvent event)
	{
		new Exception("Nada implementado aun :-(   ").printStackTrace(System.out);
	}
	public TrucoPlayer getAssociatedPlayer()
	{
		return null;
	}
	synchronized public void play(TrucoPlay tp) throws InvalidPlayExcepcion
	{
		try
		{
			trucoHandCli.play(tp);
		}
		catch(InvalidPlayExcepcion e)
		{
			throw e;
		}
	}
	public void StartHand(TrucoPlayer tPlayer)
	{
		System.out.println("trucoGame-StartHand se quiere Preparar!!!"+tPlayer.getName());
		
	}
	public void startHandClient(TrucoPlayer tPlayer)
	{
		int i; //
		System.out.println("cantidad de players preparados="+cantidadDePlayersPreparados);
		int numOfPlayer = teams[0].getNumberOfPlayer(tPlayer)*2;
		if (numOfPlayer >= 0)
		{
			if (playersPreparados[numOfPlayer]==false)
			{
				playersPreparados[numOfPlayer] = true;
				firePlayEvent(tPlayer,TrucoEvent.PLAYER_CONFIRMADO);
				cantidadDePlayersPreparados++;
				System.out.println(tPlayer.getName()+"confirmado");
			}
			else
				return;
		}
		numOfPlayer = teams[1].getNumberOfPlayer(tPlayer)*2+1;
		if (numOfPlayer>=0)
		{
			if (playersPreparados[numOfPlayer]==false)
			{
				playersPreparados[numOfPlayer] = true;
				firePlayEvent(tPlayer,TrucoEvent.PLAYER_CONFIRMADO);
				cantidadDePlayersPreparados++;
				System.out.println(tPlayer.getName()+"confirmado");
			}
			else
				return;
		}
		if (cantidadDePlayersPreparados == numberOfPlayers)
		{
			cantidadDePlayersPreparados=0;
			startHandConfirmatedClient();
		}
	}
	private void startHandConfirmatedClient()
	{
		
		if(points[0] >= 30 || points[1] >= 30)
		{
			fireEndOfGameEvent();
		}
		else
		{
			numberOfHand++;
			fireHandStarted();/*para que se preparen los jugadores*/
			trucoHandCli = new TrucoHandClient(this, numberOfHand-1); /*se crea un truco hand y guardo la referencia*/
			trucoHand = trucoHandCli;		// Truco Julístico para evitar el STRESS. Muy feo, pero cumple el objetivo: EVITAR STRESS
			//TODO comentamos el starthand porque tiene que venir por que tiene que venir en un evento
		}
	}
	public TrucoCard getCard(byte myKind, byte myValue)
	{
		TrucoCard myCarta =  trucoHandCli.getCard(myKind,  myValue);
		if (myCarta == null)
		{
			System.out.println("en TrucoGame getCard devuelve null");
		}
		return myCarta;
	}
	/**
	 *
	 */
/*	public void playResponse(TrucoPlay tp) {
			TrucoEvent te = tp.toTrucoEvent();
			System.out.println("se hizo truco event="+te.getTypeEvent());
			if (tp.getCard())
			if (te.getCard() == null)
				System.out.println("carta null en playResponse");
			if (te.getCard() != getCard((byte)te.getCard().getKind(),(byte)te.getCard().getValue()))
				System.out.println("carta no tg en mazo");;
 
			firePlayResponseEvent(te.getPlayer(), te.getCard(), te.getTypeEvent());
	}*/
	public void playResponse(TrucoPlayer tp, TrucoCard tc)
	{
		firePlayResponseEvent(tp, tc, TrucoEvent.JUGAR_CARTA);
	}
	public void playResponse(TrucoPlayer tp, byte type)
	{
		firePlayResponseEvent(tp, type);
	}
	public void playResponse(TrucoPlayer tp, byte type, int value)
	{
		firePlayResponseEvent(tp, type, value);
	}
	
	public void fireEndOfHandEvent()
	{
		points[0] = points[0] + trucoHand.getPointsOfTeam(0);
		points[1] = points[1] + trucoHand.getPointsOfTeam(1);
		teams[0].setPoints(points[0]);
		teams[1].setPoints(points[1]);
		TrucoPlayer tp = teams[(numberOfHand+1)%2].getTrucoPlayerNumber((numberOfHand-1)%numberOfPlayers/2);
		TrucoEvent event = new TrucoEvent(this,numberOfHand,tp,TrucoEvent.FIN_DE_MANO);
		for(int i=0; i<listenerlist.size();i++)
		{
			((TrucoListener)(listenerlist.get(i))).endOfHand(event);
		}
		System.out.println("------------------------------------------------------------------------");
		System.out.println("--------------------------------------Puntajes--------------------------");
		for (int i=0; i<2; i++)
			System.out.println(teams[i].getName()+" :"+teams[i].getPoints()+"puntos.");
		detalleDelPuntaje = trucoHand.getPointsDetail();
		for (int i=0; i<detalleDelPuntaje.size(); i++)
			System.out.println(((PointsDetail)detalleDelPuntaje.get(i)).aString());
		
	}
}
