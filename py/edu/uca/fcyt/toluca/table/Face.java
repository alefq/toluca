package py.edu.uca.fcyt.toluca.table;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;

import py.edu.uca.fcyt.toluca.table.animation.Animable;
import py.edu.uca.fcyt.toluca.table.state.StatesTransitioner;

/**
 * Maneja el dibujo de una carita en el juego
 */
class Face implements Animable
{
	public final static int WIDTH = (int) (PlayTable.TABLE_WIDTH * .2);
	public final static int HEIGHT = (int) (PlayTable.TABLE_HEIGHT * .2);

	private StatesTransitioner[] states;
	private BufferedImage faces[];
	private static final int[][][] triPoints = triangPoints();
	private Font[] fonts;
	private Color borderColor;
	private Rectangle txtClearRegion;
	private int clearText;
	private String playerName;
	private double ballAng;
	public boolean highlighted = false;

	/**
	 * Construye un nuevo Face. 
	 * @param name			nombre del jugador
	 * @param borderColor	color del borde
	 * @param ballAng		ángulo del globo
	 */
	public Face
	(
		String name, Color borderColor, double ballAng
	)
	{ 
		states = new StatesTransitioner[]
		{
			new StatesTransitioner(),
			new StatesTransitioner(),
			new StatesTransitioner()
		};

		this.playerName = name == null ? "": name;
		this.borderColor = borderColor;
		
		fonts = new Font[3];
		fonts[0] = new Font("SansSerif", Font.BOLD, 13);
		fonts[1] = new Font("SansSerif", Font.PLAIN, 13);
		
		this.ballAng = ballAng;
		
		pushSign(Sign.NONE, 0);
		pushText(null, false, 0);
	}
	
	
	/**
     * Retorna el área afectada por el pintado del texto si 
     * debe ser borrado, de lo contrario retorna nulo
     */
    public Rectangle getClearRegion()
    {
    	if (clearText == 1) 
    	{
    		clearText ++;
    		return txtClearRegion;
    	}
    	else return null;
    }
	
	synchronized public void setBorderColor(Color color)
	{
		borderColor = color;
	}
	
	
	/**
     * Agrega una nueva posición a la cola.
     * @param x 	coordenada x de la nueva posición
     * @param y		cooredenada y de la nueva posición
     */
	synchronized public void pushState(FaceState state, long duration)
	{
		states[0].pushState(state, duration);
	}
	
	/**
     * Agrega una pausa a la cola.
     */
	synchronized public void pushPause(long duration)
	{
		states[0].pushPause(duration);
	}
	
	/**
     * Agrega una nueva seña a la cola
     */	
	synchronized public void pushSign(int sign, long duration)
	{
		// agrega la seña a la cola
		states[1].pushState(new SignState(sign), duration);
	}

	/**
     * Agrega una nuevo texto a la cola
     */	
	synchronized public void pushText
	(
		String text, boolean highlighted, long duration
	)
	{
		// agrega la seña a la cola
		states[2].pushState
		(
			new StringState(text, highlighted), duration
		);
	}

	/** 
	 * Cuando el jugador hace click en un jugador a su 
	 * izq,der o centro el ojo se tiene que mover, en realidad 
	 * se cambia el archivo de cara correspondiente
	 */
//	public void setEyedireccion(int d){
//		switch(d){
//			case Dir.LEFT:
//				if (dir != Dir.LEFT)
//					//tiene que buscar el arch correspondiente
//				break;
//			case Dir.CENTER:
//				if (dir != Dir.CENTER)
//				
//				//tiene que buscar el arch correspondiente				
//				break;
//			case Dir.RIGHT:
//				if (dir != Dir.RIGHT)
//					//tiene que buscar el arch correspondiente				
//				break;
//		}
//		dir = d; //se coloca el nuevo valor	
//	}
	
	
//	public int getEyedireccion(){
//		return dir;
//	}
	
