package py.edu.uca.fcyt.toluca;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;


public class RankingTable extends JPanel {
	
	RankingTableModel rtm = new RankingTableModel();
	
	RankingTable(){
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            JTable jtable = new JTable(rtm);
            JScrollPane scroll = new JScrollPane(jtable);
            add( scroll );
            setSize( 100,50 );
            setVisible(true);
	}

	void addPlayer(String Nombre, int Rating){
            rtm.addPlayer(Nombre,Rating);
	}

	void removeplayer(String Nombre){
            rtm.removeplayer(Nombre);	
	}

	void modifyplayer(String Nombre, int Rating){
            rtm.modifyplayer(Nombre,Rating);
	}
	
}


