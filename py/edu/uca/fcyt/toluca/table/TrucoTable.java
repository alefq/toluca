package py.edu.uca.fcyt.toluca.table;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;

import py.edu.uca.fcyt.game.ChatPanel;
import py.edu.uca.fcyt.toluca.TolucaConstants;
import py.edu.uca.fcyt.toluca.game.TrucoPlayer;
import py.edu.uca.fcyt.toluca.guinicio.TableRanking;

/** Panel principal de juego */
class TrucoTable extends JPanel implements ComponentListener {
    public static final int BUTTON_INICIAR_OK = 0;

    public static final int BUTTON_HECHAR = 1;

    public static final int BUTTON_AYUDA = 2;

    protected Score score; // panel de puntajes

    //	protected PlayTable pTable;

    protected JButton[] buttons = new JButton[] { new JButton("Iniciar"),
            new JButton("Ayuda") };

    protected JLabel jlSaying;

    protected Actions actions;

    private Table table;

    private PlayTable playTable = null; //  @jve:decl-index=0:visual-constraint="232,59"

    private ChatPanel chatPanel = null;

    private JPanel jpLeftPanel = null;

    private JPanel jpCantos = null;

    private JScrollPane jScrollPane = null;

    private TableRanking tableRanking = null;

    /**
     * Construye un TrucoTable con ptListener como listener de eventos de la
     * mesa
     */
    public TrucoTable() {
        initialize();
        inicializar();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.setLayout(new BorderLayout());
        this.add(getPlayTable(), BorderLayout.CENTER);
        this.add(getChatPanel(), java.awt.BorderLayout.SOUTH);
        this.add(getJpLeftPanel(), java.awt.BorderLayout.WEST);
        this.add(getJpCantos(), java.awt.BorderLayout.NORTH);
        this.add(getScore(), java.awt.BorderLayout.EAST);
    }

    /**
     * @return
     */
    private Component getScore() {
        if (score == null) {
            score = new Score(30);
            score.setLayout(new BoxLayout(score, BoxLayout.Y_AXIS));
            score.add(new JLabel(" ------ Puntaje ------ "));
            score.add(new JLabel("    Rojo        "+ (TolucaConstants.isWindowFamily() ? "" : "   ")+"Azul   "));
            score.setBorder(new EtchedBorder());
        }
        return score;
    }

    /**
     *  
     */
    void inicializar() {
        //      crea los componentes
        //		pTable = new PlayTable(getTable());
        //		jpLeftPanel = new JPanel();
        //		jpCantos = new JPanel();
        //		jpChat = new ChatPanel(getTable(), getTable().getPlayer());
        //		score = new Score(30);
        //		jpWatchers = new JPlayers();

        // agrega
        //		jpChat.add(new JLabel("Chat"));

        //		setLayout(new BorderLayout());

        //		add(pTable, BorderLayout.CENTER);
        //		add(jpLeftPanel, BorderLayout.WEST);
        //		add(jpCantos, BorderLayout.NORTH);
        //		add(jpChat, BorderLayout.SOUTH);
        //		add(score, BorderLayout.EAST);

        /*
         * if (getTable().isHost()) { buttons = new JButton[] { new
         * JButton("Iniciar"), new JButton("Echar"), new JButton("Ayuda") }; }
         * else { buttons = new JButton[] { new JButton("Iniciar"), new
         * JButton("Ayuda") }; }
         */
        buttons[0].setEnabled(false);

        //		score.setBorder(new EtchedBorder());
        //		resizeComponents();

        JPanel aux;
        addComponentListener(this);

    }

    public void componentResized(ComponentEvent e) {
        resizeComponents();
    }

    public void componentMoved(ComponentEvent e) {
        // TODO: Add your code here
    }

    public void componentShown(ComponentEvent e) {
        //		resizeComponents();
    }

    public void componentHidden(ComponentEvent e) {
        // TODO: Add your code here
    }

    private void resizeComponents() {
    }

    public JButton getJButton(String text) {
        for (int i = 0; i < buttons.length; i++)
            if (buttons[i].getText().equals(text))
                return buttons[i];

        return null;
    }

    /**
     * @param manager
     */
    public void enableAction(PlayerManager manager, TrucoPlayer actualPlayer) {
        boolean ena = manager.evenTeams() && (manager.getActualChair() == 0)
                && manager.isSitted(actualPlayer);
        buttons[TrucoTable.BUTTON_INICIAR_OK].setEnabled(ena);
    }

    /**
     * @param table
     */
    public void setTable(Table table) {
        this.table = table;
        getPlayTable().setPtListener(getTable());
        getPlayTable().inicializar();
        getChatPanel().setCpc(table);
        getChatPanel().setPlayer(table.getPlayer());
        getJpLeftPanel().add(actions = new Actions(buttons, table, table));
//        ((TableRanking)getTableRanking()).setTable(table);
    }

    public Table getTable() {
        return table;
    }

    /**
     * This method initializes playTable
     * 
     * @return py.edu.uca.fcyt.toluca.table.PlayTable
     */
    public PlayTable getPlayTable() {
        if (playTable == null) {
            playTable = new PlayTable();
        }
        return playTable;
    }

    /**
     * This method initializes chatPanel
     * 
     * @return py.edu.uca.fcyt.game.ChatPanel
     */
    public ChatPanel getChatPanel() {
        if (chatPanel == null) {
            chatPanel = new ChatPanel();
            chatPanel.setBorder(new EtchedBorder());
        }
        return chatPanel;
    }

    /**
     * This method initializes jPanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJpLeftPanel() {
        if (jpLeftPanel == null) {
            jpLeftPanel = new JPanel();
            jpLeftPanel.setLayout(new BoxLayout(jpLeftPanel, BoxLayout.Y_AXIS));
            jpLeftPanel.setBorder(new EtchedBorder());
            //			jpLeftPanel.add(getJpWatchers(), null);
            jpLeftPanel.setPreferredSize(new Dimension(125, 200));
            jpLeftPanel.add(getJScrollPane(), null);
        }
        return jpLeftPanel;
    }

    /**
     * This method initializes jPanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJpCantos() {
        if (jpCantos == null) {
            jpCantos = new JPanel();
            jpCantos.add(jlSaying = new JLabel("Canto: "));
        }
        return jpCantos;
    }

    /**
     * This method initializes jScrollPane
     * 
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getJScrollPane() {
        if (jScrollPane == null) {
            jScrollPane = new JScrollPane();
            jScrollPane.setViewportView(getTableRanking());
        }
        return jScrollPane;
    }

    /**
     * This method initializes tableRanking
     * 
     * @return py.edu.uca.fcyt.toluca.guinicio.TableRanking
     */
    TableRanking getTableRanking() {
        if (tableRanking == null) {
            tableRanking = new TableRanking();       
        }
        return tableRanking;
    }

    /**
     * @param player
     */
    public void addPlayer(TrucoPlayer player) {
        //getJpWatchers().addPlayer(player.getName());
        getTableRanking().addPlayer(player);
    }

    /**
     * @param player
     */
    public void removePlayer(TrucoPlayer player) {
        //getJpWatchers().removePlayer(player.getName());
        getTableRanking().removeplayer(player);
    }
}