	synchronized public void paint(BufferedImage biOut, AffineTransform afTrans)
	{
		int width, height;
		FaceState fState;
		Graphics2D grOut;
		
		// obtiene el estado actual
		fState = (FaceState) states[0].getCurrState();
		
		// si no hay estado actual, salir
		if (fState == null) return;
		
		// obtiene un Graphics2D de 'biOut'
		grOut = biOut.createGraphics();
		
		// aplica 'afTrans' al Graphics2D
		grOut.setTransform(afTrans);
		
		// establece el antialiasing al Graphics2D
		grOut.setRenderingHint
		(
			RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON
		);
		
		// translada el orígen a la coordenada 
		// (x, y) del cuadradito blanco
		grOut.translate
		(
			fState.x, fState.y
		);

		// dibuja el cuadradito blanco
		drawChair(grOut);

		// translada el orígen a la coordenada 
		// (x, y) del centro de los demás objetos
		grOut.translate
		(
			 fState.offX, fState.offY
		);

//		grOut.setColor(Color.BLACK);
//		grOut.fillOval(-5, -5, 5, 5);
		
		drawName(biOut, grOut.getTransform());
		drawFace(biOut, grOut.getTransform());
		drawDialog(biOut, grOut.getTransform());
		
		grOut.dispose();
	}


	/* Dibuja el asiento blanquito*/
	synchronized private void drawChair(Graphics2D grOut)
	{
		int x, y;
		
		x = -WIDTH / 2;
		y = -HEIGHT / 2;

		// si la carita está seleccionada, dibujar el 
		// borde amarillo
		if (highlighted)
		{
			grOut.setColor(Color.YELLOW.brighter());
			grOut.fillRoundRect
			(
				(int) x - 2, 
				(int) y - 2, 
				(int) WIDTH + 4,
				(int) HEIGHT + 4,
				33, 33
			);	
		}
		
		grOut.setColor(Color.BLACK);
		grOut.fillRoundRect
		(
			(int) x, 
			(int) y, 
			(int) WIDTH, 
			(int) HEIGHT,
			33, 33
		);	

		grOut.setColor(borderColor);
		grOut.fillRoundRect
		(
			(int) (x + 1), 
			(int) (y + 1), 
			(int) (WIDTH - 2), 
			(int) (HEIGHT - 2),
			33, 33
		);	

		grOut.setColor(Color.WHITE);
		grOut.fillRoundRect
		(	
			(int) (x + 5), 
			(int) (y + 5), 
			(int) (WIDTH - 10), 
			(int) (HEIGHT - 10),
			23, 23
		);
	}
	
	/**
     * Dibuja el nombre de la carita
     */
	synchronized private void drawName(BufferedImage biOut, AffineTransform afTrans)
	{
		int x, y;
		int off;
		Graphics2D grOut;
		String nameShown;
		int shownLen, shownWidth;
		
		// obtiene un nuevo Graphics2D basado en 'biOut' y 'afTrans'
		grOut = getGraphics2D(biOut, afTrans);

		grOut.setFont(fonts[0]);
		
		shownLen = getNChars(playerName, 0, WIDTH - 40, grOut);
		
		nameShown = playerName.substring(0, shownLen);
		shownWidth = grOut.getFontMetrics().stringWidth(nameShown);
		
		x = (int) (-shownWidth / 2);
		y = (int) (HEIGHT * .42);
		
		grOut.setColor(Color.BLACK);	
		grOut.drawString(nameShown, x, y);
		
		grOut.dispose();
	}
	
	/**
     * Devuelve verdadero si x e y están dentro del cuadrito.
     * Las coordenadas pasadas deben ser las transformadas por 
     * el AffineTransform del Graphics2D en donde se pintó 
     * la carita.
     * @param x	coordenada x ya transformada
     * @param y coordenada y ya transformada
     */
	synchronized public boolean inside(int x, int y)
	{
		FaceState cState;
		Point2D p;
		
		// obtiene el estado actual, si es nulo sale
		try	{ cState = (FaceState) states[0].getCurrState(); }
		catch (NullPointerException ex) { return false; }

		x -= cState.x;
		y -= cState.y;		
		
		return 
			x >= -WIDTH / 2 &&
			y >= -HEIGHT / 2 &&
			x <=  WIDTH / 2 &&	
			y <= HEIGHT / 2 ;
	}
			
