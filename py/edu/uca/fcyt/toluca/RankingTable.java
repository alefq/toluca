package py.edu.uca.fcyt.toluca;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

import py.edu.uca.fcyt.toluca.game.TrucoPlayer;

import java.util.Vector;

public class RankingTable extends JPanel {
	
	RankingTableModelv2 rtm;
	Vector jugadores = new Vector();
        Vector encabezado = new Vector();
        
	RankingTable(){
            System.out.println("Se instancia el RankingTable");
            encabezado.add(new String(""));
            encabezado.add(new String("Jugador"));
            encabezado.add(new String("Ranking"));        
            
            System.out.println("Se instancia el RankingTableModel-i");
            rtm = new RankingTableModelv2(/*jugadores, encabezado*/);
            System.out.println("Se termina de instanciar");
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            JTable jtable = new JTable(rtm);
            
            jtable.setDefaultRenderer(jtable.getColumnClass(0), new RankingTable.RankingCellRenderer());
            JScrollPane scroll = new JScrollPane(jtable);
            add( scroll );
            setSize( 100,50 );
            setVisible(true);
	}

	void addPlayer(TrucoPlayer player){
            rtm.addPlayer(player);
	}

	void removeplayer(String Nombre){
            //rtm.removeplayer(Nombre);	
	}

	void modifyplayer(String Nombre, int Rating){
            //rtm.modifyplayer(Nombre,Rating);
	}
	
        public class RankingCellRenderer implements TableCellRenderer {
            
            /**  Returns the component used for drawing the cell.  This method is
             *  used to configure the renderer appropriately before drawing.
             *
             * @param	table		the <code>JTable</code> that is asking the
             * 				renderer to draw; can be <code>null</code>
             * @param	value		the value of the cell to be rendered.  It is
             * 				up to the specific renderer to interpret
             * 				and draw the value.  For example, if
             * 				<code>value</code>
             * 				is the string "true", it could be rendered as a
             * 				string or it could be rendered as a check
             * 				box that is checked.  <code>null</code> is a
             * 				valid value
             * @param	isSelected	true if the cell is to be rendered with the
             * 				selection highlighted; otherwise false
             * @param	hasFocus	if true, render cell appropriately.  For
             * 				example, put a special border on the cell, if
             * 				the cell can be edited, render in the color used
             * 				to indicate editing
             * @param	row	        the row index of the cell being drawn.  When
             * 				drawing the header, the value of
             * 				<code>row</code> is -1
             * @param	column	        the column index of the cell being drawn
             *
             */
            public Component getTableCellRendererComponent(JTable table, Object value, 
                                                boolean isSelected, boolean hasFocus, 
                                                int row, int column) {
                JLabel lab = null;
                if (column == 0) {
                    lab = new JLabel("");
                    lab.setBackground(Color.RED);
                } else {
                   lab = new JLabel((String)value);
                }
                return lab;
            }
            
        }
        
}


