package py.edu.uca.fcyt.toluca.table;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.awt.geom.*;

class Face
{
//	-------------------------------------------------------------	
//	Subclases
//	-------------------------------------------------------------
	public class State
	{
		public static final int BACK = 0;		
		public static final int INVISIBLE = 1;	
		public static final int VISIBLE = 2;	
	}
	
	public class Dir
	{
		public static final int LEFT = 0;
		public static final int CENTER = 1;
		public static final int RIGHT= 2;
	}	

	class Sign
	{
		public static final int MACHO = 0;
		public static final int HEMBRA = 1;
		public static final int ESPADA_7 = 2;
		public static final int ORO_7 = 3; 
		public static final int VAL_3 = 4;
		public static final int VAL_2 = 5;
		public static final int VAL_1 = 6;
		public static final int ENVIDO = 7;
	}
	
	class Pos
	{
		static final int DOWN_CENTER = 0;
		static final int DOWN_RIGHT = 1;
		static final int MID_RIGHT = 2;
		static final int UP_RIGHT = 3;
		static final int UP_CENTER = 4;
		static final int UP_LEFT = 5;
		static final int MID_LEFT = 6;
		static final int DOWN_LEFT = 7;
	}

	static final float OFFSET = .14f;

//  -------------------------------------------------------------	
//	Variables miembros
//  -------------------------------------------------------------
	protected int state = State.VISIBLE; 
	protected int dir = Dir.CENTER;
	protected float x,y;
	protected int playerPosition;
	protected int faceWidth, faceHeight;
	protected BufferedImage Bface;
	protected String playerName;

	protected BufferedImage biOut;
	protected Graphics2D grOut;
	protected int chairBounds[];
	protected int chairRounded;
	protected int chairInnerOffset;
	protected String nameShown;
	protected int nameShownWidth;
	protected int nameShownDescent;

//  -------------------------------------------------------------	
//	Métodos
//  -------------------------------------------------------------	

	/**
	 * Construye la carita del jugador correspondiente en su
	 * lugar correspondiente*/
	public Face(int numPlayer, int cantPlayers, String playerName)
	{ 
		calculatePosicion(numPlayer,cantPlayers);
		setSign(Sign.ORO_7);
		this.playerName = playerName;
	}
	
	
	private void calculatePosicion(int playerPosition, int cant)
	{
		switch(cant)
		{
			case 6:
				if (playerPosition < 2) positionPlayer(playerPosition);
				else if (playerPosition < 5) positionPlayer(playerPosition + 1);
				else positionPlayer(playerPosition + 2);
				break;
			case 4:
				positionPlayer(playerPosition * 2);
				break;
			case 2:
				positionPlayer(playerPosition * 4);
				break;		
		}
	}		
	
	private void positionPlayer(int playerPosition){
		this.playerPosition = playerPosition;
		switch(playerPosition){
			case Pos.DOWN_CENTER:	x = 1;   y = 2-OFFSET;break;
			case Pos.DOWN_RIGHT:	x = 2-OFFSET;   y = 2-OFFSET;break;
			case Pos.MID_RIGHT:		x =	2-OFFSET;   y = 1;break;
			case Pos.UP_RIGHT: 		x = 2-OFFSET; 	 y = OFFSET; break;
			case Pos.UP_CENTER:		x = 1; y = OFFSET; break;
			case Pos.UP_LEFT: 		x = OFFSET;  y = OFFSET; break;
			case Pos.MID_LEFT:		x = OFFSET;  y = 1;break;
			case Pos.DOWN_LEFT:		x = OFFSET;  y = 2-OFFSET;break;
		}		
	}
	
	public void setState(int s){
		state = s;	
	}
	
//_________________________________________________________________________	
	
	public int getState(){
		return state;
	}
	
//_________________________________________________________________________	
	
	public void setSign(int sign){
		
		 Bface = createFace(sign);
//		 paint(biOut);
	}
	
//_________________________________________________________________________	

	public void getSign(){
	
	}
//_________________________________________________________________________	
	
