package py.edu.uca.fcyt.game;

import javax.print.attribute.*; 
import javax.imageio.*;
import java.io.*;
import java.util.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChatPanel extends JPanel implements ActionListener
{

	public Player jugador;
	public ChatPanelContainer cont;
	public String nombre;
	public static final String cabecera="<html><body>";
	public static final String nuevalinea="<br>";
	public static final String cierre="</body></html>";
	public String cuerpo="";
	HTMLEditorKit eleditor;
	JTextField mens;
	JEditorPane mensajes;
	JScrollPane editor;
	JButton enviar;
		
	public ChatPanel(ChatPanelContainer panelc) {
            this(panelc, new Player("Berniiii"));
        }
	
	public ChatPanel(ChatPanelContainer panelc, Player player)
	{
		
		jugador=player;
                //jugador = new Player("Berni");
		cont=panelc;
		nombre="<font color=blue>"+jugador.getName()+":</font>";
		///////////////////////////////////////////
		JButton enviar= new JButton("Decir");
		enviar.addActionListener(this);
		//
		mens = new JTextField();			 
		mens.setPreferredSize(new Dimension(300,20));
		mens.addActionListener(this);
		//
		mensajes = new JEditorPane();
		eleditor = new HTMLEditorKit();
		mensajes.setEditorKit(eleditor);
		mensajes.setPreferredSize(new Dimension(350,300));
		mensajes.setEditable(false);		
		//
		editor = new JScrollPane();
		editor.setPreferredSize(new Dimension(350, 320));	
		editor.setViewportView(mensajes);
		//
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		//
		JPanel env = new JPanel();
		env.add(enviar);
		env.add(mens);	
		//
		JPanel rec = new JPanel();
		rec.add(editor);
		//
		add(env);
		add(rec);
					
		
	}

	public void showChatMessage(Player player,String htmlMessage)
	{
		cuerpo=cuerpo+nombre+htmlMessage;
		String total=cabecera+cuerpo+cierre;
		mensajes.setText(total);
		cuerpo=cuerpo+nuevalinea;
	 	
	 }
	 
	public void actionPerformed(ActionEvent e)
	{
		String estem=mens.getText();
		cont.sendChatMessage(jugador,estem);
		mens.setText("");
	}
	
	
	
		

}

