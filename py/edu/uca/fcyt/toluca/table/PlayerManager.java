package py.edu.uca.fcyt.toluca.table;

import java.util.Vector;

import py.edu.uca.fcyt.toluca.game.TrucoPlayer;

/** 
 * Maneja la posicion de los jugadores en la mesa
 */
class PlayerManager
{

	protected TrucoPlayer actualPlayer;
	protected Vector players;/*Vector de referencia de 
										jugadores cuyo subindice
										corresponde al numero de
										su silla*/
	private boolean started = false;
	
	
	/** Crea un PlayerManager */
	public PlayerManager(int playerCount)
	{
		players = new Vector(playerCount);//vector de referencias 
													   //de los players		
		players.setSize(playerCount);
	}
	public PlayerManager()
	{
		
	}
	
	/** 
	 * Sienta al jugador en un numero 
	 * de silla correspondiente 
	 */
	public void sitPlayer(TrucoPlayer p,int chair)
	{
		if (players.contains(p))
			throw new TableException("Jugador ya est� sentado");

	 	if (getPlayer(chair) != null)
 			throw new TableException("Silla ocupada");

		players.set(chair, p);
	}

	/** 
	 * Para al jugador en una silla
	 * @param chair		Silla
	 */
	public TrucoPlayer standPlayer(int chair)
	{
		TrucoPlayer player;
		
		player = getPlayer(chair);
	 	if (player == null)	throw new TableException("Silla vac�a");
 			
 		if (actualPlayer == player) actualPlayer = null; 

		players.set(chair, null);
		return player;
	}
	
	public void setActualPlayer(TrucoPlayer p)
	{
		Util.verifParam(p != null, "Par�metro 'p' nulo");
		Util.verif(players.contains(p), "Jugador " + p.getName() + " no agregado");
		
		actualPlayer = p;
	}
	
	public int getActualChair()
	{
		if (actualPlayer == null) return 0;
		else return getChair(actualPlayer);
	}
		
	
	
	/** 
	 * Reubica a los jugadores de tal manera que ocupen todas
	 * las sillas (saca las sillas sobrantes).
	 */
	public void startGame()
	{
		int[] teamCount; 	// cantidad de jugadores de cada team
		Vector[] teams; 		// equipos
		Object player;
		
		// crea el vector de 2 equipos
		teams = new Vector[]
		{
			new Vector(3), new Vector(3)
		};

		for (int i = 0; i < players.size(); i++)
		{
			player = players.get(i);
			if (player != null)	teams[i % 2].add(player);
		}

		if (teams[0].size() == teams[1].size())
		{
			players.clear();
			for (int i = 0; i < teams[0].size(); i++)
			{
				players.add(teams[0].get(i));
				players.add(teams[1].get(i));
			}
				
			started = true;
		} 
		else throw new IllegalStateException
		(
			"Equipos no parejos: Equipo 0 tiene " + teams[0].size()
			+ ", equipo 1 tiene " + teams[1].size()
		);
	}
	
	/**
     * Retorna la cantidad de jugadores en cada equipo
     * @return 	Un vector de 2 enteros, donde el primero
     *			representa a la cantidad de jugadores del	
     *			team 0, y el segundo del team 1
     */
	private int[] getTeamCount()
	{
		int[] teamCount; 
		
		teamCount = new int[2]; 
		
		for (int i = 0; i < getPlayerCount(); i++)
		{
			teamCount[i%2] += (getPlayer(i) != null) ? 1 : 0;
		}
		
		return teamCount;
	}
	
	/**
     * Retorna verdadero si los equipos est�n parejos
     */
    public boolean evenTeams()
    {
    	int[] teamCount;
    	
    	teamCount = getTeamCount();
    	return teamCount[0] == teamCount[1] && teamCount[0] > 0;
    }
	
	/* Te devuelve la referencia de un player de acuerdo
	 * al lugar donde esta sentado, si no hay nadie sentado
	 *alli te devuelve null */
	 
	public TrucoPlayer getPlayer(int chair)
	{
		// verificaciones
		return (TrucoPlayer) players.get(chair);
	}
	
	public int getChair(TrucoPlayer player)
	{
		return players.indexOf(player);
	}
	
	
	/* Devuelve la posicion relativa de un jugador
	 * en relacion al jugador que esta enfrente a la pantalla.
	 * Se le manda el lugar de la silla en donde eligi� sentarse
	 * y de acuerdo a algunos calculos se le coloca en la posicion
	 * correspondiente para que el jugador actual lo vea
	 */
	public int getPos(int chair)
	{	
		if (started)
			return (getPlayerCount() + chair - getActualChair()) 
				% getPlayerCount();
		else
			return chair;
	}
	
	/**
     * Retorna la posici�n en la cual est� <code>player</code>.
     * Si no est� sentado, retorna -1
     */
	public int getPos(TrucoPlayer player)
	{
		return getPos(getChair(player));
	}

	public int getChair(int pos)
	{
		if (started)
			return (getActualChair() + pos) % getPlayerCount();
		else
			return pos;
	}
	
	public int getPlayerCount() { return players.size(); }
	
	/**
     * Retorna verdadero si 'player' est� sentado
     */
	public boolean isSitted(TrucoPlayer player)
	{
		return players.contains(player);
	}
	/**
	 * @return Returns the players.
	 */
	public Vector getPlayers() {
		return players;
	}
	/**
	 * @param players The players to set.
	 */
	public void setPlayers(Vector players) {
		this.players = players;
	}
	/**
	 * @return Returns the started.
	 */
	public boolean isStarted() {
		return started;
	}
	/**
	 * @param started The started to set.
	 */
	public void setStarted(boolean started) {
		this.started = started;
	}
	/**
	 * @return Returns the actualPlayer.
	 */
	public TrucoPlayer getActualPlayer() {
		return actualPlayer;
	}
}