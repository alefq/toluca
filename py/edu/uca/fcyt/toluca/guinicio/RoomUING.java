/* RoomUING.java
 * Created on Sep 10, 2004
 *
 * Last modified: $Date: 2004/12/23 21:14:13 $
 * @version $Revision: 1.10 $ 
 * @author afeltes
 */
package py.edu.uca.fcyt.toluca.guinicio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedInputStream;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;



import py.edu.uca.fcyt.game.ChatPanel;
import py.edu.uca.fcyt.toluca.RoomClient;
import py.edu.uca.fcyt.toluca.game.TrucoPlayer;
import py.edu.uca.fcyt.toluca.table.Table;

/**
 * 
 * @author afeltes
 *  
 */
public class RoomUING extends JApplet {

	//se saca el log4j porque es muy gande para que este en el cliente
//    protected static org.apache.log4j.Logger logger = org.apache.log4j.Logger
//            .getLogger(RoomUING.class);

    public static final String CLAVE_LOGIN = "claveLogin";
    
    private javax.swing.JPanel jContentPane = null;

    private JPanel panelPrincipal = null; //  @jve:decl-index=0:visual-constraint="25,16"

    private PanelGradiente panelTitle = null; //  @jve:decl-index=0:visual-constraint="10,220"

    /** el directorio relativo de donde se sacan las imagenes */
    public static final String IMAGE_DIR = "/py/edu/uca/fcyt/toluca/images/inicio/";

    private JPanel panelConMargen = null;

    public static final Color COLOR_DE_FONDO = new Color(80, 120, 60);

    int margen = 10;

    private JPanel panelPaneles = null;

    private JPanel panelControl = null;

    private JPanel panelCentral = null;

    private JPanel scrollRanking = null;

    private PanelComandos panelComandos = null;

    private JScrollPane scrollGame = null;

    private TableGame tableGame = null;

    private JPanel panelSur = null;

    private ChatPanel chatPanel = null;

    private JScrollPane jScrollPane = null;

    private TableRanking tableRanking = null;

    private RoomClient roomClient;

    /**
     * This method initializes panelPrincipal
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getPanelPrincipal() {
        if (panelPrincipal == null) {
            panelPrincipal = new JPanel();
            panelPrincipal.setLayout(new BorderLayout());
            //            panelPrincipal.add(getPanelConMargen(),
            // java.awt.BorderLayout.CENTER);
            panelPrincipal.add(getPanelTitle(), java.awt.BorderLayout.NORTH);
            panelPrincipal.add(getPanelConMargen(),
                    java.awt.BorderLayout.CENTER);
            //            panelPrincipal.add(getPanelTitle(), java.awt.BorderLayout.NORTH);
        }
        return panelPrincipal;
    }

    
    /* (non-Javadoc)
	 * @see java.applet.Applet#stop()
	 * 
	 * Programación porcina aa / af el 23 de Diciembre de 2004
	 * Cerramos el socket al cerrar el applet.
	 * 
	 */
	public void stop() {
		roomClient.cerrarConexion();
		super.stop();
	}	
    /**
     * This method initializes panelTitle
     * 
     * @return py.edu.uca.fcyt.toluca.guinicio.PanelGradiente
     */
    private PanelGradiente getPanelTitle() {
        if (panelTitle == null) {
            ImageIcon logo = new ImageIcon(getClass().getResource(RoomUING.IMAGE_DIR + "LogoSinFondo.gif"));//RoomUING.loadImage("LogoSinFondo.gif");
            panelTitle = new PanelGradiente();
            panelTitle.setLogo(logo);
            panelTitle.setStartColor(new Color(50, 255, 50));
            panelTitle.setLargo(50);
            panelTitle.setNombre("Dani Cricco");
            
            panelTitle.setMinimumSize(new Dimension(logo.getIconWidth(), logo
                    .getIconHeight()));

            panelTitle.setSize(543, 71);
        }
        return panelTitle;
    }

