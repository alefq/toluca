package py.edu.uca.fcyt.toluca.table;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import py.edu.uca.fcyt.game.ChatPanel;

/** Panel principal de juego*/
class JTrucoTable extends JPanel implements ComponentListener 
{
	// paneles
	protected JPanel jpPlayers; 	// panel de jugadores
	protected ChatPanel jpChat;		// panel de chat
	protected JPanel jpLeftPanel;	// panel izquierdo
	protected Score score;			// panel de puntajes
	protected Watchers jpWatchers;	// observadores
	protected PlayTable pTable;
	protected JButton[] buttons;

	/**
	 * Construye un JTrucoTable con ptListener
	 * como listener de eventos de la mesa
	 */
	public JTrucoTable(Table table)
	{
		// crea los componentes
		pTable = new PlayTable(table);
		jpLeftPanel = new JPanel();
		jpPlayers = new JPanel();
		jpChat = new ChatPanel(table, table.getPlayer());
		score = new Score(30);
		jpWatchers = new Watchers();

		// agrega
		jpPlayers.add(new JLabel("Jugadores"));
//		jpChat.add(new JLabel("Chat"));

		score.setLayout(new BoxLayout(score, BoxLayout.Y_AXIS));
		score.add(new JLabel(" ------ Puntaje ------ "));
		score.add(new JLabel("    Rojo        Azul   "));

		setLayout(new BorderLayout());

		add(pTable, BorderLayout.CENTER);
		add(jpLeftPanel, BorderLayout.WEST);
		add(jpPlayers, BorderLayout.NORTH);
		add(jpChat, BorderLayout.SOUTH);
		add(score, BorderLayout.EAST);

		if (table.isHost())
		{
			buttons = new JButton[]
			{
				new JButton("Iniciar"),
				new JButton("Hechar")
			};
		}
		else
		{
			buttons = new JButton[]
			{
				new JButton("Iniciar")
			};
		}
		
		buttons[0].setEnabled(false);

		JLabel obs = new JLabel("       Personas        ");
		obs.setPreferredSize(new Dimension(100,30));

		jpLeftPanel.setLayout(new BoxLayout(jpLeftPanel, BoxLayout.Y_AXIS));
		jpLeftPanel.add(obs);
		jpLeftPanel.add(jpWatchers);
		jpLeftPanel.add(new Actions(buttons, table));
		jpLeftPanel.setPreferredSize(new Dimension(125,200));

//		pTable.setBorder(new EtchedBorder());
		jpChat.setBorder(new EtchedBorder());
		jpLeftPanel.setBorder(new EtchedBorder());
		jpPlayers.setBorder(new EtchedBorder());
		score.setBorder(new EtchedBorder());
//		resizeComponents();

		JPanel aux;
		addComponentListener(this);
	}

	/** retorna el PlayTable asociado a la mesa */
	public PlayTable getPlayTable()
	{
		return pTable;
	}
	
	public void componentResized(ComponentEvent e) 
	{
		resizeComponents();
	}
	
	public void componentMoved(ComponentEvent e) {
		// TODO: Add your code here
	}

	public void componentShown(ComponentEvent e) 
	{
//		resizeComponents();
	}

	public void componentHidden(ComponentEvent e) {
		// TODO: Add your code here
	}


	private void resizeComponents()
	{
	}
}
