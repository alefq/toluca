package py.edu.uca.fcyt.toluca.table;

import javax.swing.*;
import java.util.EventListener;
import java.awt.*;
import javax.swing.border.*;

/** Panel principal de juego*/
class JTrucoTable extends JPanel
{
	// paneles
	private JPanel
		jpPlayers,	// panel de jugadores
		jpChat;		// panel de chat

	public JPanel jpLeftPanel;		// panel de botones
	protected Score score;		// panel de puntajes
	public Watchers jpWatchers;	// observadores
	public PlayTable pTable;

	/**
	 * Construye un JTrucoTable con ptListener
	 * como listener de eventos de la mesa
	 */
	public JTrucoTable(PTableListener ptListener)
	{
		// crea los componentes
		pTable = new PlayTable(ptListener);
		jpLeftPanel = new JPanel();
		jpPlayers = new JPanel();
		jpChat = new JPanel();
		score = new Score(30);
		jpWatchers = new Watchers();

		// agrega
		jpPlayers.add(new JLabel("Jugadores"));
		jpChat.add(new JLabel("Chat"));

		score.setLayout(new BoxLayout(score, BoxLayout.Y_AXIS));
		score.add(new JLabel(" ---- Puntaje ---- "));
		score.add(new JLabel(" Equipo1   Equipo2 "));


		setLayout(new BorderLayout());

		add(pTable, BorderLayout.CENTER);
		add(jpLeftPanel, BorderLayout.WEST);
		add(jpPlayers, BorderLayout.NORTH);
		add(jpChat, BorderLayout.SOUTH);
		add(score, BorderLayout.EAST);

		JLabel obs = new JLabel("Observadores");
		obs.setPreferredSize(new Dimension(100,30));

		jpLeftPanel.setLayout(new BoxLayout(jpLeftPanel, BoxLayout.Y_AXIS));
		jpLeftPanel.add(obs);
		jpLeftPanel.add(jpWatchers);
		jpLeftPanel.add(new Actions());
		jpLeftPanel.setPreferredSize(new Dimension(125,200));

		pTable.setBorder(new EtchedBorder());
		jpChat.setBorder(new EtchedBorder());
		jpLeftPanel.setBorder(new EtchedBorder());
		jpPlayers.setBorder(new EtchedBorder());
		score.setBorder(new EtchedBorder());

		JPanel aux;

	}

	/** retorna el PlayTable asociado a la mesa */
	public PlayTable getPlayTable()
	{
		return pTable;
	}
}