	/**
     * Devuelve las coordenadas de 2 triángulos equilátero, el
     * segundo más chico que el primero.
     * @return 	Una matriz de coordenadas, donde la posición
     *			i = 0, 1 para el primer y segundo triáng. resp.
     * 			j = 0, 1 para x e y respectivamente, y 
     *			k = 0, 1, 2 para el primer, segundo y tercer punto.
     *			Ejemplo: (1, 1, 1) = coordenada y del punto 1 del 
     *			segundo triángulo
     */	
	private static int[][][] triangPoints()
	{
		int[][][] ret;
		
		ret = new int[][][]
		{
			new int[][]{new int[3], new int[3]}, 
			new int[][]{new int[3], new int[3]}
		};
		
		for (int i = 0; i < 3; i++)
		{
			ret[0][0][i] = (int) (20 * Math.cos(Math.PI / 3 * i * 2 + .5));
			ret[0][1][i] = (int) (20 * Math.sin(Math.PI / 3 * i * 2 + .5));

			ret[1][0][i] = (int) (18 * Math.cos(Math.PI / 3 * i * 2 + .5));
			ret[1][1][i] = (int) (18 * Math.sin(Math.PI / 3 * i * 2 + .5));
		}
		
		return ret;
	}
		
	
	/** Este método es llamado cuando un jugador canta alguna jugada
	 *recibe como argumentos un bufferImage, la accion que debe cantar
	 *y el numero de jugador, o sea su posicion en la mesa*/
	 
	synchronized private void drawDialog(BufferedImage biOut, AffineTransform afTrans)
	{
		int width, height;
		int textWidth = 0, textHeight = 0;
		int[] xPoints, yPoints;
		StringState sState;
		String text;
		String[] lines;
		FontMetrics fMetrics;
		int triAng = 0, triOffset = 0;
		int lineHeight, lineWidth;
		int lineAscent;
		Graphics2D grBall, grTri;
		boolean highlighted;
		
		// obtiene el estado actual
		sState = (StringState) states[2].getCurrState();
		
		// obtiene el estado resaltado
		highlighted = sState.isHighlighted();
		
		// obtiene el texto actual, si no hay texto actual sale
		text = sState.getText();
		if (text == null) return;
		
		// obtiene nuevos Graphics2D basados en 'biOut' y 'afTrans'
		grBall = getGraphics2D(biOut, afTrans);
		grTri = getGraphics2D(biOut, afTrans);

		// resaltar el texto o no
		if (highlighted)
			grBall.setFont(fonts[0]);
		else
			grBall.setFont(fonts[1]);

		// obtiene las líneas de texto a mostrar
		lines = getLines(text, WIDTH, grBall);
		
		// obtiene las medidas de la fuente actual
		fMetrics = grBall.getFontMetrics();
		
		// obtiene el ancho y el alto del globito
		for (int i = 0; i < lines.length; i++)
		{
			lineWidth = fMetrics.stringWidth(lines[i]);
			if (lineWidth > textWidth)
				textWidth = lineWidth;
		}
		
		lineHeight = fMetrics.getHeight();
		textHeight = lineHeight * lines.length;
		lineAscent = fMetrics.getAscent();
			
		// añade 50 puntos al ancho y al alto
		width = textWidth + 50;
		height = textHeight + 50;

		grBall.translate
		(
			(width * .5 + 40) * Math.sin(ballAng), 
			(height * .5 + 40) * Math.cos(ballAng)
		);
		grTri.rotate(-ballAng);
		grTri.translate(0, 40);
		grTri.scale((double) height / width, 1);
		
		// se establece el color negro
		grTri.setColor(Color.BLACK);
		grBall.setColor(Color.BLACK);
		

		// se dibuja la elipse y el triangulito
		grBall.fillOval
		(
			-width / 2,
			-height / 2,
			width, 
			height
		);
		grTri.fillPolygon(triPoints[0][0], triPoints[0][1], 3);

		// se establece el color a amarillo
		grBall.setColor(Color.YELLOW);
		grTri.setColor(Color.YELLOW);

		// se dibuja la elipse y el triangulito
		grTri.fillPolygon(triPoints[1][0], triPoints[1][1], 3);
		grBall.fillOval
		(
			(int) (-width / 2.0 + 1),
			(int) (-height / 2.0 + 1), 
			width - 2, height - 2
		);

		// resaltar el texto o no
		if (highlighted)
			grBall.setColor(Color.RED.darker());	
		else
			grBall.setColor(Color.BLACK);
		
		// se dibujas las líneas de texto
		for (int i = 0; i < lines.length; i++)
			grBall.drawString
			(
				lines[i],
				(int) (-textWidth / 2),
				lineHeight * i - textHeight / 2 + lineAscent
			);
		
		grBall.dispose();
		grTri.dispose();
	}


	
	/** 
	 * Establece el nombre de la carita
	 * @param name	nombre de la carita
	 */
	synchronized public void setName(String name)
	{
		playerName = name;
	}
	 
	
	/**
     * Obtiene la cantidad de caracteres de un texto que entran 
     * en una cierta cantidad de píxeles a lo largo del texto.
     * @param text	Texto a analizar.
     * @param start	Posición inicial del análisis.
     * @param width	Tamaño máximo en píxeles.
     * @param grOut	Contexto de dibujo del texto
     */
	private int getNChars
	(
		String text, int start, int width, Graphics2D grOut
	)
	{
		FontMetrics fMetrics;
		int max, shown, currWidth;
		char[] chars;

		// obtiene las medidas de la fuente actual
		fMetrics = grOut.getFontMetrics();

		// carga el tamaño actual y la cantidad 
		// de caracteres mostrados actualmente
		currWidth = 0;
		shown = 0;

		try
		{
			while(currWidth < width) 
			{
				currWidth += fMetrics.charWidth
				(
					text.charAt(start + shown++)
				);
			}
		}
		catch(IndexOutOfBoundsException ex) {}
		
		shown --;
		
	   	return shown;
	}
	
