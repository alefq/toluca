package py.edu.uca.fcyt.toluca;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.Vector;


/**
 *
 * @author  Nico
 */
public class ButtonEditor2 extends AbstractCellEditor
			  implements TableCellEditor {
    
    protected JButton button;
    
    /** Creates a new instance of ButtonEditor2 */
    public ButtonEditor2() {
        super();
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
        System.out.println("Estoy en el button editor");

    }
    
    public Component getTableCellEditorComponent(JTable table, Object value,
                   boolean isSelected, int row, int column) {
  
        System.out.println("En el buttonEditor value es=" + value.getClass());
    	
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
       }
    
        button.setText(value.toString());
        return button;
    }

    public Object getCellEditorValue() {
	
        
    	return new String(button.getText());
    }
  
    public boolean stopCellEditing() {
        return super.stopCellEditing();
    }

    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }

}
