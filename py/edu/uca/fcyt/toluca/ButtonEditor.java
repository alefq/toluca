package py.edu.uca.fcyt.toluca;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.Vector;


public class ButtonEditor extends AbstractCellEditor
						  implements TableCellEditor {
	
  protected JButton button;
  protected JLabel  etiqueta;
  private boolean   isPushed;
  private static Vector tableList;
  boolean   isInTable;
  String label;
  String valueCopy;
  

  
  public ButtonEditor() {
    super();
    button = new JButton();
    etiqueta = new JLabel();
    tableList = new Vector();
    button.setOpaque(true);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        fireEditingStopped();
      }
    });

  }

  public Component getTableCellEditorComponent(JTable table, Object value,
                   boolean isSelected, int row, int column) {
  
    Component comp = etiqueta;
    Integer fila = new Integer(row);
    
    System.out.println("fila= " +fila);
    System.out.println("value=" +value);

	valueCopy = value.toString();
	
	if (isSelected) {
      button.setForeground(table.getSelectionForeground());
      button.setBackground(table.getSelectionBackground());
    } else {
      button.setForeground(table.getForeground());
      button.setBackground(table.getBackground());
    }
 
      label = "nombre";
	
	/*
	 * controla si un jugador ya esta jugando en una mesa
	 * si no estaba jugando ahi, se le agrega
     */
    isInTable = tableList.contains(fila);
    System.out.println("isIntable="+isInTable);

	if(!isInTable)
		tableList.add(fila);	
	
	/* 
	 * si presionó alguno de los botones de "Jugar"
     */
    if( column>1 && column<8){
    	
    	if (!isInTable) {
            etiqueta.setText(label);
            comp = etiqueta;
        } else {
            button.setText(value.toString());
            comp = button;
        }
    }

	/* 
	 * si presiono "Observar"
	 * se controla que quien quiere observar, no este jugando
	 */
	if( column==1 ){

		String aux = new String();

		if(!isInTable){
			aux = table.getValueAt(row,8).toString();
			aux = aux.concat(", ");
			aux = aux.concat("nombre");
			
			table.setValueAt(aux, row,8);
			label = "Observar";
			button.setText("Observar");
            comp = button;
		}
	}
	
    isPushed = true;
    return comp;
  }

  public Object getCellEditorValue() {
	
    if (isPushed)
    	if(isInTable)
      		label = valueCopy;
	
    isPushed = false;
	return new String(label);
  }
  
  public boolean stopCellEditing() {
    isPushed = false;
    return super.stopCellEditing();
  }

  protected void fireEditingStopped() {
    super.fireEditingStopped();
  }
}

