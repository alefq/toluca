package py.edu.uca.fcyt.toluca.table;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.awt.geom.*;
import java.util.*;

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
	protected int nameShownAscent;
	protected float triAng;
	protected float triOffset;
	protected Font font;
	protected Color borderColor;
	protected String balloonText;
	protected AffineTransform afTrans;

//  -------------------------------------------------------------	
//	M�todos
//  -------------------------------------------------------------	

	/**
	 * Construye la carita del jugador correspondiente en su
	 * lugar correspondiente*/
	public Face
	(
		int numPlayer, int cantPlayers, 
		String playerName, Color borderColor
	)
	{ 
		int max;
		
		calculatePosicion(numPlayer,cantPlayers);
		this.playerName = playerName;
		this.borderColor = borderColor;
		afTrans = new AffineTransform();
		setSign(Sign.ESPADA_7);
	}
	
	
	private void calculatePosicion(int playerPosition, int cant)
	{
		switch(cant)
		{
			case 6:
				if (playerPosition < 2) setPosition(playerPosition);
				else if (playerPosition < 5) setPosition(playerPosition + 1);
				else setPosition(playerPosition + 2);
				break;
			case 4:
				setPosition(playerPosition * 2);
				break;
			case 2:
				setPosition(playerPosition * 4);
				break;		
		}
	}		
	
	protected void setBorderColor(Color color)
	{
		borderColor = color;
	}
	
	protected void setPosition(int playerPosition)
	{
		this.playerPosition = playerPosition;
		
		switch(playerPosition)
		{
			case Pos.DOWN_CENTER:	
				x = 1; y = 2-OFFSET;
				triAng = (float) Math.PI;
				triOffset = 0;
				break;
			case Pos.DOWN_RIGHT:	
				x = 2-OFFSET; y = 2-OFFSET;
				triAng = (float) ((Math.PI*3)/4);
				triOffset = 0;
				break;
			case Pos.MID_RIGHT:
				x =	2-OFFSET; y = 1;
				triAng = (float) ((Math.PI)/2);
				triOffset = 0; //scaleWidth/5;
				break;
			case Pos.UP_RIGHT:
				x = 2-OFFSET;
				y = OFFSET; 
				triAng = (float) ((Math.PI)/4);
				triOffset = 0;
				break;
			case Pos.UP_CENTER:
				x = 1; y = OFFSET; 
				triAng = 0;
				triOffset = 0;
				break;
			case Pos.UP_LEFT: 
				x = OFFSET; y = OFFSET; 
				triAng = (float) ((Math.PI*7)/4);
				triOffset = 0;
				break;
			case Pos.MID_LEFT:
				x = OFFSET;  y = 1;
				triAng = (float) ((Math.PI*3)/2);
				triOffset = 0; //-scaleWidth/5;
				break;
			case Pos.DOWN_LEFT:
				x = OFFSET;  y = 2-OFFSET;
				triAng = (float) ((Math.PI*5)/4);
				triOffset = 0;
				break;
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
	
	public void setBalloonText(String text)
	{
		balloonText = text;
	}
	
	public void paint()
	{
		int max;
		
		max = Math.max(biOut.getWidth(), biOut.getHeight());
		
		drawChair();
		drawFace(Bface, biOut, 0, max / 800f, afTrans);
		drawName();
		if (balloonText != null) 
			drawDialog(balloonText);
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

		font = new Font
		(
			"SansSerif", Font.BOLD, 
			(int) (max * .044)
		);
		
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

		grOut.setColor(borderColor);
		grOut.fillRoundRect
		(
			chairBounds[0] + 1, 
			chairBounds[1] + 1, 
			chairBounds[2] - 2, 
			chairBounds[3] - 2,
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
		

		grOut.setFont(font);
		grOut.setColor(Color.BLACK);	
		grOut.drawString(nameShown, x, y);
	}
	
	public boolean inside(int x, int y)
	{
		return 
			x >= chairBounds[0] &&
			y >= chairBounds[1] &&
			x <= chairBounds[0] + chairBounds[2] &&	
			y <= chairBounds[1] + chairBounds[3];
	}
			
//_________________________________________________________________________	
	
	/** Este m�todo es llamado cuando un jugador canta alguna jugada
	 *recibe como argumentos un bufferImage, la accion que debe cantar
	 *y el numero de jugador, o sea su posicion en la mesa*/
	 
	public void drawDialog(String accion)
	{
		int[] xPointsDown, yPointsDown;
		int[] xPointsUp, yPointsUp;
		int[] x1Points, y1Points;

		/**Se calcula el punto central del globito que depende del tama�o de 
		 *la mesa y de la posicion en la que se encuentra*/
		 
		int x = (int) (biOut.getWidth()*.4 * (this.x-1));
		int y = (int) (biOut.getHeight()*.4 * (this.y-1));
	
		x+=biOut.getWidth()/2;
		y+=biOut.getHeight()/2;
		
		grOut.setFont(font);
		
		int width;
		
		/**Aca se elige de acuerdo a la longitud del string el ancho
		 *del globito. Si la longitud es menor 10 hay un ancho por defecto
		 *sino se hace un calculo*/	
		 
		if ((10*accion.length()) < 100)
			width = 100;
		else
			width = 10*(accion.length()-2);
		
		/**Se pone el ancho y alto del globito de acuerdo al tama�o de la mesa*/	
		int scaleWidth = (int)  (width * biOut.getWidth()/PlayTable.TABLE_WIDTH);
		int scaleHeight = (int) (40 * biOut.getHeight()/PlayTable.TABLE_HEIGHT);
		
//		double triAng = 0; //triAng del triangulito
		//si es 90 y 270 tienen que tener un triOffset hacia la 
		//izq y der respectivamente para q no se quede abajo del ovalo
//		int triOffset = 0;	//sirve para los triangulitos de los costados 	

	
		xPointsDown = new int[]
		{
			0,
			(int) (-11 * biOut.getWidth()/PlayTable.TABLE_WIDTH),
			(int) (11 * biOut.getWidth()/PlayTable.TABLE_WIDTH)
		};
		
		yPointsDown = new int[]
		{
			-(scaleHeight/2)-(int) (21 * biOut.getHeight()/PlayTable.TABLE_HEIGHT),
			-(scaleHeight/2),-(scaleHeight/2)
		};
		
		xPointsUp = new int[] 
		{
			0,
			(int) (-10 * biOut.getWidth()/PlayTable.TABLE_WIDTH),
			(int) (10 * biOut.getWidth()/PlayTable.TABLE_WIDTH)
		};
		
		yPointsUp = new int[] 
		{
			-(scaleHeight/2)-(int) (20 * biOut.getHeight()/PlayTable.TABLE_HEIGHT),
			-(scaleHeight/2),-(scaleHeight/2)
		};
	
		x1Points = new int[3];
		y1Points = new int[3];


		/**Se calculan los puntos de acuerdo al tama�o de la mesa y
		 *la posicion del jugador y se dibuja el dialogo negro(el de fondo)*/		
		for (int j=0; j<3; j++)
		{ 
			x1Points[j] =(int) (Math.cos(triAng)*xPointsDown[j] - Math.sin(triAng)*yPointsDown[j]);
			y1Points[j] = (int) (Math.sin(triAng)*xPointsDown[j] + Math.cos(triAng)*yPointsDown[j]);
			x1Points[j] += x+triOffset;
			y1Points[j] += y;
		}
	
		//se dibuja el triangulito
		grOut.setColor(Color.BLACK);
		grOut.fillPolygon(x1Points,y1Points,3);
		
		//se dibuja la elipse de abajo en color negro
		grOut.setColor(Color.BLACK);
		grOut.fillOval((int)x-scaleWidth/2-1,(int)y-scaleHeight/2-1,scaleWidth+2,scaleHeight+2);
		
		
		/**Se calculan los puntos de acuerdo al tama�o de la mesa y
		 *la posicion del jugador y se dibuja el dialogo amarillo*/			
		for (int j=0; j<3; j++){ 
			x1Points[j] =(int) (Math.cos(triAng)*xPointsUp[j] - Math.sin(triAng)*yPointsUp[j]);
			y1Points[j] = (int) (Math.sin(triAng)*xPointsUp[j] + Math.cos(triAng)*yPointsUp[j]);
			x1Points[j] += x+triOffset;
			y1Points[j] += y;
		}
		
		//se dibuja el triangulito
		grOut.setColor(Color.yellow);
		grOut.fillPolygon(x1Points,y1Points,3);
		
		//se dibuja la elipse	
		grOut.setColor(Color.yellow.brighter().brighter());
		grOut.fillOval((int)x-scaleWidth/2,(int)y-scaleHeight/2,scaleWidth,scaleHeight);
		
		//Se coloca la accion al globito
		grOut.setColor(Color.black);	
		//Tama�o del texto de acuerdo al tama�o de la mesa y estilo de letra
		grOut.drawString(accion,(int)x - (int) ((4*accion.length()-1)* biOut.getWidth()/PlayTable.TABLE_WIDTH),y+(5*biOut.getHeight()/PlayTable.TABLE_HEIGHT));
	}


	
	//obtiene la imagen de la carita
	public ImageIcon getFaceImage(int sign)
	{
		return new ImageIcon("..\\imagenes\\faces\\standard\\" + sign + ".jpg");
	}
	
	// dibuja 'bfIn' en 'bfOut' en la posici�n 
	// (x, y) con un �ngulo de rotaci�n 'rotAngle' 
	private void drawFace
	(
		BufferedImage bfIn, BufferedImage bfOut, 
		float rotAngle, float scale,
		AffineTransform afTrans
	)
	{

		int x;
		int y;
		
		
		x = chairBounds[0] + chairBounds[2] / 2;

		y = (int) (chairBounds[1] + chairBounds[3] - nameShownDescent - chairInnerOffset);
		y = Math.max(y, 0);
		y = Math.min(y, biOut.getHeight() - nameShownDescent);
		y -= (int) ((bfIn.getHeight() + 3) * scale / 2);
		y -= nameShownAscent;

				
		AffineTransformOp afTransOp;	// Dibujador de la imagen
		int centX, centY;

		// obtiene el centro de 'bfIn'
		centX = bfIn.getWidth() / 2;
		centY =	bfIn.getHeight() / 2;
		
		// limpia la transformaci�n 
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
		int nameCharsShown;
		int max;
		char[] nameChars;

		playerName = name;
		
		max = Math.max(biOut.getWidth(), biOut.getHeight());

		fMetrics = grOut.getFontMetrics(font);

		nameChars = playerName.toCharArray();
		
		nameShownWidth = fMetrics.stringWidth(playerName);
		nameShownAscent = fMetrics.getAscent();
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