	/*cuando el jugador hace click en un jugador a su izq,der o centro
	el ojo se tiene que mover, en realidad se cambia el archivo de
	cara correspondiente*/
	public void setEyedireccion(int d){
		switch(d){
			case Dir.LEFT:
				if (dir != Dir.LEFT)
					//tiene que buscar el arch correspondiente
				break;
			case Dir.CENTER:
				if (dir != Dir.CENTER)
				
				//tiene que buscar el arch correspondiente				
				break;
			case Dir.RIGHT:
				if (dir != Dir.RIGHT)
					//tiene que buscar el arch correspondiente				
				break;
		}
		dir = d; //se coloca el nuevo valor	
	}
	
	
	public int getEyedireccion(){
		return dir;
	}
	
	
	public void dibujarSeñas(){
	
	}
	
	
	public void paint()
	{
		drawChair();
		//drawFace(Bface, i,0,.5f,new AffineTransform());
		drawName();
		
		/*este esta nomas mientras tanto aca, pero en realidad
		 este metodo tiene que ser llamado nose por quien
		 cada vez que un jugador de una mesa haya cantado alguna
		 seña*/
		//mostrarDialog(i,"Truco",playerPosition);
	}


	public void setOut(BufferedImage biOut)
	{	
	
		float r = .2f;
		int width, height, max;
		
		this.biOut = biOut;
		grOut = biOut.createGraphics();

		// establece el antialiasing de 'grOut' a verdadero
		grOut.setRenderingHint
		(
			RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON
		);

		width = biOut.getWidth() / 2;
		height = biOut.getHeight() / 2;
		max = Math.max(width, height);
		
		chairBounds = new int[]
		{
			(int) ((x-r) * width),
			(int) ((y-r) * height), 
			(int) ((r*2) * width), 
			(int) ((r*2) * height)
		};
		
		chairInnerOffset = (int) (max * .013) + 1;
		chairRounded = (int) (max * .1);
		
		setName(playerName);
	}

	/* Dibuja el asiento blanquito*/
	protected void drawChair()
	{
		grOut.setColor(Color.BLACK);
		grOut.fillRoundRect
		(
			chairBounds[0], 
			chairBounds[1], 
			chairBounds[2], 
			chairBounds[3],
			chairRounded, 
			chairRounded
		);	

		grOut.setColor(Color.WHITE);
		grOut.fillRoundRect
		(	
			chairBounds[0] + chairInnerOffset, 
			chairBounds[1] + chairInnerOffset, 
			chairBounds[2] - chairInnerOffset * 2, 
			chairBounds[3] - chairInnerOffset * 2,
			(int) (chairRounded * .7), 
			(int) (chairRounded * .7)
		);
	}
	
	protected void drawName()
	{
		int x, y;
		
		x = chairBounds[0] + (chairBounds[2] - nameShownWidth) / 2;
		y = (int) (chairBounds[1] + chairBounds[3] - nameShownDescent - chairInnerOffset);
		
		x = Math.max(x, 3);
		x = Math.min(x, biOut.getWidth() - nameShownWidth);

		y = Math.max(y, 0);
		y = Math.min(y, biOut.getHeight() - nameShownDescent);
		
		grOut.setColor(Color.BLACK);	
		grOut.drawString(nameShown, x, y);
	}
	
	public boolean inside(int x, int y)
	{
//		System.out.println("x: " + x + "  y: " + y);
//		System.out.println(chairBounds[0] + " " + chairBounds[1]);
//		System.out.println(chairBounds[2] + " " + chairBounds[3]);
		return 
			x >= chairBounds[0] &&
			y >= chairBounds[1] &&
			x <= chairBounds[0] + chairBounds[2] &&	
			y <= chairBounds[1] + chairBounds[3];
	}
			
//_________________________________________________________________________	
	
	/** Este método es llamado cuando un jugador canta alguna jugada
	 *recibe como argumentos un bufferImage, la accion que debe cantar
	 *y el numero de jugador, o sea su posicion en la mesa*/
	 
