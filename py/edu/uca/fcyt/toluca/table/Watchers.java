package py.edu.uca.fcyt.toluca.table;

import java.awt.*;
import javax.swing.*;

class Watchers extends JScrollPane /*JPanel*/{

	//private JScrollPane scroll;
	private DefaultListModel listModel;
	private JList list;

	private float matiz = 170;
	private float saturacion = 160;
	private float luminosidad = 110;

	public Watchers(){
		super();
		initComponents();

	}

	private void initComponents(){

		listModel = new DefaultListModel();

		list = new JList(listModel);
     	setViewportBorder(
     		BorderFactory.createLineBorder(Color.black));
        getViewport().add(list);
    }


	public void addPlayer(/*Player p*/String name){
		listModel.addElement(name);
	}


	public void removePlayer(/*Player p*/String  name){
		listModel.removeElement(name);
	}



	/*static public void main(String []args){

		Watchers w = new Watchers();
		w.addPlayer("CURIOSO");
		w.addPlayer("Nati");
		w.removePlayer("Nati");
		JFrame jf = new JFrame ();
		jf.getContentPane().add(w);
		jf.pack();
		jf.show();
	}*/


}