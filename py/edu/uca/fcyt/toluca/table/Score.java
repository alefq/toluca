package py.edu.uca.fcyt.toluca.table;

import javax.swing.*;
import java.awt.*;

class Score extends JPanel{
	
	protected static final int ABAJO = 0;
	protected static final int ARRIBA = 1;
	protected static final int IZQUIERDA = 0;
	protected static final int DERECHA = 1;
	protected int ptsTeam1;
	protected int ptsTeam2;
	protected int puntos;
	
	public Score(int totalPts)
	{
		super();
		puntos = totalPts;

		Util.verifParam
		(
			puntos == 30 || puntos == 20, 
			"Parámetro 'totalPts' inválido"
		);
		
	}	

	public void paint(Graphics g)
	{
		
		super.paint(g);
		((Graphics2D) g).setRenderingHint
		(
			RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON
		);
		
		//se pintan los puntajes representados por fósforos
		drawMatch(20,55,g,ptsTeam1);
		drawMatch(70,55,g,ptsTeam2);
		
		//se pintan las lìneas divisorias del puntaje
		drawHorizontalLine(g, puntos);
		drawVerticalLine(g);
		
		g.setColor(Color.black);

	}
	//------------------------------------------
	//este metodo es llamado cuando se actualizan los puntajes		
	//el que se encarga de eso tiene que mandarnos el puntaje 
	//actual de cada equipo
	public void actualizarPuntaje(int ptsTeam1,int ptsTeam2){
		this.ptsTeam1 = ptsTeam1;
		this.ptsTeam2 = ptsTeam2;
		try
		{
			paint(getGraphics());
		}
		catch(NullPointerException ex){}
	}
	
	//------------------------------------------
	//dibujar puntajes con fosforito
	private void drawMatch(int x,int y, Graphics g,int pts){
		int cont1 = 0;
		int cont2 = 0;
		y += 2;
		
		if (pts == 0) return;
		
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
	private void drawVerticalLine(Graphics g)
	{
		int h, w;

		h = (int) getHeight() - 3;
		w = (int) (getWidth() - 3) / 2;
		
		g.setColor(Color.white);
		g.drawLine(w,50,w,h);
		g.setColor(Color.gray);
		g.drawLine(w + 1,50, w + 1,h);
		g.setColor(Color.DARK_GRAY);
		g.drawLine(w + 2,50, w + 2,h);
	}
	
	private void drawHorizontalLine(Graphics g, int p)
	{
		int w, y;

		w = (int) getWidth() - 3;
		
		
		g.setColor(Color.black);
		//dibuja la linea de separacion del titulo
		g.setColor(Color.white);
		g.drawLine(0,48,w,48);
		g.setColor(Color.gray);
		g.drawLine(0,49, w,49);
		g.setColor(Color.DARK_GRAY);
		g.drawLine(0,50,w,50);
		
		
		y = 155 + (p - 20) * 5;
		g.setColor(Color.white);
		g.drawLine(0, y, w, y);
		g.setColor(Color.gray);
		g.drawLine(0, y + 1, w, y + 1);
		g.setColor(Color.DARK_GRAY);
		g.drawLine(0, y + 2, w, y + 2);
	}

}