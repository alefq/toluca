package py.edu.uca.fcyt.toluca;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import py.edu.uca.fcyt.toluca.game.TrucoPlayer;

public class RankingTable extends JPanel {
	
	RankingTableModelv2 rtm;
	Vector jugadores = new Vector();
        Vector encabezado = new Vector();
        
	RankingTable(){
          //  System.out.println("Se instancia el RankingTable");
            encabezado.add(new String("Nivel"));
            encabezado.add(new String("Jugador"));
            encabezado.add(new String("Ranking"));        
            
          //  System.out.println("Se instancia el RankingTableModel-i");
            rtm = new RankingTableModelv2(/*jugadores, encabezado*/);
            //System.out.println("Se termina de instanciar1324123123");
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            JTable jtable = new JTable(rtm);
         
            TableColumn nivelColumn = jtable.getColumn("Nivel");
            nivelColumn.setPreferredWidth(30);
            
            jtable.setDefaultRenderer(jtable.getColumnClass(0), new RankingTable.RankingCellRenderer());
            JScrollPane scroll = new JScrollPane(jtable);
            add( scroll );
            setPreferredSize( new Dimension(200,190) );
            setVisible(true);
	}

	void addPlayer(TrucoPlayer player){
            rtm.addPlayer(player);
	}

	void removeplayer(TrucoPlayer player){
            rtm.removePlayer(player);	
	}

	void modifyplayer(TrucoPlayer player){
            rtm.modifyPlayer(player);
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