	/**
     * Retorna un vector de Strings que es un texto separado
     * en varias líneas de manera que su ancho no sea mayor
     * que <code>width</code>.
     * @param text	Texto a analizar
     * @param width	Tamaño máximo en puntos
     * @param grOut	contexto de dibujo de la fuente
     */
	private String[] getLines
	(
		String text, int width, Graphics2D grOut
	)
	{
		LinkedList lines;
		StringTokenizer strTok;
		FontMetrics fMetrics;
		int lineSize = 0, tokWidth;
		String token, currLine;
		String[] strLines;
		int len;
		
		// crea la lista enlazada de líneas
		lines = new LinkedList();
		
		// obtiene los tókens (palabras) de 'text'
		strTok = new StringTokenizer(text, " ", true);
		
		// obtiene las medidas de la fuente de 'grOut'
		fMetrics = grOut.getFontMetrics();
		
		// inicializa el tamaño actual de la línea como para 
		// que al principio sobrepase el tamaño máximo
		lineSize = 0;
		
		// inicaliza la línea actual
		currLine = new String();
		
		// carga las líneas de texto
		while(strTok.hasMoreElements())
		{
			// obtiene el tóken y su tamaño
			token = (String) strTok.nextElement();
			tokWidth = fMetrics.stringWidth(token);
			
			// si el tamaño del tóken es mayor que 'width'
			while (tokWidth > width)
			{
				len = getNChars(token, 0, width, grOut);

				lines.add(token.substring(0, len - 1));
				token = token.substring(len, token.length() - 1);
				tokWidth = fMetrics.stringWidth(token);
			}

			// incrementa el tamaño actual de la línea
			lineSize += tokWidth;
			
			// si ha excedido el tamaño, habilitar una nueva línea
			if (lineSize > width)
			{
				lineSize = tokWidth;
				lines.add(currLine);
				currLine = new String(token);
			}
			// si no, agregar el tóken a la línea
			else currLine = currLine.concat(token);
		}
		
		lines.add(currLine);
		
		// crea el vector de Strings y lo carga
		strLines = new String[lines.size()];
		System.arraycopy(lines.toArray(), 0, strLines, 0, lines.size());

		return strLines;
	}
	
