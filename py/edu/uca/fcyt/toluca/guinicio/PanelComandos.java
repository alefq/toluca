package py.edu.uca.fcyt.toluca.guinicio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import py.edu.uca.fcyt.toluca.RoomClient;

/**
 * @author dani
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PanelComandos extends JPanel{

	private RoomClient roomClient = null;
	private TableGame tableGame = null;
	private JButton botonJugar;
	private JButton botonUnirse;
	private JButton botonSalir;
	private JButton botonAyuda;
	private JLabel puntaje;
	public static final Color COLOR_DE_FONDO=new Color(235,238,183);
	
	public PanelComandos()
	{
	    initComponents();
	}
	public PanelComandos(ImageIcon iconJugar,ImageIcon control,ImageIcon salir,ImageIcon puntaje)
	{
		
		botonJugar=new JButton(iconJugar);
		
		botonUnirse=new JButton(iconJugar);
		botonSalir=new JButton(salir);
		botonAyuda=new JButton(control);
		this.puntaje=new JLabel(puntaje);
		botonJugar.setBackground(PanelComandos.COLOR_DE_FONDO);
		botonUnirse.setBackground(PanelComandos.COLOR_DE_FONDO);
		botonSalir.setBackground(PanelComandos.COLOR_DE_FONDO);
		botonAyuda.setBackground(PanelComandos.COLOR_DE_FONDO);
		setPanel();
	}
	private JPanel getPanelBoton(JButton button,String text)
	{
		JPanel panel=new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.setBackground(PanelComandos.COLOR_DE_FONDO);
		panel.add(button);
		//panel.add(new JLabel(text));
		return  panel;
	}
	private JPanel getPanelBotonesJugar()
	{
		JPanel panel=new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.add(getPanelBoton(botonJugar,"Crear mesa"));
		panel.add(getPanelBoton(botonUnirse,"Unirse"));
		return panel;
	}
	private JPanel getPanelControlGral()
	{
		JPanel panel=new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.add(getPanelBoton(botonAyuda,"Ayuda"));
		panel.add(getPanelBoton(botonSalir,"Salir"));
		return panel;
	}
	private JPanel getPuntaje()
	{
		JPanel panel=new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.add(Box.createVerticalGlue());
		panel.setBackground(PanelComandos.COLOR_DE_FONDO);
		panel.setOpaque(true);
			JPanel center=new JPanel();
			center.setLayout(new FlowLayout(FlowLayout.CENTER));
			center.add(puntaje);
			center.setBackground(PanelComandos.COLOR_DE_FONDO);
			center.setOpaque(true);
		panel.add(center);
		return panel;
		
		
	}
	private void setPanel()
	{
		setBackground(PanelComandos.COLOR_DE_FONDO );
		setOpaque(true);
		setLayout(new BorderLayout());
		
		
		add(getPanelBotonesJugar(),BorderLayout.NORTH);
		
		
		add(getPuntaje(),BorderLayout.CENTER);
		add(getPanelControlGral(),BorderLayout.SOUTH);
		
		
		
	}
	
	private void initComponents()
	{	    
	    ImageIcon icon = RoomUI.loadImage("btnCrearMesa.gif");
	    ImageIcon icon2 = RoomUI.loadImage("btnUnirse.gif");
        ImageIcon controlIcon = RoomUI.loadImage("btnAyuda.gif");
        ImageIcon salirIcon = RoomUI.loadImage("btnSalir.gif");
        ImageIcon puntajeIcon = RoomUI.loadImage("puntaje.gif");
        botonJugar=new JButton(icon);
		botonUnirse=new JButton(icon2);
		botonSalir=new JButton(salirIcon);
		botonAyuda=new JButton(controlIcon);
		this.puntaje=new JLabel(puntajeIcon);
		botonJugar.setBackground(PanelComandos.COLOR_DE_FONDO);
		botonJugar.addActionListener(new java.awt.event.ActionListener() { 
			public void actionPerformed(java.awt.event.ActionEvent e) {    
				botonJugarActionPerformed(e);
			}
		});
		botonUnirse.setBackground(PanelComandos.COLOR_DE_FONDO);
		botonSalir.setBackground(PanelComandos.COLOR_DE_FONDO);
		botonAyuda.setBackground(PanelComandos.COLOR_DE_FONDO);
		botonUnirse.addActionListener(new java.awt.event.ActionListener() { 
			public void actionPerformed(java.awt.event.ActionEvent e) {    
				botonUnirseActionPerformed(e);
			}
		});
		setPanel();
	}
    /**
     * @param e
     */
    protected void botonJugarActionPerformed(ActionEvent e) {
        getRoomClient().createTableRequest();
    }
    /**
     * @param e
     */
    protected void botonUnirseActionPerformed(ActionEvent e) {
        int numeroDeFila = getTableGame().getSelectedRow();
        //System.out.println("numeroDeFila=" + numeroDeFila);
        if (numeroDeFila >= 0) {
            numeroDeFila = getTableGame().getNumeroDeMesa(numeroDeFila);
           // System.out.println("////////////// JOINNN /////////////");
            //System.out.println("Se presiono el join..." + numeroDeFila);
            roomClient.joinTableRequest(numeroDeFila);
        }        
    }
	
    /**
     * @return Returns the roomClient.
     */
    public RoomClient getRoomClient() {
        return roomClient;
    }
    /**
     * @param roomClient The roomClient to set.
     */
    public void setRoomClient(RoomClient roomClient) {
        this.roomClient = roomClient;
    }
    /**
     * @return Returns the tableGame.
     */
    public TableGame getTableGame() {
        return tableGame;
    }
    /**
     * @param tableGame The tableGame to set.
     */
    public void setTableGame(TableGame tableGame) {
        this.tableGame = tableGame;
    }
}