    /**
     * This method initializes panelConMargen
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getPanelConMargen() {
        if (panelConMargen == null) {
            panelConMargen = new JPanel();
            panelConMargen.setLayout(new BorderLayout());
            panelConMargen.setOpaque(true);
            panelConMargen.setBackground(RoomUING.COLOR_DE_FONDO);
            panelConMargen.add(getPanelPaneles(), java.awt.BorderLayout.CENTER);
            panelConMargen.add(Box.createRigidArea(new Dimension(0, margen)),
                    BorderLayout.NORTH);
            panelConMargen.add(Box.createRigidArea(new Dimension(0, margen)),
                    BorderLayout.SOUTH);
            panelConMargen.add(Box.createRigidArea(new Dimension(margen, 0)),
                    BorderLayout.EAST);
            panelConMargen.add(Box.createRigidArea(new Dimension(margen, 0)),
                    BorderLayout.WEST);
        }
        return panelConMargen;
    }

    /**
     * This method initializes panelPaneles
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getPanelPaneles() {
        if (panelPaneles == null) {
            panelPaneles = new JPanel();
            panelPaneles.setLayout(new BorderLayout());
            panelPaneles.add(getPanelControl(), java.awt.BorderLayout.WEST);
            panelPaneles.add(getPanelCentral(), java.awt.BorderLayout.CENTER);
            panelPaneles.add(getScrollRanking(), java.awt.BorderLayout.EAST);
        }
        return panelPaneles;
    }

    /**
     * This method initializes panelControl
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getPanelControl() {
        if (panelControl == null) {
            panelControl = new JPanel();
            panelControl.setLayout(new BorderLayout());
            panelControl.add(getPanelComandos(), java.awt.BorderLayout.CENTER);
            panelControl.setBackground(RoomUING.COLOR_DE_FONDO);
            panelControl.setOpaque(true);
            panelControl.add(Box.createRigidArea(new Dimension(margen, 0)),BorderLayout.EAST);
        }
        return panelControl;
    }

    /**
     * This method initializes panelCentral
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getPanelCentral() {
        if (panelCentral == null) {
            panelCentral = new JPanel();
            panelCentral.setLayout(new BorderLayout());
            panelCentral.add(getScrollGame(), java.awt.BorderLayout.CENTER);
            panelCentral.add(getPanelSur(), java.awt.BorderLayout.SOUTH);
        }
        return panelCentral;
    }

    /**
     * This method initializes scrollRanking
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getScrollRanking() {
        if (scrollRanking == null) {
            scrollRanking = new JPanel();
            scrollRanking.setLayout(new BorderLayout());
            //scrollRanking.setPreferredSize(new Dimension(150, 2000));
            scrollRanking.add(Box.createRigidArea(new Dimension(margen,0)),BorderLayout.WEST);
            scrollRanking.setOpaque(true);
            scrollRanking.setBackground(RoomUING.COLOR_DE_FONDO);
            scrollRanking.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
        }
        return scrollRanking;
    }

    /**
     * This method initializes panelComandos
     * 
     * @return py.edu.uca.fcyt.toluca.guinicio.PanelComandos
     */
    private PanelComandos getPanelComandos() {
        if (panelComandos == null) {
            panelComandos = new PanelComandos();
          //  panelComandos.add(Box.createRigidArea(new Dimension(margen, 0)),
            //        BorderLayout.EAST);
            panelComandos.setBackground(RoomUING.COLOR_DE_FONDO);
            panelComandos.setTableGame(getTableGame());
        }
        return panelComandos;
    }

    /**
     * This method initializes scrollGame
     * 
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getScrollGame() {
        if (scrollGame == null) {
            scrollGame = new JScrollPane();
            scrollGame.setViewportView(getTableGame());
        }
        return scrollGame;
    }

    /**
     * This method initializes tableGame
     * 
     * @return py.edu.uca.fcyt.toluca.guinicio.TableGame
     */
    public TableGame getTableGame() {
        if (tableGame == null) {
            tableGame = new TableGame();
        }
        return tableGame;
    }