	public void mostrarDialog(BufferedImage i,String accion,int playerPosition){
		
		/**Se calcula el punto central del globito que depende del tamaño de 
		 *la mesa y de la posicion en la que se encuentra*/
		 
		int x = (int) (i.getWidth()*.4 * (this.x-1));
		int y = (int) (i.getHeight()*.4 * (this.y-1));
	
		x+=i.getWidth()/2;
		y+=i.getHeight()/2;
		
		
		int width;
		
		/**Aca se elige de acuerdo a la longitud del string el ancho
		 *del globito. Si la longitud es menor 10 hay un ancho por defecto
		 *sino se hace un calculo*/	
		 
		if ((10*accion.length()) < 100)
			width = 100;
		else
			width = 10*(accion.length()-2);
		
		/**Se pone el ancho y alto del globito de acuerdo al tamaño de la mesa*/	
		int scaleWidth = (int)  (width * i.getWidth()/PlayTable.TABLE_WIDTH);
		int scaleHeight = (int) (40 * i.getHeight()/PlayTable.TABLE_HEIGHT);
		
		double angulo = 0; //angulo del triangulito
		//si es 90 y 270 tienen que tener un desplazamiento hacia la 
		//izq y der respectivamente para q no se quede abajo del ovalo
		int desplazamiento = 0;	//sirve para los triangulitos de los costados 	

		Graphics2D g;
		Font nameFont; //para poder ponerle size y estilo a la letra del globito
		
		g = i.createGraphics(); 
		
		
		g.setRenderingHint
		(
			RenderingHints.KEY_ANTIALIASING, 
			RenderingHints.VALUE_ANTIALIAS_ON
		);
					
	
		int[] xPointsDown = {0,(int) (-11 * i.getWidth()/PlayTable.TABLE_WIDTH),(int) (11 * i.getWidth()/PlayTable.TABLE_WIDTH)};
		int[] yPointsDown = {-(scaleHeight/2)-(int) (21 * i.getHeight()/PlayTable.TABLE_HEIGHT),-(scaleHeight/2),-(scaleHeight/2)};
		
		int[] xPointsUp = {0,(int) (-10 * i.getWidth()/PlayTable.TABLE_WIDTH),(int) (10 * i.getWidth()/PlayTable.TABLE_WIDTH)};
		int[] yPointsUp = {-(scaleHeight/2)-(int) (20 * i.getHeight()/PlayTable.TABLE_HEIGHT),-(scaleHeight/2),-(scaleHeight/2)};
	
		int[] x1Points = new int[3];
		int[] y1Points = new int[3];
		int nPoints = 3;


		/**De acuerdo a la posicion del jugador en la mesa
		 *se elige un angulo para q gire el triangulito*/	
		switch(playerPosition){
			case Pos.DOWN_CENTER: 
				/*creo q no tiene q hacer nada*/
				break;
			case Pos.DOWN_RIGHT: //esq inferior derecha
				angulo = (Math.PI*3)/4;
				desplazamiento = 0;
				break;
			case Pos.MID_RIGHT: //ver medio derecho
				angulo = (Math.PI)/2;
				desplazamiento = scaleWidth/5;
				break;
			case Pos.UP_RIGHT: //esquina superior derecha
				angulo = (Math.PI)/4;
				desplazamiento = 0;
				break;
			case Pos.UP_CENTER: //centro arriba
				angulo = 0;
				desplazamiento = 0;
				break;
			case Pos.UP_LEFT://esq sup izq
				angulo = (Math.PI*7)/4;
				desplazamiento = 0;
				break;
			case Pos.MID_LEFT: //medio izq
				angulo = (Math.PI*3)/2;
				desplazamiento = -scaleWidth/5;
				break;
			case Pos.DOWN_LEFT: //esquina inferior izq
				angulo = (Math.PI*5)/4;
				desplazamiento = 0;
				break;
		}
		
		
		/**Se calculan los puntos de acuerdo al tamaño de la mesa y
		 *la posicion del jugador y se dibuja el dialogo negro(el de fondo)*/		
		for (int j=0; j<3; j++){ 
			x1Points[j] =(int) (Math.cos(angulo)*xPointsDown[j] - Math.sin(angulo)*yPointsDown[j]);
			y1Points[j] = (int) (Math.sin(angulo)*xPointsDown[j] + Math.cos(angulo)*yPointsDown[j]);
			x1Points[j] += x+desplazamiento;
			y1Points[j] += y;
		}
	
		//se dibuja el triangulito
		g.setColor(Color.BLACK);
		g.fillPolygon(x1Points,y1Points,nPoints);
		
		//se dibuja la elipse de abajo en color negro
		g.setColor(Color.BLACK);
		g.fillOval((int)x-scaleWidth/2-1,(int)y-scaleHeight/2-1,scaleWidth+2,scaleHeight+2);
		
		
		/**Se calculan los puntos de acuerdo al tamaño de la mesa y
		 *la posicion del jugador y se dibuja el dialogo amarillo*/			
		for (int j=0; j<3; j++){ 
			x1Points[j] =(int) (Math.cos(angulo)*xPointsUp[j] - Math.sin(angulo)*yPointsUp[j]);
			y1Points[j] = (int) (Math.sin(angulo)*xPointsUp[j] + Math.cos(angulo)*yPointsUp[j]);
			x1Points[j] += x+desplazamiento;
			y1Points[j] += y;
		}
		
		//se dibuja el triangulito
		g.setColor(Color.yellow);
		g.fillPolygon(x1Points,y1Points,nPoints);
		
		//se dibuja la elipse	
		g.setColor(Color.yellow.brighter().brighter());
		g.fillOval((int)x-scaleWidth/2,(int)y-scaleHeight/2,scaleWidth,scaleHeight);
		
		//Se coloca la accion al globito
		g.setColor(Color.black.darker());	
		//Tamaño del texto de acuerdo al tamaño de la mesa y estilo de letra
		nameFont = new Font(accion,Font.ROMAN_BASELINE,(int) (16 * i.getWidth()/PlayTable.TABLE_WIDTH));
		g.setFont(nameFont);
		g.drawString(accion,(int)x - (int) ((4*accion.length()-1)* i.getWidth()/PlayTable.TABLE_WIDTH),y+(5*i.getHeight()/PlayTable.TABLE_HEIGHT));
	}
	
