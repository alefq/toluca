
package py.edu.uca.fcyt.toluca.guinicio;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;


public class TableModelRanking extends AbstractTableModel{

	private ArrayList datos;
	private final String [] columnNames={"User","Puntaje"};
	
	public TableModelRanking()
	{
		datos=new ArrayList();
		//insertPrueba();
	}
	private void insertPrueba()
	{
		insertRow(new RowRanking("dani",new Integer(1532)));
		insertRow(new RowRanking("cricco",new Integer(1)));
		insertRow(new RowRanking("yo",new Integer(155)));
		insertRow(new RowRanking("dani",new Integer(15322)));
		insertRow(new RowRanking("cricco",new Integer(1000)));
		insertRow(new RowRanking("yo",new Integer(1554)));
		insertRow(new RowRanking("dani",new Integer(15553)));
		insertRow(new RowRanking("cricco",new Integer(1)));
		insertRow(new RowRanking("yo",new Integer(155)));
		insertRow(new RowRanking("dani",new Integer(2153)));
		insertRow(new RowRanking("cricco",new Integer(1)));
		insertRow(new RowRanking("yo",new Integer(1255)));
	}
	public int getRowCount() {

		return datos.size();
	}


	public int getColumnCount() {
		
		return columnNames.length;
	}

	
	public Object getValueAt(int row, int col) {
		
		RowRanking data=(RowRanking) datos.get(row);
		switch(col)
		{
			
			case 0: return data;
			case 1: return data.getRanking();
			default: return null;
		}
	}
	public void insertRow(RowRanking dato)
	{
		int row=getRowCount();
		datos.add(dato);
		fireTableRowsInserted(row,row);
	}
	public void deleteRow(int row) {

		datos.remove(row);
		fireTableRowsDeleted(row, row);

	}
	public String getColumnName(int col)
	{
		return columnNames[col];
	}
	
}
