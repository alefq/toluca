
package py.edu.uca.fcyt.toluca.guinicio;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;


public class TableRankingRender implements TableCellRenderer{

	private HashMap imagenes;
	private Color colorBack;
	private Color colorSelected;
	
	public TableRankingRender(HashMap imagenes,Color colorBack,Color selected)
	{
		this.imagenes=imagenes;
		this.colorBack=colorBack;
		this.colorSelected=selected;
	}
	public Component getTableCellRendererComponent(JTable jTable, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

		JPanel panel=new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.setBackground(colorBack);
		panel.setOpaque(true);
		if(isSelected)
			panel.setBackground(colorSelected);
		if(col==0)
		{
			
			RowRanking rowRanking=(RowRanking) value;
			
			ImageIcon icon=(ImageIcon) imagenes.get(rowRanking.getRankingStatus());
			panel.add(new JLabel(icon));
			panel.add(new JLabel(rowRanking.getUser()));
			
		}
		else
		{
			JLabel label=new JLabel(value.toString());
			
		}
		return panel;
	}

}
