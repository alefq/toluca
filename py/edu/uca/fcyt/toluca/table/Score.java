package py.edu.uca.fcyt.toluca.table;

import javax.swing.*;
import java.awt.*;

class Score extends JPanel{
	
	static final int ABAJO = 0;
	static final int ARRIBA = 1;
	static final int IZQUIERDA = 0;
	static final int DERECHA = 1;
	private int ptsTeam1=0;
	private int ptsTeam2=0;
	public int puntos;
	
	public Score(int totalPts)
	{
		super();
		puntos = totalPts;
		//PARA CONTROLAR EL PUNTAJE	què hace desps aca?
		Util.verif(puntos == 30 || puntos == 20, "Puntaje inválido");
		
	}	

	public void paint(Graphics g){
		
		((Graphics2D) g).setRenderingHint
		(
			RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON
		);
		
		super.paint(g);
		//se pintan los puntajes representados por fósforos
		drawMatch(20,55,g,ptsTeam1);
		drawMatch(70,55,g,ptsTeam2);
		
		//se pintan las lìneas divisorias del puntaje
		drawVerticalLine(g);
		drawHorizontalLine(g, puntos);
		
		g.setColor(Color.black);

	}
	//------------------------------------------
	//este metodo es llamado cuando se actualizan los puntajes		
	//el que se encarga de eso tiene que mandarnos el puntaje 
	//actual de cada equipo
	public void actualizarPuntaje(int ptsTeam1,int ptsTeam2){
		this.ptsTeam1 = ptsTeam1;
		this.ptsTeam2 = ptsTeam2;
		repaint();
	}
	
	//------------------------------------------
	//dibujar puntajes con fosforito
	private void drawMatch(int x,int y, Graphics g,int pts){
		int cont1 = 0;
		int cont2 = 0;
		y += 2;
		
		label:  //goto
		while(true){ 
				horizontal(x,y+(53*cont2),g,ARRIBA);
				if (++cont1==pts)
					break label;
				vertical(x-11,(y+6)+(53*cont2),g,IZQUIERDA);
				if (++cont1==pts)
					break label;
				horizontal(x-9,(y+35)+(53*cont2),g,ABAJO);
				if (++cont1==pts)
					break label;
				vertical(x+19,(y+15)+(53*cont2),g,DERECHA);
				if (++cont1==pts)
					break label;
				diagonal(x-5,(y+28)+(53*cont2),g);	
				if (++cont1==pts)
					break label;
				cont2++;
		}
	}	
	
	//------------------------------------------
	private void horizontal(int x,int y,Graphics g, int posicion)
	{
		//dibuja el palito en degrade
		for(int i=0;i<5;i++){
			g.setColor(new Color(240-i*20,240-i*20,0));	
			if(posicion == ARRIBA)
				g.drawLine(x,y+i,x+20,y+i);
			else
				g.drawLine(x,y+i,x+20,y+i);
			
		}
		
		//dibuja el borde del palito en negro
		g.setColor(Color.BLACK);
		g.drawRect(x,y,20,5);		
		
		//Dibuja la punta del fosforo
		g.setColor(Color.RED);
		if(posicion == ARRIBA){
			g.fillOval(x-9, y-2, 10, 8);
			//Se coloca el borde de la cabeza del fosforo
			g.setColor(Color.BLACK);
			g.drawOval(x-9,y-2,10,8);
		}
		else{
			g.fillOval(x+27-9, y-2, 10, 8);
			//Se coloca el borde de la cabeza del fosforo
			g.setColor(Color.BLACK);
			g.drawOval(x+27-9,y-1,10,8);
		}
	}

	//------------------------------------------
	private void vertical(int x,int y,Graphics g, int posicion)
	{
		//dibuja el palito en degrade
		for(int i=0;i<5;i++){
			g.setColor(new Color(240-i*20,240-i*20,0));	
			if(posicion == IZQUIERDA)
				g.drawLine(x+i,y,x+i,y+20);
			else
				g.drawLine(x+i,y,x+i,y+20);
		
		}
		
		//dibuja el borde del palito en negro
		g.setColor(Color.BLACK);
		g.drawRect(x,y,5,20);		
		
		//se pinta en degrade
		/*for(int i=0;i<10;i++){
			g.setColor(new Color(240-i*20,0,0));	
			g.drawLine
			(
				(int)(x -2 - Math.sqrt(25-Math.pow(i-5,2))),
				y-3+i, 
				(int)(x -2 + Math.sqrt(25-Math.pow(i-5,2))),y-3+i
			);
		}*/
		
			
		//Dibuja la punta del fosforo
		g.setColor(Color.RED);
		if(posicion == IZQUIERDA){
			g.fillOval(x-2, y+19, 8, 10);
			//Se coloca el borde de la cabeza del fosforo
			g.setColor(Color.BLACK);
			g.drawOval(x-2,y+19,8,10);
		}
		else{
			g.fillOval(x-2, y-9, 8, 10);
			//Se coloca el borde de la cabeza del fosforo
			g.setColor(Color.BLACK);
			g.drawOval(x-2,y-9,8,10);
		}
	}

	//------------------------------------------
	private void diagonal(int x,int y,Graphics g)
	{
		//dibuja el palito en degrade
		for(int i=0;i<5;i++){
			if (i==0 || i == 4)
				g.setColor(Color.black);
			else
				g.setColor(new Color(240-i*20,240-i*20,0));	
			
			g.drawLine(x+i/2,y+i,(x+17)+i/2,y-17+i);
			
			
			if (i >0 && i < 4) g.drawLine(1+x+i/2,y+i,1+(x+17)+i/2,y-17+i);
		}

		//Dibuja la punta del fosforo
		g.setColor(Color.RED);
		g.fillOval(x+14, y-20, 8, 10);

		//Se coloca el borde de la cabeza del fosforo
		g.setColor(Color.BLACK);
		g.drawOval(x+14,y-20,8,10);
	}
	
	//-----------------------------------------
	
	//dibuja las líneas divisorias del puntaje
	private void drawVerticalLine(Graphics g){
		g.setColor(Color.white);
		g.drawLine(50,50,50,365);
		g.setColor(Color.gray);
		g.drawLine(51,50,51,365);
		g.setColor(Color.DARK_GRAY);
		g.drawLine(52,50,52,365);
	}
	
	private void drawHorizontalLine(Graphics g, int p){
		g.setColor(Color.black);
		//dibuja la linea de separacion del titulo
		g.setColor(Color.white);
		g.drawLine(0,48,98,48);
		g.setColor(Color.gray);
		g.drawLine(0,49,49,49);
		g.drawLine(51,49,98,49);
		g.setColor(Color.DARK_GRAY);
		g.drawLine(0,50,49,50);
		g.drawLine(52,50,98,50);
		
		if (p == 20){ 
		
			g.setColor(Color.white);
			g.drawLine(0,155,98,155);
			g.setColor(Color.gray);
			g.drawLine(0,156,49,156);
			g.drawLine(51,156,98,156);
			g.setColor(Color.DARK_GRAY);
			g.drawLine(0,157,49,157);
	   		g.drawLine(52,157,98,157);
	    }
			//g.drawLine(0,155,98,155);
		else{ //p == 30
		
			g.setColor(Color.white);
			g.drawLine(0,208,98,208);
			g.setColor(Color.gray);
			g.drawLine(0,209,49,209);
			g.drawLine(51,209,98,209);
			g.setColor(Color.DARK_GRAY);
			g.drawLine(0,210,49,210);
	   		g.drawLine(52,210,98,210);
	    }
		
		
		
	}

}