/*
 * TrucoHandClient.java
 *
 * Created on 5 de junio de 2003, 20:34
 */

package py.edu.uca.fcyt.toluca.game;

import java.util.Vector;

import py.edu.uca.fcyt.toluca.statusGame.statusGameCliente.TrucoStatusTableCliente;
/**
 *
 * @author  Julio Rey
 */

public class TrucoHandClient extends TrucoHand{
    
    /** Creates a new instance of TrucoHandClient */
    private py.edu.uca.fcyt.toluca.statusGame.statusGameCliente.TrucoStatusTableCliente statusTableCli;
    private TrucoGameClient game;
    private boolean cartasRecibidasDePlayers[];
    private boolean runTrucoHandClient = false;
    private int cantidadDeCartasDePlayersRecibidas;
    public TrucoHandClient(TrucoGameClient game, int reparteCartas) {
	super(game, reparteCartas);
        this.game = game;
        cantidadDePlayers = game.getNumberOfPlayers();
        cartasRecibidasDePlayers = new boolean[cantidadDePlayers];
        for (int i=0; i<cantidadDePlayers; i++)
            cartasRecibidasDePlayers[i] = false;
        primerTurnoNumber = (reparteCartas+1)%cantidadDePlayers;
        playTurnNumber = primerTurnoNumber; //quien juega la primera carta
        envidoTurnNumber = primerTurnoNumber; //quien canta el primer envido en caso de cantar
        florTurnNumber = primerTurnoNumber; //quien canta su flor en caso de haber
        cantidadDeCartasDePlayersRecibidas =0;
        statusTableCli = new TrucoStatusTableCliente(cantidadDePlayers); //se crea un estado de la mesa
        detalleDePuntaje = new Vector();
        for (int i=0; i<3; i++)
            winRound[i] = -1;
        points[0] = 0;  /*Cerar los puntajes.*/
    	points[0] = 0;    
        teams[0] = game.getTeam(0);
        teams[1] = game.getTeam(1);
        getPies(); //obtener los pies de la mano
    }
    public void recibirCartas (TrucoPlayer tp, TrucoCard cartas[]){
        int numeroDelPlayer = getNumberOfPlayer(tp);
        if (cartasRecibidasDePlayers[numeroDelPlayer])
            return;
        cartasRecibidasDePlayers[numeroDelPlayer] = true;
        statusTableCli.recibirCartas(numeroDelPlayer, cartas);
        cantidadDeCartasDePlayersRecibidas++;
        if(cantidadDeCartasDePlayersRecibidas == cantidadDePlayers)
            runTrucoHandClient = true;
    }
}
