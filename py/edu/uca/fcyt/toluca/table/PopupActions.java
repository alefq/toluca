/*
 * ActEnvido.java
 *
 * Created on 27 de junio de 2003, 22:28
 */

package py.edu.uca.fcyt.toluca.table;
import java.awt.event.*;

/**
 *
 * @author  ale
 */
class PopupActions extends javax.swing.AbstractAction 
{
    private PTableListener ptListener;
    int action;
    
    /** Creates a new instance of ActComponentCut */
    public PopupActions
    (
    	PTableListener ptListener, int action, String title
    ) 
    {
    	super(title);
    	this.action = action;
        this.ptListener = ptListener;
    }
    
    public void actionPerformed(ActionEvent actionEvent) 
    {
        System.out.println(this.NAME);
    }
    
}