	//obtiene la imagen de la carita
	public ImageIcon getFaceImage(int sign)
	{
	
//		return  new ImageIcon("c:\\Powerpuf.bmp");
		return  new ImageIcon("..\\imagenes\\Powerpuf.gif");
		
	}
	
	// dibuja 'bfIn' en 'bfOut' en la posición 
	// (x, y) con un ángulo de rotación 'rotAngle' 
	private void drawFace
	(
		BufferedImage bfIn, BufferedImage bfOut, 
		float rotAngle, float scale,
		AffineTransform afTrans
	)
	{

		int x = (int) (bfOut.getWidth()*.5 * (this.x-1));
		int y = (int) (bfOut.getHeight()*.49 * (this.y-1));
	
		x+=bfOut.getWidth()/2;
		y+=bfOut.getHeight()/2-10;
		
		AffineTransformOp afTransOp;	// Dibujador de la imagen
		int centX, centY;

		// obtiene el centro de 'bfIn'
		centX = bfIn.getWidth() / 2;
		centY =	bfIn.getHeight() / 2;
		
		// limpia la transformación 
		afTrans.setToIdentity();
	
		afTrans.translate
		(
			(int) (x), 
			(int) (y)
		);		
		afTrans.scale(scale, scale);
		afTrans.rotate(rotAngle);

		afTrans.translate
		(
			(int) - centX, 
			(int) - centY
		);
		
		// dibuja la imagen 'bfIn' en 'bfOut
		new AffineTransformOp
		(
			afTrans, AffineTransformOp.TYPE_BILINEAR
		).filter(bfIn, bfOut);
	}

	/**Carga el imageIcon de la carita en un BufferedImage para
	 *la carita
	 */
	private BufferedImage createFace(int sign)
	{
		BufferedImage face;
		
		ImageIcon f = getFaceImage(sign);
//		System.out.println (f.getIconWidth());
//		System.out.println (f.getIconWidth());
		
		face = new BufferedImage
		(	
			f.getIconWidth(),f.getIconHeight(),
			/*(int) (f.getIconWidth()*(biOut.getWidth()/PlayTable.TABLE_WIDTH)),
			(int) (f.getIconHeight()*(biOut.getHeight()/PlayTable.TABLE_HEIGHT)),*/
			BufferedImage.TYPE_3BYTE_BGR
		);		
		
		Util.copyImage(f,face);

		return face;
	}
	
	public void setName(String name)
	{
		FontMetrics fMetrics;
		Font nameFont;
		int nameCharsShown;
		int max;
		char[] nameChars;

		playerName = name;
		
		max = Math.max(biOut.getWidth(), biOut.getHeight());


		nameFont = new Font
		(
			"SansSerif", Font.BOLD, 
			(int) (max * .022)
		);
		grOut.setFont(nameFont);
		fMetrics = grOut.getFontMetrics(nameFont);

		nameChars = playerName.toCharArray();
		
		nameShownWidth = fMetrics.stringWidth(playerName);
		nameShownDescent = fMetrics.getDescent();
		
		nameCharsShown = nameChars.length;

		while(nameShownWidth > max * .14)
		{
			nameShownWidth -= fMetrics.charWidth
			(
				nameChars[--nameCharsShown]
			);
	   	}
	   	nameShown = playerName.substring(0, nameCharsShown);
	}
}