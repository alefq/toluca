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
public class TrucoGameClient extends TrucoGame implements TrucoListener{
    /** Creates a new instance of TrucoClient */
    protected TrucoHandClient trucoHandCli;
   
    
    public TrucoGameClient(TrucoTeam t1, TrucoTeam t2) {
        super(t1, t2);
        System.err.println("Se crea un nuevo truco Game Client");
    
    }
    public void startGameClient(){
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
     public void recibirCartas (TrucoPlayer tp,TrucoCard cards[]){
        trucoHandCli.recibirCartas(tp,cards);
        dealtCards(tp,cards);
    }
    public boolean esPosibleJugar(TrucoPlay tp){
        return trucoHandCli.esPosibleJugar(tp);
    }
    public void play(TrucoEvent event){
        switch(event.getTypeEvent()){
            case TrucoEvent.ENVIAR_CARTAS:
               recibirCartas(event.getPlayer(),event.getCards()); 
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
        
        }
    }

    public void turn (TrucoEvent event){
		//new Exception("Nada implementado aun :-(   ").printStackTrace();
		fireTurnEvent(event.getPlayer(), TrucoEvent.JUGAR_CARTA);
    }
    public void endOfHand(TrucoEvent event){
	new Exception("Nada implementado aun :-(   ").printStackTrace();
    }
    public void cardsDeal(TrucoEvent event){
    	System.err.println("event.getTypeEvent(): " + event.getTypeEvent());
		new Exception("En cardsDeal. Acá se tendrían que recibir las cartas").printStackTrace(System.err);				
    }
    public void handStarted(TrucoEvent event){
	new Exception("Nada implementado aun :-(   ").printStackTrace();
    }
    public void gameStarted(TrucoEvent event){
	new Exception("Nada implementado aun :-(   ").printStackTrace();
    }
    public void endOfGame(TrucoEvent event){
	new Exception("Nada implementado aun :-(   ").printStackTrace();
    }
    public TrucoPlayer getAssociatedPlayer(){
        return null;
    }
    public void play (TrucoPlay tp) throws InvalidPlayExcepcion{
        try{
            trucoHandCli.play(tp);
        }
        catch(InvalidPlayExcepcion e){
            throw e;
        }
    }
     public void startHandClient(TrucoPlayer tPlayer){
        int i; //
        System.out.println("cantidad de players preparados="+cantidadDePlayersPreparados);
        int numOfPlayer = teams[0].getNumberOfPlayer(tPlayer)*2;
        if (numOfPlayer >= 0){
            if (playersPreparados[numOfPlayer]==false){
                playersPreparados[numOfPlayer] = true;
                firePlayEvent(tPlayer,TrucoEvent.PLAYER_CONFIRMADO);
                cantidadDePlayersPreparados++;
                System.out.println(tPlayer.getName()+"confirmado");
            }
            else
                return;
        }
        numOfPlayer = teams[1].getNumberOfPlayer(tPlayer)*2+1;
        if (numOfPlayer>=0){
            if (playersPreparados[numOfPlayer]==false){
                playersPreparados[numOfPlayer] = true;
                firePlayEvent(tPlayer,TrucoEvent.PLAYER_CONFIRMADO);
                cantidadDePlayersPreparados++;
                System.out.println(tPlayer.getName()+"confirmado");
            }
            else
                return;
        }
        if (cantidadDePlayersPreparados == numberOfPlayers){
            cantidadDePlayersPreparados=0;
            startHandConfirmatedClient();
        }
    }
    private void startHandConfirmatedClient(){
        
        if(points[0] >= 30 || points[1] >= 30){
            fireEndOfGameEvent();
        }
        else{
            numberOfHand++;
            fireHandStarted();/*para que se preparen los jugadores*/
            trucoHandCli = new TrucoHandClient(this, numberOfHand-1); /*se crea un truco hand y guardo la referencia*/
            //TODO comentamos el starthand porque tiene que venir por que tiene que venir en un evento
            //trucoHandCli.startHand();
        }
    }
}
