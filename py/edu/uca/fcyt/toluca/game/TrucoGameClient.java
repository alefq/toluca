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
            fireGameStarted();
            for (int i=0; i<numberOfPlayers; i++)
                playersPreparados[i] = true;
            startHandConfirmatedClient();//fletes
    }
    /** Enviar mensaje a todos los oyentes sobre el final del juego.
     *
     */
     void recibirCartas (TrucoPlayer tp,TrucoCard cards[]){
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
                startHandConfirmatedClient();
                break;
            case TrucoEvent.INICIO_DE_MANO:
                startHandConfirmatedClient();
                break;
        
        }
    }

    public void turn (TrucoEvent event){
    }
    public void endOfHand(TrucoEvent event){
    }
    public void cardsDeal(TrucoEvent event){
    }
    public void handStarted(TrucoEvent event){
    }
    public void gameStarted(TrucoEvent event){
    }
    public void endOfGame(TrucoEvent event){
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
            trucoHandCli.startHand();
        }
    }
}
