/*
 * TrucoHandClient.java
 *
 * Created on 5 de junio de 2003, 20:34
 */

package py.edu.uca.fcyt.toluca.game;

import java.util.Vector;
import py.edu.uca.fcyt.toluca.event.TrucoEvent;

import py.edu.uca.fcyt.toluca.statusGame.statusGameCliente.TrucoStatusTableCliente;
/**
 *
 * @author  Julio Rey
 */

public class TrucoHandClient extends TrucoHand{
    
    /** Creates a new instance of TrucoHandClient */
    private py.edu.uca.fcyt.toluca.statusGame.statusGameCliente.TrucoStatusTableCliente statusTable;
    private TrucoGameClient game;
    private boolean cartasRecibidasDePlayers[];
    private boolean runTrucoHandClient = false;
    private int cantidadDeCartasDePlayersRecibidas;
    public TrucoHandClient(TrucoGameClient game, int reparteCartas) {
        //super(game, reparteCartas);  No se tiene q repartir las cartas, tiene q venir de un evento del serva
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
        statusTable = new TrucoStatusTableCliente(cantidadDePlayers); //se crea un estado de la mesa
        detalleDePuntaje = new Vector();
        for (int i=0; i<3; i++)
            winRound[i] = -1;
        points[0] = 0;  /*Cerar los puntajes.*/
        points[0] = 0;
        teams[0] = game.getTeam(0);
        teams[1] = game.getTeam(1);
        getPies(); //obtener los pies de la mano
    }
    public void recibirCartas(TrucoPlayer tp, TrucoCard cartas[]){
        int numeroDelPlayer = getNumberOfPlayer(tp);
        if (cartasRecibidasDePlayers[numeroDelPlayer])
            return;
        cartasRecibidasDePlayers[numeroDelPlayer] = true;
        statusTable.recibirCartas(numeroDelPlayer, cartas);
        cantidadDeCartasDePlayersRecibidas++;
        if(cantidadDeCartasDePlayersRecibidas == cantidadDePlayers)
            runTrucoHandClient = true;
    }
    

    protected void nextPlayTurn() throws InvalidPlayExcepcion{
        cartasJugadas++;
        if (cartasJugadas%cantidadDePlayers == 0){
            winRound[numeroDeRonda-1] = statusTable.resultadoRonda(numeroDeRonda); /*fin de ronda*/
            
            if(winRound[numeroDeRonda-1]>=0)
                playTurnNumber = winRound[numeroDeRonda-1];
            else
                playTurnNumber = (playTurnNumber+1)%cantidadDePlayers;/*siguiente player*/
            
            statusTable.terminoRonda();
            
            if(finDeRonda())
                return;
            numeroDeRonda++;
            volverAEstadoDeJuego();
        }
        else{
            playTurnNumber = (playTurnNumber+1)%cantidadDePlayers;/*siguiente player*/
        }
        playTurn();/*asignar turno*/
    }
    