    /**
     * This method initializes panelSur
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getPanelSur() {
        if (panelSur == null) {
            panelSur = new JPanel();
            panelSur.setLayout(new BorderLayout());
            panelSur.setOpaque(true);
            panelSur.setBackground(RoomUING.COLOR_DE_FONDO);
            panelSur.add(getChatPanel(), java.awt.BorderLayout.CENTER);
            panelSur.add(Box.createRigidArea(new Dimension(0, margen)),
                    BorderLayout.NORTH);
            //panelSur.add(getChatPanel(), BorderLayout.CENTER);
        }
        return panelSur;
    }

    /**
     * This method initializes chatPanel
     * 
     * @return py.edu.uca.fcyt.game.ChatPanel
     */
    public ChatPanel getChatPanel() {
        if (chatPanel == null) {
            chatPanel = new ChatPanel();
        }
        return chatPanel;
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
            //jScrollPane.setPreferredSize(new Dimension(150, 2000));
        }
        return jScrollPane;
    }

    /**
     * This method initializes tableRanking
     * 
     * @return py.edu.uca.fcyt.toluca.guinicio.TableRanking
     */
    public TableRanking getTableRanking() {
        if (tableRanking == null) {
            tableRanking = new TableRanking();
        }
        return tableRanking;
    }

    public static void main(String[] args) {
    }

    /**
     * This is the default constructor
     */
    public RoomUING() {
        super();
        //init();
       
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    public void init() {
//        DOMConfigurator.configure(System.getProperty("user.dir")
//                + System.getProperty("file.separator") + "log.xml");
        loadAppletParameters();
        this.setSize(750, 470);
        this.setContentPane(getJContentPane());
        login();
    }

    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private javax.swing.JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new javax.swing.JPanel();
            jContentPane.setLayout(new java.awt.BorderLayout());
            jContentPane.add(getPanelPrincipal(), java.awt.BorderLayout.CENTER);
        }
        return jContentPane;
    }

    protected void loadAppletParameters() {
        //Get the applet parameters.
        //String at = getParameter("img");
        //  dir = (at != null) ? at : "/images";
        try {
            String imagedir = getParameter("IMAGEDIR");
           // logger.debug("La dire de imagenes es " + imagedir);
        } catch (Exception e) {
        }
    }

    protected static ImageIcon loadImage(String image) {
        /*ImageIcon ret = new ImageIcon(image.getClass().getResource(image));
        return ret;*/
        String path = IMAGE_DIR + image;
        int MAX_IMAGE_SIZE = 10000; //Change this to the size of
        //your biggest image, in bytes.
        int count = 0;
        BufferedInputStream imgStream = new BufferedInputStream(path.getClass()
                .getResourceAsStream(path));
        if (imgStream != null) {
            byte buf[] = new byte[MAX_IMAGE_SIZE];
            try {
                count = imgStream.read(buf);
                imgStream.close();
            } catch (java.io.IOException ioe) {
                System.out.println("Couldn't read stream from file: " + path);
                return null;
            }
            if (count <= 0) {
            	System.out.println("Empty file: " + path);
                return null;
            }
            return new ImageIcon(Toolkit.getDefaultToolkit().createImage(buf));
        } else {
        	System.out.println("Couldn't find file: " + path);
            return null;
        }
    }

    public void login() {

        // El login lo escribo por n-ï¿½sima vez - CVS urgeeeeente!
        py.edu.uca.fcyt.util.LoginDialog ld = new py.edu.uca.fcyt.util.LoginDialog(
                JOptionPane.getRootFrame(), true);
        ld.show();
        roomClient = new RoomClient(this, ld.getUsername(), ld.getPassword());
        getChatPanel().setCpc(roomClient);
        
        //System.out.println("El chatpanel del roomui es "+chatPanel);
        roomClient.setChatPanel(getChatPanel());
        roomClient.setMainTable(getTableGame());
        roomClient.setRankTable(getTableRanking());
        getPanelComandos().setRoomClient(roomClient);
        // esto es para probar nomas, sacar despues!!
        //Table table = new Table();
        //table.setTableNumber(5);
        //mainTable.insertarFila(table);

        //jpChatRanking.validate();
        roomClient.fireLoginRequested(ld.getUsername(), ld.getPassword());
    }

    /**
     * @param chatPanel2
     */
    public void addChatPanel(ChatPanel chatPanel2) {
        setChatPanel(chatPanel2);
    }
    /**
     * @param chatPanel The chatPanel to set.
     */
    public void setChatPanel(ChatPanel chatPanel) {
        this.chatPanel = chatPanel;
    }
    public void setOwner(TrucoPlayer trucoPlayer)
    {
    	panelTitle.setNombre(trucoPlayer.getFullName());
    }
    public void removeTable(Table table)
    {
    	tableGame.eliminarFila(table.getTableNumber());
    }
} //  @jve:decl-index=0:visual-constraint="10,30"