	synchronized public void clear(Graphics2D grOut) 
	{
		FaceState cState;
		int w, h;
		
		// obtiene el estado anterior
		cState = (FaceState) states[0].getCurrState();
		
		// si no hay estado anterior, salir
		if (cState == null) return;
		
		grOut.setColor(Color.GREEN.darker());
//		grOut.setColor(new Color((int) (Math.random() * Math.pow(2, 24))));
		grOut.fillRect
		(
			(int) (cState.x - WIDTH / 2 - 5), 
			(int) (cState.y - HEIGHT / 2 - 5),
			(int) WIDTH + 10, (int) HEIGHT + 10
		);
	}

	synchronized public boolean advance() 
	{
		states[0].advance();
		states[1].advance();
		states[2].advance();
		return true;
	}

	synchronized public boolean isEnabled() 
	{
		return true;
	}
	
	/**
     * Retorna una copia del estado actual
     */
	synchronized private FaceState getCurrState()
	{
		return (FaceState) states[0].getCurrState();
	}
	
	/**
     * Carga el vector de caritas
	 * @deprecated Usar loadFacesFromURL (applet compilant)
     */
	synchronized public void setFacesDir(String dir)
	{
		ImageIcon fIcon;
		
		if (dir == null)
			faces = null;
		else
		{
			faces = new BufferedImage[10];
			
			for (int i = 0; i < faces.length; i++)
			{
				fIcon = new ImageIcon
				(
					dir + i + ".jpg"
				);
	
				faces[i] = new BufferedImage
				(
					fIcon.getIconWidth(), fIcon.getIconHeight(),
					BufferedImage.TYPE_3BYTE_BGR
				);
	
				Util.copyImage(fIcon, faces[i]);
			}
		}
	}
	
	/**
	 * Carga el vector de caritas
	 */
	synchronized public void loadFacesFromURL(String baseURL)
	{
	    ImageIcon fIcon;
	    
	    if (baseURL == null)
		faces = null;
	    else
	    {
		faces = new BufferedImage[10];
		
		for (int i = 0; i < faces.length; i++)
		{
		    fIcon = new ImageIcon
		    (
		    getClass().getResource(baseURL + i + ".jpg")
		    );
		    if(fIcon != null)
		    {			
			faces[i] = new BufferedImage
			(
			fIcon.getIconWidth(), fIcon.getIconHeight(),
			BufferedImage.TYPE_3BYTE_BGR
			);
			
			Util.copyImage(fIcon, faces[i]);
		    } else
		    {
			System.out.println("No se pudo crear el Icon con : " + baseURL);
			System.out.println("El URL intentado fue: " + baseURL + i + ".jpg");
		    }
		}
	    }
	}
	

	/**
     * Dibuja la carita 
     * @param biOut		lugar donde se dibujará la carita
     * @param afTrans	AffineTransform a utizar
     */
	synchronized private void drawFace
	(
		BufferedImage biOut, AffineTransform afTrans
	) 
	{
		int sign;
		SignState sState;
		
		if (faces == null) return;
		
		sState = (SignState) states[1].getCurrState();
		
		if (sState == null) return;
		
		// obtiene la seña
		sign = sState.getSign();
		
		// crea una copia de 'afTrans'
		afTrans = new AffineTransform(afTrans);

		afTrans.scale(.8, .8);
		
		// translada la carita a su posición
		afTrans.translate
		(
			-faces[sign].getWidth() / 2,
			-faces[sign].getHeight() / 2
		);
		

		// dibuja la imagen 'bfIn' en 'biOut
		new AffineTransformOp
		(
			afTrans, AffineTransformOp.TYPE_BILINEAR
		).filter(faces[sign], biOut);
	}
	
	/**
     * Retorna el tiempo restante de animación
     */
    synchronized public long getRemainingTime()
    {
    	return states[0].getRemainingTime();
    }
    
    /**
     * Retorna un Graphics2D con transformación 
     * afTrans y antialiasing
     */
    private Graphics2D getGraphics2D
    (
    	BufferedImage biOut, AffineTransform afTrans
    )
    {
    	Graphics2D ret;

		ret = biOut.createGraphics();
		ret.setTransform(afTrans);

		// establece el antialiasing al Graphics2D
		ret.setRenderingHint
		(
			RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON
		);
		
		return ret;
	}
}