    /*asignar al player el turno*/
    protected void playTurn() throws InvalidPlayExcepcion{
        try {
            if(statusTable.estaCerrado(playTurnNumber)){
                nextPlayTurn();
                return;
            }
            playTurn = teams[playTurnNumber%2].getTrucoPlayerNumber(playTurnNumber/2);
            //TODO AA comente el fire turn event, supongo que tienen que venir en un paqutes 
            //game.fireTurnEvent(playTurn,TrucoEvent.TURNO_JUGAR_CARTA);
        } catch (NullPointerException npe ) {
            System.out.println("\n####Error en playturn");
            if (statusTable == null)
                System.out.println("Status Table es nulo");
            throw npe;
        }
    }
    protected int sePuedeCantarEnvido(TrucoPlay tp) {
        
        if (!sePuedeCantarEnvido) /*ya no se puede cantar*/
            return 1;
        
        if(playTurn != tp.getPlayer())/*no es el turno de ese player*/
            return 2;
        
        if (tp.getPlayer()!=pie1 && tp.getPlayer()!=pie2)/*no es pie el que canta*/
            return 3;
        
        if ((numberOfEnvidos+numberOfRealEnvidos) >= 3 && tp.getType() != TrucoPlay.FALTA_ENVIDO)/*ya se cantaron mas de */
            return 4;
        
        if(estadoActual != PRIMERA_RONDA && estadoActual != ENVIDO && estadoActual != REAL_ENVIDO && estadoActual != ESPERANDO_RESPUESTA_DEL_TRUCO)
            return 5;
        
        if (statusTable.jugoPrimeraCarta(getNumberOfPlayer(tp.getPlayer())) && estadoActual != ENVIDO && estadoActual != REAL_ENVIDO && estadoActual != FALTA_ENVIDO)
            return 6;
        return 0;
    }
    /*El Player canta envido, real envido o falta envido>>>>>>>*/
    /** Verifica si es posible realizar una jugada.
     * @param tp TrucoPlay que quiere ser verificada.
     * @return retorna verdadero en caso de ser validá la jugada, falso en caso contrario.
     */
    /*esta funcion tengo que cambiar con choco*/
    public boolean esPosibleJugar(TrucoPlay tp) {
        int numPlayer = getNumberOfPlayer(tp.getPlayer());
        if (numPlayer == -1)
            return false;
        
        if (seTerminoLaMano)/*ya termino la mano*/
            return false;
        
        switch(tp.getType()){
            
            case TrucoPlay.ENVIDO:
                if(tp.getPlayer() != playTurn)
                    return false; //no es el turno, no puede ju
                if(sePuedeCantarEnvido(tp)>0)
                    return false;
                break;
                
            case TrucoPlay.REAL_ENVIDO:
                if(tp.getPlayer() != playTurn)
                    return false; //no es el turno, no puede ju
                if(sePuedeCantarEnvido(tp)>0)
                    return false;
                break;
                
            case TrucoPlay.FALTA_ENVIDO:
                if(tp.getPlayer() != playTurn)
                    return false; //no es el turno, no puede ju
                if(sePuedeCantarEnvido(tp)>0)
                    return false;
                break;
                
            case TrucoPlay.TRUCO:
                if(tp.getPlayer() != playTurn)
                    return false; //no es el turno, no puede ju
                
                if(playTurn != tp.getPlayer() || (estadoActual==PRIMERA_RONDA && playTurn != pie1 && playTurn != pie2))
                    return false; /*el player no puede cantar TRUCO ahora*/
                if(truco)
                    return false;/*("Ya se canto truco, no se puede cantar mas nada"));*/
                if(laPalabra!=null)
                    return false; /*el el equipo no tiene la palabra*/
                if(estadoActual!=PRIMERA_RONDA && estadoActual != SEGUNDA_RONDA && estadoActual != TERCERA_RONDA)
                    return false; /*no hay estado para cantar*/
                
                break;
                
            case TrucoPlay.RETRUCO:
                if(tp.getPlayer() != playTurn)
                    return false; //no es el turno, no puede ju
                if(playTurn != tp.getPlayer() || (estadoActual==PRIMERA_RONDA && playTurn != pie1 && playTurn != pie2))
                    return false; /*el player no puede cantar ahora*/
                if(!truco)
                    return false; /*tdvia no se canto truco*/
                if(retruco)
                    return false;/*("Ya se canto retruco, no se puede cantar mas nada"));*/
                if(!laPalabra.isPlayerTeam(playTurn))
                    return false; /*el el equipo no tiene la palabra*/
                
                if(estadoActual!=PRIMERA_RONDA && estadoActual != SEGUNDA_RONDA && estadoActual != TERCERA_RONDA && estadoActual != ESPERANDO_RESPUESTA_DEL_TRUCO)
                    return false; /*no es posible cantar*/
                
                break;
                
            case TrucoPlay.VALE_CUATRO:
                if(tp.getPlayer() != playTurn)
                    return false; //no es el turno, no puede ju
                
                if(playTurn != tp.getPlayer() || (estadoActual==PRIMERA_RONDA && playTurn != pie1 && playTurn != pie2))
                    return false; /*el player no puede cantar, */
                if(!retruco)
                    return false; /*tdvia no se canto truco*/
                if(valecuatro)
                    return false; /*ya no se puede nada, se canto vale cuatro*/
                if(!laPalabra.isPlayerTeam(playTurn))
                    return false; /*el equipo no tiene la palabra*/
                if(estadoActual!=PRIMERA_RONDA && estadoActual != SEGUNDA_RONDA && estadoActual != TERCERA_RONDA && estadoActual != ESPERANDO_RESPUESTA_DEL_RETRUCO)
                    return false; /*en este estado no se puede cantar*/
                
                break;
                
            case TrucoPlay.QUIERO:
                if(tp.getPlayer() != playTurn)
                    return false; //no es el turno, no puede ju
                
                if(playTurn!=pie1 && playTurn!=pie2)
                    return false; /*solamente los pies pueden responder eso*/
                if(estadoActual != ENVIDO && estadoActual != REAL_ENVIDO && estadoActual != FALTA_ENVIDO && estadoActual != ESPERANDO_RESPUESTA_DEL_TRUCO
                && estadoActual != ESPERANDO_RESPUESTA_DEL_RETRUCO && estadoActual!=ESPERANDO_RESPUESTA_DEL_VALECUATRO)
                    return false; //no se reponde nada
                break;
                
            case TrucoPlay.NO_QUIERO:
                if(tp.getPlayer() != playTurn)
                    return false; //no es el turno, no puede ju
                if(playTurn!=pie1 && playTurn!=pie2)
                    return false; /*solamente los pies pueden responder eso*/
                if(estadoActual != ENVIDO && estadoActual != REAL_ENVIDO && estadoActual != FALTA_ENVIDO && estadoActual != ESPERANDO_RESPUESTA_DEL_TRUCO
                && estadoActual != ESPERANDO_RESPUESTA_DEL_RETRUCO && estadoActual!=ESPERANDO_RESPUESTA_DEL_VALECUATRO)
                    return false; //no se reponde nada
                
                break;
                
            case TrucoPlay.ME_VOY_AL_MAZO:
                if(tp.getPlayer() != playTurn)
                    return false;
                if(tp.getPlayer()!=pie1 && tp.getPlayer() != pie2)
                    return false; /*solamente los pies puede llevar al mazo al equipo*/
                if (estadoActual != PRIMERA_RONDA && estadoActual != SEGUNDA_RONDA && estadoActual != TERCERA_RONDA)
                    return false; /*solo se pueden import al mazo en estado de juego*/
                break;
                
            case TrucoPlay.JUGAR_CARTA:
                if(tp.getPlayer() != playTurn)
                    return false; //no es el turno, no puede ju
                if (estadoActual != PRIMERA_RONDA && estadoActual != SEGUNDA_RONDA && estadoActual != TERCERA_RONDA)
                    return false; //solamente en juegos de ronda se puede jugar carta
                /*aqui>>>>>*/
                if (!statusTable.puedeJugarCarta(playTurnNumber, tp.getCard()))
                    return false;/*no tiene esa carta para jugar o ya jugo*/
                break;
                
            case TrucoPlay.PASO_ENVIDO:
                if(tp.getPlayer() != playTurn)
                    return false; //no es el turno, no puede ju
                if(estadoActual != RONDA_DE_ENVIDO)
                    return false; /*no puede pasar envido porque no es ronda*/
                break;
                
            case TrucoPlay.PASO_FLOR:
                //if(estadoActual != RONDA_DE_ENVIDO)
                //    return false;
                
                
            case TrucoPlay.CERRARSE:
                if(tp.getPlayer() != playTurn)
                    return false; //no es el turno, no puede ju
                if (estadoActual != PRIMERA_RONDA && estadoActual != SEGUNDA_RONDA && estadoActual != TERCERA_RONDA)
                    return false; //no es posible cerrase si no es en ronda de juego.
                if(statusTable.estaCerrado(playTurnNumber))
                    return false;
                
                if (cantidadDePlayers == 2)
                    return false; /*es lo mismo que irse al mazo*/
                
                break;
                
            case TrucoPlay.CANTO_ENVIDO:
                
                if(tp.getPlayer() != playTurn)
                    return false; //no es el turno, no puede ju
                
                if(estadoActual != RONDA_DE_ENVIDO)
                    return false;
                
                if(!statusTable.tieneEnvido(envidoTurnNumber,tp.getValue()))
                    return false;
                
                break;
                
            case TrucoPlay.FLOR:
                /*VERIFICANDO LA FLOR*/
                if(tp.getPlayer() != playTurn)
                    return false; //no es el turno, no puede ju
                if(!statusTable.puedeCantarFlor(getNumberOfPlayer(playTurn)))
                    return false;
                
                if(!sePuedeCantarFlor)/*ya no se puede cantar flor, por que se canto envido o no es primera ronda etc.*/
                    return false;
                
                if(estadoActual!=PRIMERA_RONDA && estadoActual!=ENVIDO && estadoActual != REAL_ENVIDO && estadoActual != FALTA_ENVIDO)
                    return false; /*fuera de estos estados no se puede cantar*/
                if (statusTable.jugoPrimeraCarta(getNumberOfPlayer(tp.getPlayer())))
                    return false;//el player ya jugo su primera carta
                
                break;
                
            default:
                System.out.println("no esta definido el TrucoPlay de tipo "+tp.getType()+"es un error avisar a la Gente de TrucoGame, Metodo esPosibleJugar(TrucoPlay)");
                return false;
                
        }
        return true;
    }

	/*el player juega una carta*/
	protected void jugarCarta(TrucoPlay tp) throws InvalidPlayExcepcion{ //alguien juega una carta
        
		if (estadoActual != PRIMERA_RONDA && estadoActual != SEGUNDA_RONDA && estadoActual != TERCERA_RONDA)
			throw(new InvalidPlayExcepcion("TrucoHand - jugarCarta(TrucoPlay ) > No se puede jugar carta"));
		if(playTurn != tp.getPlayer())
				throw(new InvalidPlayExcepcion("TrucoHand - jugarCarta(TrucoPlay ) > No es el turno de ese player"));
		if (!statusTable.jugarCarta(playTurnNumber, tp.getCard())) //*****************************funcion que necesito de choco
				throw(new InvalidPlayExcepcion("TrucoHand - jugarCarta(TrucoPlay ) > el player no puede jugar esa carta"));
		game.firePlayEvent(playTurn,tp.getCard(),TrucoEvent.JUGAR_CARTA);
        
        
		nextPlayTurn();
	}    
    
}
